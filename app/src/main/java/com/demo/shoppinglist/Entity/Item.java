package com.demo.shoppinglist.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_item")
public class Item {

    @PrimaryKey()
    private Long time ;

    private Integer isImportant;

    private Integer isComplete;

    private String title;

    private String description;

    //Time
    public long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    //TItle
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    //Desciption
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Important
    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    //Complete
    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    public Integer getIsComplete() {
        return isComplete;
    }


    public Item(long time, String title, String description) {
        this.time = time;
        this.title = title;
        this.description = description;
    }

    public Item(long time, Integer isImportant, String title, String description) {
        this.time = time;
        this.isImportant = isImportant;
        this.title = title;
        this.description = description;
    }

    public Item() {
    }

    public Item(long time, Integer isImportant, Integer isComplete, String title, String description) {
        this.time = time;
        this.isImportant = isImportant;
        this.isComplete = isComplete;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "time=" + time +
                ", isImportant=" + isImportant +
                ", isComplete=" + isComplete +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
