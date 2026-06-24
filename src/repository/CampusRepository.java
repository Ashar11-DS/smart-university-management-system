package repository;   // Central Storage Manager for all Objects

import java.io.Serializable;
import java.util.ArrayList;

public class CampusRepository<T> implements Serializable {
    private ArrayList<T> items;

    public CampusRepository() {
        items = new ArrayList<>();
    }

    // add() -> stores new object
    public void add(T item) throws utils.DuplicateEntryException {   //create
        if(items.contains(item)){
            throw new utils.DuplicateEntryException("Item already Exists!");
        }
        items.add(item);
    }

    // contains() -> checks duplicates
    public boolean contains(T item) {
        return  items.contains(item);
    }

    // getAll() -> Return all objects
    public ArrayList<T> getAll() {  // Read
        return  items;
    }

    // remove() -> Delete Object
    public void remove(T item) {  //Remove
        items.remove(item);
    }

    // Update (Basic replace)
    public void update(int index, T newItem) {
        if(index >= 0 && index < items.size()) {
            items.set(index, newItem);    //.set() -> replaces the element at specified position in the list with specified element
        }
    }

    // size() -> Count objects
    public int size() {
        return items.size();
    }

}
