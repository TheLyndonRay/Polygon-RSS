package com.solaris.lyndon.rss;

import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;


public class SAXHandler extends DefaultHandler {
    private ArrayList<String> titles;
    private ArrayList<String> publisheds;

    private boolean inEntry, inTitle, inPublished;

    private StringBuffer sb;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXParseException, SAXException {
        Log.d("test", "startElement: qName is " + qName + ", localName is " + localName + ", URI " + uri);
        if (qName.equals("entry")){
            inEntry = true;
        }

        if (qName.equals("title")){
            inTitle = true;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (inEntry && inTitle){
            String s = new String(ch, start, length);
            titles.add(s);
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

    }

    public ArrayList<String> getTitles() {
        return titles;
    }


}
