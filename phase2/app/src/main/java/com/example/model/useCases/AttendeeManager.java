package com.example.model.useCases;

import com.example.model.entities.Attendee;
import com.example.model.entities.User;

import java.io.*;

public class AttendeeManager extends UserManager implements Serializable{

    public AttendeeManager()
    {
        super();
    }

    /**
     * Read the useCases.UserManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return useCases.UserManager object read from .ser file
     * @throws ClassNotFoundException is thrown if useCases.UserManager object is not found
     */
    public AttendeeManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            AttendeeManager am = (AttendeeManager) input.readObject();
            input.close();
            return am;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return new AttendeeManager();
        }
    }
    /**
     * Write the useCases.UserManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the useCases.UserManager
        output.writeObject(this);
        output.close();
    }
    /**
     * create a new user and add user to list
     * @param name
     * @param userName
     * @param password
     * @param ID
     * @return true if and only if the user is successfully added to the list
     */
    public boolean createAttendee(String name, String userName, String password, int ID)
    {
        User user = new Attendee(name, userName, password, ID);
        if (addUser(user.getUserId()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
