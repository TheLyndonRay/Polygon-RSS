package com.solaris.lyndon.rss;

/**
 * Created by Lyndon on 2014-10-09.
 */
public class ListItem {

    protected String title;
    protected String publishDate;

    public ListItem (String title, String publishDate){

        this.title = title;
        this.publishDate = publishDate;
    }

    protected void setTitle(String title){
        this.title = title;

    }

    protected void setPublishDate(String publishDate){
        this.publishDate = publishDate;
    }

    protected String getTitle(){
        return title;
    }

    protected String getPublishDate(){
        return publishDate;
    }

}
