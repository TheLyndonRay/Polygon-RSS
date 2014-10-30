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
    protected String backgroundColor;
    protected boolean hideDate;
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

        Spinner backgroundColorSpinner = (Spinner)findViewById(R.id.background_color_spinner);
        ArrayAdapter<CharSequence> backgroundColorAdapter = ArrayAdapter.createFromResource(this, R.array.background_color_array, android.R.layout.simple_spinner_dropdown_item);
        backgroundColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backgroundColorSpinner.setAdapter(backgroundColorAdapter);

        Spinner showDateSpinner = (Spinner)findViewById(R.id.date_spinner);
        ArrayAdapter<CharSequence> showDateAdapter = ArrayAdapter.createFromResource(this, R.array.show_date_array, android.R.layout.simple_spinner_dropdown_item);
        showDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        showDateSpinner.setAdapter(showDateAdapter);

        FontSizeSpinnerHandler fontSizeSpinnerHandler = new FontSizeSpinnerHandler();
        FontColorSpinnerHandler fontColorSpinnerHandler = new FontColorSpinnerHandler();
        BackgroundColorSpinnerHandler backgroundColorSpinnerHandler = new BackgroundColorSpinnerHandler();
        ShowDateSpinnerHandler showDateSpinnerHandler = new ShowDateSpinnerHandler();

        fontColorSpinner.setOnItemSelectedListener(fontColorSpinnerHandler);
        fontSizeSpinner.setOnItemSelectedListener(fontSizeSpinnerHandler);
        showDateSpinner.setOnItemSelectedListener(showDateSpinnerHandler);
        backgroundColorSpinner.setOnItemSelectedListener(backgroundColorSpinnerHandler);

        //Sets the selection of the spinners to whatever they were set to last
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
            default :
                fontSizeSpinner.setSelection(0);
        }

        //Sets the selection of the spinners to whatever they were set to last
        if (sp.getString("fontColor", "").equals("#000000")){
            fontColorSpinner.setSelection(0);
        } else if (sp.getString("fontColor", "").equals("#3842CF")){
            fontColorSpinner.setSelection(1);
        } else if (sp.getString("fontColor", "").equals("#FA0C64")){
            fontColorSpinner.setSelection(2);
        } else if (sp.getString("fontColor", "").equals("#A00BE6")){
            fontColorSpinner.setSelection(3);
        } else {
            fontColorSpinner.setSelection(0);
        }

        //Sets the selection of the spinners to whatever they were set to last
        if (!sp.getBoolean("hideDate", false)){
            showDateSpinner.setSelection(0);
        } else {
            showDateSpinner.setSelection(1);
        }

        //Sets the selection of the spinners to whatever they were set to last
        if (sp.getString("backgroundColor", "").equals("#FFFFFF")){
            backgroundColorSpinner.setSelection(0);
        } else if (sp.getString("backgroundColor", "").equals("#999999")){
            backgroundColorSpinner.setSelection(1);
        } else if (sp.getString("backgroundColor", "").equals("#FC83DC")) {
            backgroundColorSpinner.setSelection(2);
        }

    }

    class BackgroundColorSpinnerHandler implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            switch (pos){
                case 0 :
                    backgroundColor = "#FFFFFF";
                    break;
                case 1 :
                    backgroundColor = "#999999";
                    break;
                case 2 :
                    backgroundColor = "#FC83DC";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

            backgroundColor = sp.getString("backgroundColor", "");
        }
    }

    class ShowDateSpinnerHandler implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

            switch (pos){
                case 0 :
                    hideDate = false;
                    break;
                case 1 :
                    hideDate = true;
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

            hideDate = sp.getBoolean("hideDate", false);

        }

    }


    class FontSizeSpinnerHandler implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
            switch (pos) {
                case 0 :
                    fontSize = 15;
                    //Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
                    break;
                case 1 :
                    fontSize = 20;
                    //Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
                    break;
                case 2 :
                    fontSize = 25;
                    //Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
                    break;
                case 3 :
                    fontSize = 30;
                    //Toast.makeText(getApplicationContext(), "" + fontSize + " is the font size", Toast.LENGTH_SHORT).show();
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
                    //Toast.makeText(getApplicationContext(), "Black is the current font color", Toast.LENGTH_SHORT).show();
                    break;
                case 1 :
                    fontColor = "#3842CF";
                    //Toast.makeText(getApplicationContext(), "Blue is the current font color", Toast.LENGTH_SHORT).show();
                    break;
                case 2 :
                    fontColor = "#FA0C64";
                    //Toast.makeText(getApplicationContext(), "Red is the current font color", Toast.LENGTH_SHORT).show();
                    break;
                case 3 :
                    fontColor = "#A00BE6";
                    //Toast.makeText(getApplicationContext(), "Purple is the current font color", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            fontColor = sp.getString("fontColor", "");
        }
    }




    // Saves data to my SP
    private void saveData (String fColor, String bgColor, int size, boolean date){

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("fontColor", fColor);
        editor.putString("backgroundColor", bgColor);
        editor.putInt("fontSize", size);
        editor.putBoolean("hideDate", date);
        editor.commit();

    }

    // The back button calls the saveData method to save my stuff
    @Override
    public void onBackPressed(){
        saveData(fontColor, backgroundColor, fontSize, hideDate);
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
            // The UP button calls the saveData method to save my stuff
            saveData(fontColor, backgroundColor, fontSize, hideDate);


        }
        return super.onOptionsItemSelected(item);
    }

}
