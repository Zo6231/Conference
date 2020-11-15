package entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the messages for the conference.
 */
public class Message implements Serializable {

    /**
     * Stores the entities.User id of sender
     */
    private int senderID;

    /**
     * Stores the entities.User id of receiver
     */
    private int receiverID;

    /**
     * Stores the message content
     */
    private String content;
    /**
     * Stores the id of the message
     */
    private int messageID;
    /**
     * Stores the condition of whether the message is read by receiver
     */
    private boolean unread;

    /**
     * Constructor:
     *   Create a new entities.Message object given
     *   @param _senderID of the message,
     *   @param _receiverID of the message,
     *   @param _content of the message,
     *   @param messageID of the message.
     */
    public Message(int _senderID, int _receiverID, String _content, int messageID){
        this.senderID = _senderID;
        this.receiverID = _receiverID;
        this.content = _content;
        this.messageID = messageID;
        this.unread = false;
    }

    /**
     * Return the sender ID of the message
     * @return the sender ID of the message
     */
    public int getSenderID(){
        return senderID;
    }

    /**
     * Return the receiver ID of the message
     * @return the receiver ID of the message
     */
    public int getReceiverID(){
        return receiverID;
    }

    /**
     * Return the content of the message
     * @return the content of the message
     */
    public String getContent(){
        return content;
    }

    /**
     * Return the ID of the message
     * @return the ID of the message
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * Return the condition of the message
     * @return the condition of the message
     */
    public boolean getMessageCondition(){
        return unread;
    }

    /**
     * Change the read condition of the message to true
     */
    public void changeMessageCondition(){
        unread = true;
    }
}
