package com.solaris.lyndon.rss;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SecondActivity extends Activity {

    protected TextView title, publishDate, content, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Fetching data from a parcelable object passed from MainActivity
        ListItem currentItem = getIntent().getParcelableExtra("currentItem");

        title = (TextView)findViewById(R.id.title2);
        publishDate = (TextView)findViewById(R.id.pub_date2);
        content = (TextView)findViewById(R.id.content);
        url = (TextView)findViewById(R.id.id_url);

        title.setText(currentItem.getTitle());
        publishDate.setText(currentItem.getPublishDate());
        content.setText(currentItem.getContent()); // WebView this shit later as the content has HTML to format
        url.setText(currentItem.getUrl());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
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
