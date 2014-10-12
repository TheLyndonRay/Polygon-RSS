package com.solaris.lyndon.rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lyndon on 10/12/2014.
 */

// This extends ArrayAdapter for type ListItem
public class CustomArrayAdapter extends ArrayAdapter<ListItem> {

    private List<ListItem> listItems; // Holds a god damn ListItem list that was passed when the adapter was set
    private Context context; //The context

    //The constructor takes in a List of type ListItem called listItems and the context
    public CustomArrayAdapter(List<ListItem> listItems, Context context){
        super(context, R.layout.activity_main, listItems); //Inherits and passes these to its super
        this.listItems = listItems;
        this.context = context;
    }

    /* This will draw the view, I don't understand the parameters */
    public View getView(int position, View convertView, ViewGroup parent) {

        // If convertVie is null, it will create it by making an inflater and then inflating it with activity_main's XML settings
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main, parent, false);
        }

        // Creates the views
        TextView titleView = (TextView)convertView.findViewById(R.id.title);
        TextView pubDateView = (TextView)convertView.findViewById(R.id.pub_date);
        ListItem currentItem = listItems.get(position); // I don't understand how this works

        titleView.setText(currentItem.getTitle()); //Sets it
        pubDateView.setText(currentItem.getPublishDate()); //Sets it

        return convertView;
    }
}
