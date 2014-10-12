package com.solaris.lyndon.rss;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends ListActivity {
    public static final String FEED_URL = "http://www.polygon.com/rss/group/news/index.xml"; // Needed in RSSFeeder class
    public URL xml_file; // Needed in RSSFeeder class
    public BufferedReader in; // Needed in RSSFeeder class
    protected ListView lv; // Needed in RSSFeeder class

    RSSFeeder feedme;
    SAXHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedme = new RSSFeeder();
        feedme.execute();



    }


    public class RSSFeeder extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                xml_file = new URL(FEED_URL);
                in = new BufferedReader(new InputStreamReader(xml_file.openStream()));

                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                handler = new SAXHandler();

                sp.parse(new InputSource(in), handler);

                //Thread.sleep(10000);
            } catch (MalformedURLException ex) {
                Log.e("Test", ex.getMessage());
            } catch (IOException ex) {
                Log.e("Test", ex.getMessage());
            } catch (SAXException ex) {
                Log.e("Test", ex.getMessage());
            } catch (ParserConfigurationException ex) {
                Log.e("Test", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            lv = getListView();
            lv.setTextFilterEnabled(true);

            setListAdapter(new CustomArrayAdapter(buildListItem(), getApplicationContext()));

        }
    }

    private ArrayList<ListItem> buildListItem (){
        ArrayList<ListItem> listItems = new ArrayList<ListItem>();

        for (int i=0; i < handler.getTitles().size() ; i++ )
        {
            ListItem currentItem = new ListItem(handler.getTitles().get(i), handler.getPublishDates().get(i));
            listItems.add(currentItem);
        }

        return listItems;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
