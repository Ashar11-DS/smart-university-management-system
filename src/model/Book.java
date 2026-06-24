package model;

import java.io.Serializable;

public class Book implements Serializable{
    private int bookID;
    private String title;

    public Book(int bookID, String title) {
        this.bookID = bookID;
        this.title = title;
    }

    // Getters
    public int getBookID() {
        return  bookID;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return bookID + " - " + title;
    }
}
