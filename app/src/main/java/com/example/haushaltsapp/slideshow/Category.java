package com.example.haushaltsapp.slideshow;

import android.graphics.Color;

import com.example.haushaltsapp.R;
public class Category {

    private final String id;
    private String name;
    private final int backgroundColor;
    private final int resourceID;


    public Category(String id, String visibleName, int iconResourceID, int backgroundColor) {
        this.id = id;
        this.name = visibleName;
        this.resourceID = iconResourceID;
        this.backgroundColor = backgroundColor;
    }

    public String getCategoryID() {
        return id;
    }

    private static Category[] categories = new Category[]{
            new Category(":others", "Others", R.drawable.ic_menu_camera, Color.parseColor("#455a64")),
            new Category(":clothing", "Clothing", R.drawable.ic_action_send, Color.parseColor("#d32f2f")),
    };

    public static Category[] getDefaultCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return getCategoryID();
    }

}
