package com.solaris.lyndon.rss;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends Activity {

    protected TextView title, publishDate, url;
    protected WebView content;
    protected final String MIME_TYPE = "text/html";
    protected final String ENCODING = "UTF-8";
    protected ListItem intentCurrentItem;
    protected SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        sp = getSharedPreferences("fontSettings", MODE_PRIVATE);

        // Fetching data from a parcelable object passed from MainActivity
        ListItem currentItem = getIntent().getParcelableExtra("currentItem");
        intentCurrentItem = currentItem;

        EventHandler eventHandler = new EventHandler();

        title = (TextView)findViewById(R.id.title2);
        publishDate = (TextView)findViewById(R.id.pub_date2);
        content = (WebView)findViewById(R.id.content);
        url = (TextView)findViewById(R.id.id_url);

        title.setText(currentItem.getTitle());
        publishDate.setText(currentItem.getPublishDate());
        url.setText(currentItem.getUrl());

        content.setWebChromeClient(new WebChromeClient()); // I'm trying to get iframe youtube elements to work
        content.setWebViewClient(new WebViewClient());
        content.getSettings().setPluginState(WebSettings.PluginState.ON);
        content.getSettings().setJavaScriptEnabled(true);

        content.loadDataWithBaseURL("", resizeImages(currentItem.getContent()), MIME_TYPE, ENCODING, ""); // Loads the content data but shoves css styling in the head
        //content.getSettings().setLoadWithOverviewMode(true);
        //content.getSettings().setUseWideViewPort(true);
        content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        title.setOnClickListener(eventHandler);
        url.setOnClickListener(eventHandler);

    }

    protected String resizeImages(String bodyHTML) {
        String head = "<head><style>img, iframe {max-width: 100%; width:auto; height: auto;} p {font-size:" + sp.getInt("fontSize", 0) + "; color:" + sp.getString("fontColor", "") + "; background-color:" + sp.getString("backgroundColor", "") + ";} </style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    class EventHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if (v.getId() == R.id.title2 || v.getId() == R.id.id_url){
                startWebIntent(intentCurrentItem.getUrl());
            }
        }
    }

    //Programmed to specifically open in chrome
    public void startWebIntent(String url){
        try {
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "You don't have Chrome? WTF?", Toast.LENGTH_SHORT).show();
        }

    }


    /*
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
    */
}
