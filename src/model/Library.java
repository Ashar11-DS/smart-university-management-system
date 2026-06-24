package model;

import interfaces.Reportable;
import java.util.ArrayList;

public class Library extends Facility implements Reportable {
    private ArrayList<Book> books;

    public Library(String entityID, String name, String location, double maintainanceCost, int usageFrequency) {
        super(entityID, name, location, maintainanceCost, usageFrequency);
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void generateReport() {
        System.out.println("Library Report");
        System.out.println("Total Books: " + books.size());
    }

    @Override
    public String toString() {
        return  super.toString() + " | Books: " + books.size();
    }
}
