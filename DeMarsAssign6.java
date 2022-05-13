/*
Coleman DeMars
CSCI 3020 Section W1
Fall 2018
Assignment 6
NetBeans IDE 8.2

This program impliments a SAX parser and gets the following information from the mondial-3.0.xml file
The parser extracts the information and returns it to main in an ArrayList. The arrayList
is then written to an .xml file
 */
package demarsassign6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author colem
 */
public class DeMarsAssign6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            XMLReader reader = XMLReaderFactory.createXMLReader();
            MyHandler handler = new MyHandler();
            reader.setContentHandler(handler);
            reader.setErrorHandler(handler);

            InputSource inputSource = new InputSource("mondial-3.0.xml");
            reader.parse(inputSource);

            /*COUNTRY.XML FILE*/
            ArrayList<String> data = handler.getCountrys();
            PrintWriter pw = null;
            pw = new PrintWriter("country.xml");
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<!--");
            pw.println("Developer: Coleman DeMars");
            pw.println("-->");
            pw.println("<countries>");
            for (int i = 0; i < data.size(); i++) {
                String country = data.get(i);
                pw.print("     <country>");
                pw.print(country);
                pw.println("</country>");
            }
            pw.println("</countries>");
            pw.close();

            /*CITIES.XML FILE*/
            ArrayList<String> country1 = handler.getCountry1();
            ArrayList<String> city = handler.getCityName();
            pw = new PrintWriter("cities.xml");
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<!--");
            pw.println("Developer: Coleman DeMars");
            pw.println("-->");
            pw.println("<cities>");
            for (int i = 0; i < country1.size(); i++) {

                String c = city.get(i);
                String countryStr = country1.get(i);
                pw.println("    <city>");
                pw.print("      <city-name>");
                pw.print(c);//outputs city to file
                pw.println("</city-name>");
                pw.print("      <country-name>");
                pw.print(countryStr);//outputs country to file
                pw.println("</country-name>");
                pw.println("    </city>");
            }
            pw.println("</cities>");
            pw.close();

            /*RELIGIONS.XML FILE*/
            ArrayList<String> relCountry = handler.getRelCountry();//this hold a list of just the countries
            ArrayList<String> religion = handler.getReligion();
            pw = new PrintWriter("religions.xml");
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<!--");
            pw.println("Developer: Coleman DeMars");
            pw.println("-->");
            pw.println("<religions>");
            int g = 0;
            //int p = relCountry.size();
            //int h = religion.size();
            //System.out.println(p);
            //System.out.println(h);
            for (int i = 0; i < relCountry.size();) {

                String c = relCountry.get(i);
                String r = religion.get(g);
                if (r.equals(c)) {//we are finsished with that country
                    if (i > 0) {
                        pw.println("        </religion-list>");
                        pw.println("    </country>");
                    }
                    pw.println("    <country>");
                    pw.println("        <country-name>" + c + "</country-name>");
                    pw.println("        <religion-list>");
                    i++;
                    g++;
                } else {
                    pw.println("            <religion>" + r + "</religion>");
                    g++;

                }
                if (i == relCountry.size()) {
                    i++;
                    while (i < religion.size()){
                        r = religion.get(i);
                        pw.println("            <religion>" + r + "</religion>");
                        i++;
                    }
                    pw.println("        </religion-list>");
                    pw.println("    </country>");
                }
            }
            pw.println("</religions>");

            pw.close();
        } catch (SAXException ex) {
            Logger.getLogger(DeMarsAssign6.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DeMarsAssign6.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
