import java.io.*;

public class OrganizerManager extends UserManager implements Serializable{

    public OrganizerManager()
    {
        super();
    }
    /**
     * creates a new attendee object and returns it
     * @param name User's real name
     * @param username User's username
     * @param password User's password
     * @return the user object that we created
     */

    public Organizer createOrganizer(String name, String username, String password){
        return new Organizer(name, username, password);
    }

    /**
     * Read the UserManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return UserManager object read from .ser file
     * @throws ClassNotFoundException is thrown if UserManager object is not found
     */
    public OrganizerManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            OrganizerManager om = (OrganizerManager) input.readObject();
            input.close();
            return om;
        } catch (IOException ex) {
            return new OrganizerManager();
        }
    }
    /**
     * Write the UserManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the UserManager
        output.writeObject(this);
        output.close();
    }
}