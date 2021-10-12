package com.demo.shoppinglist.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_item")
public class Item {

    @PrimaryKey
    private long time ;

    @ColumnInfo(defaultValue = "false")
    private Boolean isImportant;

    @ColumnInfo(defaultValue = "false")
    private Boolean isComplete;

    private String title;

    private String description;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getImportant() {
        return isImportant;
    }

    public void setImportant(Boolean important) {
        isImportant = important;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Item(long time, String title, String description) {
        this.time = time;
        this.title = title;
        this.description = description;
    }

    public Item(long time, Boolean isImportant, String title, String description) {
        this.time = time;
        this.isImportant = isImportant;
        this.title = title;
        this.description = description;
    }

    public Item() {
    }

    public Item(long time, Boolean isImportant, Boolean isComplete, String title, String description) {
        this.time = time;
        this.isImportant = isImportant;
        this.isComplete = isComplete;
        this.title = title;
        this.description = description;
    }
}
