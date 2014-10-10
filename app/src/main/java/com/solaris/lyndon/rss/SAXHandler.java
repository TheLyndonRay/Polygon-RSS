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


    public SAXHandler(){

        titles = new ArrayList<String>();
        publishDates = new ArrayList<String>();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXParseException, SAXException {
        Log.d("test", "startElement: qName is " + qName + ", localName is " + localName + ", URI " + uri);
        if (qName.equals("entry")){
            inEntry = true;
        }

        if (qName.equals("title")){
            inTitle = true;
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

            titles.add(s);
        }

        if (inEntry && inPublished){

            publishDates.add(s);
        }



    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (qName.equals("entry")){
            inEntry = false;
        }

        if (qName.equals("title")){
            inTitle = false;
        }

        if (qName.equals("published")){
            inPublished = false;
        }




    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public ArrayList<String> getPublishDates() {
        return publishDates;
    }


}
