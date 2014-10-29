package com.solaris.lyndon.rss;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class SettingsActivity extends Activity{

    private SharedPreferences sp;
    protected String fontColor;
    protected int fontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        sp = getSharedPreferences("fontSettings", MODE_PRIVATE);

        Spinner fontSizeSpinner = (Spinner)findViewById(R.id.font_size_spinner);
        ArrayAdapter<CharSequence> fontSizeAdapter = ArrayAdapter.createFromResource(this, R.array.font_size_array, android.R.layout.simple_spinner_item);
        fontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(fontSizeAdapter);

        Spinner fontColorSpinner = (Spinner)findViewById(R.id.font_color_spinner);
        ArrayAdapter<CharSequence> fontColorAdapter = ArrayAdapter.createFromResource(this, R.array.font_color_array, android.R.layout.simple_spinner_dropdown_item);
        fontColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontColorSpinner.setAdapter(fontColorAdapter);

        FontSizeSpinnerHandler fontSizeSpinnerHandler = new FontSizeSpinnerHandler();
        FontColorSpinnerHandler fontColorSpinnerHandler = new FontColorSpinnerHandler();

        fontColorSpinner.setOnItemSelectedListener(fontColorSpinnerHandler);
        fontSizeSpinner.setOnItemSelectedListener(fontSizeSpinnerHandler);

        switch (sp.getInt("fontSize", 0)){
            case 15 :
                fontSizeSpinner.setSelection(0);
                break;
            case 20 :
                fontSizeSpinner.setSelection(1);
                break;
            case 25 :
                fontSizeSpinner.setSelection(2);
                break;
            case 30 :
                fontSizeSpinner.setSelection(3);
                break;
        }

        if (sp.getString("fontColor", "").equals("#000000")){
            fontColorSpinner.setSelection(0);
        } else if (sp.getString("fontColor", "").equals("#3842CF")){
            fontColorSpinner.setSelection(1);
        } else if (sp.getString("fontColor", "").equals("#FA0C64")){
            fontColorSpinner.setSelection(2);
        } else if (sp.getString("fontColor", "").equals("#A00BE6")){
            fontColorSpinner.setSelection(3);
        }

    }



    class FontSizeSpinnerHandler implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
            switch (pos) {
                case 0 :
                    fontSize = 15;
                    Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
                    break;
                case 1 :
                    fontSize = 20;
                    Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
                    break;
                case 2 :
                    fontSize = 25;
                    Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
                    break;
                case 3 :
                    fontSize = 30;
                    Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

            fontSize = sp.getInt("fontSize", 0);
        }
    }

    class FontColorSpinnerHandler implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
            switch (pos) {
                case 0 :
                    fontColor = "#000000";
                    Toast.makeText(getApplicationContext(), "Black is the current font color", Toast.LENGTH_SHORT).show();
                    break;
                case 1 :
                    fontColor = "#3842CF";
                    Toast.makeText(getApplicationContext(), "Blue is the current font color", Toast.LENGTH_SHORT).show();
                    break;
                case 2 :
                    fontColor = "#FA0C64";
                    Toast.makeText(getApplicationContext(), "Red is the current font color", Toast.LENGTH_SHORT).show();
                    break;
                case 3 :
                    fontColor = "#A00BE6";
                    Toast.makeText(getApplicationContext(), "Purple is the current font color", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            fontColor = sp.getString("fontColor", "");
        }
    }



    private void saveData (String color, int size){

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("fontColor", color);
        editor.putInt("fontSize", size);
        editor.commit();

    }

    @Override
    public void onBackPressed(){
        saveData(fontColor, fontSize);
        super.onBackPressed();

    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            saveData(fontColor, fontSize);


        }
        return super.onOptionsItemSelected(item);
    }

}
