/*
Coleman DeMars
CSCI 3020 Section W1
Fall 2018
Assignment 6
NetBeans IDE 8.2

This is the handler for the sax parser, the handler retrives the data and returns 
the data to the main in ArrayLists
 */
package demarsassign6;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author colem
 */
public class MyHandler extends DefaultHandler {

    private boolean inCountry;//tell me when i am in a start and end country
    private boolean firstCountry;
    private boolean inCity;//tells us if we are in city element
    private boolean inReligion;
    private boolean notOrg;//this will let me know when to end the religion
    private String countryBuffer;
    private String cityBuffer;
    private String firstCountryBuffer;
    private String religionBuffer;
    private ArrayList<String> country;//holds the country names
    private ArrayList<String> cityName;//holds the city names
    private ArrayList<String> country1;//hold the first country name if there are multiple
    private ArrayList<String> religion;//holds the religions for each country
    private ArrayList<String> relCountry;//holds all the countries just and saves them just once

    public ArrayList<String> getCountrys() {
        return country;//returns the country 
    }
    public ArrayList<String> getCityName() {
        return cityName;//returns the cityName
    }
    public ArrayList<String> getCountry1() {
        return country1;//returns the first country name
    }
    public ArrayList<String> getRelCountry() {
        return relCountry;//returns the first country name only once
    }
    public ArrayList<String> getReligion() {
        return religion;//returns the religions
    }

    @Override
    public void startDocument() {
        country = new ArrayList<>();//stores country name/ all country names
        cityName = new ArrayList<>();//stores city name
        country1 = new ArrayList<>();//stores first country name
        religion = new ArrayList<>();//stores the religions and countries
        relCountry = new ArrayList<>();//stores just the countries
        inCountry = false;
        inCity = false;
        firstCountry = false;
        inReligion = false;
        notOrg = true;
        countryBuffer = "";
        cityBuffer = "";
        firstCountryBuffer = "";//we dont want to reset the buffer for the first country since it will be used many times
        religionBuffer = "";
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName.equals("city")) {
            inCity = true;
        }
        if (localName.equals("country")){
            firstCountry = true;//reset the first country switch 
        }
        if (localName.equals("name") && inCity == false) {
            //System.out.print("<country>");
            inCountry = true;
        }
        if (localName.equals("religions")){
            inReligion = true;
        }
        if (localName.equals("organization")){
           notOrg = false;
        }
        countryBuffer = "";//clear the buffer
        cityBuffer = "";//clear the buffer
        religionBuffer = "";//clear buffer

    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);
        if (inCountry) {//grab countries
            //System.out.println(data);
            countryBuffer += data;//stores the data in the buffer
        }
        if (inCity) {//grab city
            cityBuffer += data;
            
            //System.out.println(data);
        }
        if (firstCountry) {//grab first country
            firstCountryBuffer = data;
            //System.out.println(data);
        }
        if (inReligion && notOrg){//grab religion
            religionBuffer = data;
        }
        
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (inCountry) {
            //System.out.print(countryBuffer);
            //System.out.println("</country>");
            country.add(countryBuffer);//save the country name inside the array list
        }
        if (inCity) {
            cityName.add(cityBuffer);//save the city name iside the city array list
        }
        if (localName.equals("name") && inCity){//this adds the country every time a city is added
            country1.add(firstCountryBuffer);
        }
        if (firstCountry){//this is used for the religion.xml
            relCountry.add(firstCountryBuffer);
            religion.add(firstCountryBuffer);//add countries in with the religions
        }
        if (inReligion && notOrg){
            religion.add(religionBuffer);
        }
        /*the switch is reset every iteration so we can check the next element*/
        inCountry = false;
        inCity = false;
        firstCountry = false;//first country was found, so we flip the switch
        inReligion = false;

    }

    @Override
    public void endDocument() {
        //System.out.println("end");
        //pw.println("</countries>");
        /*for (int i = 0; i < religion.size(); i++){
            String x = religion.get(i);
            System.out.println(x);
        }*/
    }

}
