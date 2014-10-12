package com.solaris.lyndon.rss;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;


public class SAXHandler extends DefaultHandler {
    private ArrayList<String> titles;
    private ArrayList<String> publishDates;

    private boolean inEntry, inTitle, inPublished;

    private StringBuilder titleSB; //I need this StringBuilder cuz the characters method sometimes cuts off and runs again mid element, creating a 2nd entry in the ArrayList


    public SAXHandler(){

        titles = new ArrayList<String>();
        publishDates = new ArrayList<String>();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXParseException, SAXException {
        //Log.d("test", "startElement: qName is " + qName + ", localName is " + localName + ", URI " + uri);
        if (qName.equals("entry")){
            inEntry = true;
        }

        if (qName.equals("title")){
            inTitle = true;
            titleSB = new StringBuilder(); //Instantiating it here as we're proven to be in a title element

        }

        if (qName.equals("published")){
            inPublished = true;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String s = new String(ch, start, length);


        if (inEntry && inTitle){

            // This for loop builds the string appending each char it sees (char is an array parameter)
            for (int i=start; i<start+length; i++) {
                titleSB.append(ch[i]);
            }
        }


        if (inEntry && inPublished){
            publishDates.add(s);
        }



    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Log.d("endELement", " Current Title ArrayList size is -- " + titles.size());
        super.endElement(uri, localName, qName);

        if (qName.equals("entry")){
            inEntry = false;
        }

        if (qName.equals("title")){
            // Sometimes it takes EMPTY entries too, wtf.. I don't get it but this makes sure it skips those
            if (!(titleSB.toString().equals(""))){
                titles.add(titleSB.toString().trim()); // Finally, adds the combined char sequence as a string to the titles ArrayList<String>
            }

            inTitle = false;

        }

        if (qName.equals("published")){
            inPublished = false;
        }

        for (String title: titles){
            Log.d("endElement titles", "current titles -- " + title);
        }


    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public ArrayList<String> getPublishDates() {
        return publishDates;
    }


}
