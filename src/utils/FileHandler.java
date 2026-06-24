package utils;       //saving/loading data after program closes

import java.io.*; // imports all file handling classes

public class FileHandler {

    // Save Object into a file
    public static void saveData(Object obj, String filename) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj);   // Serialize + write obj into file

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    //Load object (Reads saved object back fom file)
    public static Object loadData (String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {

            return  in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading file (Possibly corrupted).");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found during loading.");
        }
        return  null;
    }
}
