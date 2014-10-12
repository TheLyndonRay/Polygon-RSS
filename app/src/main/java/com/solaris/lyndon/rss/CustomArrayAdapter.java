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
public class CustomArrayAdapter extends ArrayAdapter<ListItem> {

    private List<ListItem> listItems;
    private Context context;

    public CustomArrayAdapter(List<ListItem> listItems, Context context){
        super(context, R.layout.activity_main, listItems);
        this.listItems = listItems;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main, parent, false);
        }

        TextView titleView = (TextView)convertView.findViewById(R.id.title);
        TextView pubDateView = (TextView)convertView.findViewById(R.id.pub_date);
        ListItem currentItem = listItems.get(position);

        titleView.setText(currentItem.getTitle());
        pubDateView.setText(currentItem.getPublishDate());

        return convertView;
    }
}
