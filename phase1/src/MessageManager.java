import java.io.*;
import java.util.ArrayList;

public class MessageManager implements Serializable{

    /**
     * The list of all Messages
     */
    private ArrayList<Message> messages = new ArrayList<>();

    /**
     * Returns the shallow copy of all messages in a list
     * @return the shallow copy of all messages in a list
     */
    public ArrayList<Message> getMessages(){
        return (ArrayList<Message>) messages.clone();
    }

    /**
     * add a new Message to messages
     * @param message message to be added
     * @return true if and only if the message is successfully added to the message list
     */
    public boolean addMessage(Message message){
        for (Message content: messages){
            if(content == message){
                return false;
            }
        }
        messages.add(message);
        return true;
    }

    /**
     * return the Message object given the message ID
     * @param messageID the given Message ID
     * @return the Message object corresponding to the messageID
     */
    public Message getMessageById(int messageID){
        for (Message message: messages){
            if (message.getMessageID() == messageID){
                return message;
            }
        }
        return null;
    }
    public Message createMessage(String content, int senderID, int receiverID){
        return new Message(senderID, receiverID, content);
    }

    /**
     * return the messageID given the Message object
     * @param message the given Message object
     * @return the messageID corresponding to the Message object
     */
    public int getIdByMessage(Message message){
        return message.getMessageID();
    }

    /**
     * Read the MessageManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return MessageManager object read from .ser file
     * @throws ClassNotFoundException is thrown if MessageManager object is not found
     */
    public MessageManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            MessageManager mm = (MessageManager) input.readObject();
            input.close();
            return mm;
        } catch (IOException ex) {
            return new MessageManager();
        }
    }

    /**
     * Write the MessageManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the MessageManager
        output.writeObject(this);
        output.close();
    }

}