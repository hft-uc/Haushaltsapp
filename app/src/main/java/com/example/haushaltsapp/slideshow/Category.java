package com.example.haushaltsapp.slideshow;

import android.content.Context;

public class Category {

    private final String id;
    private String name;
    private final int backgroundColor;
    private final int resourceID;
    private int visibleResourceID;


    public Category(String id, String visibleName, int iconResourceID, int backgroundColor) {
        this.id = id;
        this.name = visibleName;
        this.resourceID = iconResourceID;
        this.backgroundColor = backgroundColor;
    }

    public String getCategoryID() {
        return id;
    }


    public String getCategoryVisibleName(Context context) {
        if (name != null)
            return name;
        return context.getResources().getString(visibleResourceID);
    }
}
