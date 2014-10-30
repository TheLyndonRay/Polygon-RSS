package com.solaris.lyndon.rss;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lyndon on 2014-10-09.
 */
public class ListItem implements Parcelable{

    protected String title, publishDate, content, url;

    // ListItem main constructor
    public ListItem (String title, String publishDate, String content, String url){

        this.title = title;
        this.publishDate = publishDate;
        this.content = content;
        this.url = url;
    }


    /*

    4 hours in the god damn API
    The next set of lines are for Parcelables. I needed this to pass ListItem objects via Intents

    */

    //Invoked by method createFromParcel(Parcel source) of object CREATOR ?? WTF DOES THIS MEAN?!
    private ListItem (Parcel in){
        this.title = in.readString();
        this.publishDate = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    // The creator of the ListItem Parcelables
    public static final Parcelable.Creator<ListItem> CREATOR = new Parcelable.Creator<ListItem>(){

        @Override
        public ListItem createFromParcel(Parcel source){
            return new ListItem(source);
        }

        @Override
        public ListItem[] newArray(int size){
            return new ListItem[size];
        }
    };

    // I needed this, but don't understand why. Won't compile without it
    @Override
    public int describeContents(){
        return 0;
    }

    // This stores the ListItem data to the Parcelable version of ListItem
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(title);
        dest.writeString(publishDate);
        dest.writeString(content);
        dest.writeString(url);
    }

    /*

    END OF THE PARCELABLE BULLSHITS

    */

    protected String getTitle(){
        return title;
    }

    protected String getPublishDate(){
        return publishDate;
    }

    protected String getContent(){
        return content;
    }

    protected String getUrl(){
        return url;
    }
}
