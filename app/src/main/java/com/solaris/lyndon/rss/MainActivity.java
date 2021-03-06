package com.solaris.lyndon.rss;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends ListActivity {
    public static final String FEED_URL = "http://www.polygon.com/rss/group/news/index.xml"; // Needed in RSSFeeder class
    public static final int GET_NAME_REQUEST_CODE = 0;
    public String currentURL = FEED_URL;
    public URL xml_file; // Needed in RSSFeeder class
    public BufferedReader in; // Needed in RSSFeeder class
    protected ListView lv; // Needed in RSSFeeder class

    public SharedPreferences sp;
    public int fontSize;
    public String fontColor; //in hex
    public String backgroundColor; //in hex
    public boolean hideDate;

    protected ArrayList<ListItem> listItems;
    protected ListItem currentItem; // Used to pass to intent for SecondActivity

    RSSFeeder feedme;
    SAXHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("fontSettings", MODE_PRIVATE);
        loadSettings();

        feedme = new RSSFeeder();
        feedme.execute();

    }

    @Override
    public void onResume(){

        loadSettings();
        updateList(currentURL);
        super.onResume();
    }

    protected void loadSettings (){

        // Checks to see if any of my SP settings are empty, sets defaults if they are or whatever is in SP
        if (!sp.contains("backgroundColor") || !sp.contains("fontSize") || !sp.contains("fontColor") || !sp.contains("hideDate")) {

            fontSize = 20;
            fontColor = "#000000";
            backgroundColor = "#FFFFFF";
            hideDate = false;
            Log.d("IN THE IF", "HUUUUUUUUUUUURRRRRRRR");

        } else {

            fontSize = sp.getInt("fontSize", 0);
            fontColor = sp.getString("fontColor", "");
            backgroundColor = sp.getString("backgroundColor", "");
            hideDate = sp.getBoolean("hideDate", false);
            Log.d("IN THE ELSE", "HUUUUUUUUUUUURRRRRRRR");
        }



    }

    protected void updateList (String newURL){

        // Clears adapter if it wasn't already null
        if (lv != null) {
            lv.setAdapter(null);
        }

        currentURL = newURL;
        feedme = new RSSFeeder();
        feedme.execute();
    }

    public class RSSFeeder extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                xml_file = new URL(currentURL);
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

            lv = getListView(); // Don't understand this
            lv.setBackgroundColor(Color.parseColor(backgroundColor));
            lv.setTextFilterEnabled(true); // Don't understand this

            setListAdapter(new CustomArrayAdapter(buildListItem(), getApplicationContext(), fontColor, backgroundColor, fontSize, hideDate)); //my custom adapter

            //Set the listener, setOnItemClickListener is specific for ListViews, takes into account the position
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentItem = listItems.get(position); //instantiating the ListItem object to pass to intent, class variable
                    startSecondActivity(view);
                }
            });

        }
    }

    public void startSecondActivity(View view){

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("currentItem", currentItem); //passes the ListItem object, will make use of the Parcelable version in SecondActivity
        startActivity(intent);

    }

    public void startSettingsActivity(){

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, GET_NAME_REQUEST_CODE);
    }

    private ArrayList<ListItem> buildListItem (){
        listItems = new ArrayList<ListItem>();

        for (int i=0; i < handler.getTitles().size() ; i++ )
        {
            ListItem currentItem = new ListItem(handler.getTitles().get(i), handler.getPublishDates().get(i),
                                                handler.getContents().get(i), handler.getIds().get(i));
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

        switch (id) {
            case R.id.news :

                updateList("http://www.polygon.com/rss/group/news/index.xml");
                break;
            case R.id.reviews :

                updateList("http://www.polygon.com/rss/group/reviews/index.xml");
                break;
            case R.id.features :

                updateList("http://www.polygon.com/rss/group/features/index.xml");
                break;
            case R.id.all :

                updateList("http://www.polygon.com/rss/index.xml");
                break;
            case R.id.settings :

                startSettingsActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
