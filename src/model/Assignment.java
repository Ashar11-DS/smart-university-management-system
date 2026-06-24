package model;

import  java.io.Serializable;

public class Assignment implements Serializable {
    private String title;
    private int marks;

    public Assignment (String title, int mark) {
        this.title = title;
        this.marks = mark;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return  title + " (" + marks + " marks )";
    }
}
