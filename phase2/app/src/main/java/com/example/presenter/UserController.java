package com.example.presenter;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.model.useCases.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserController implements Serializable{

    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private VipManager vipm;
    private VipEventManager vipe;
    private int userID;
    /**
     * Store the current Manager of the current user
     */
    private UserManager currentManager;
    private View view;

    /**
     * Construct an instance of UserMenu with the given Managers
     *
     * @param am     the instance of <code>AttendeeManager</code> in the conference
     * @param om     the instance of <code>OrganizerManager</code> in the conference
     * @param sm     the instance of <code>SpeakerManager</code> in the conference
     * @param rm     the instance of <code>RoomManager</code> in the conference
     * @param em     the instance of <code>EventManager</code> in the conference
     * @param mm     the instance of <code>MessageManager</code> in the conference
     * @param vipm   the instance of <code>VipManager</code> in the conference
     * @param vipe   the instance of <code>VipEventManager</code> in the conference
     * @param userID an instance of <code>User</code> that simulate the user on the keyboard
     * @param view   the View of the application
     */
    public UserController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                          EventManager em, MessageManager mm, VipManager vipm, VipEventManager vipe,
                          int userID, View view) {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.vipm = vipm;
        this.vipe = vipe;
        this.userID = userID;
        this.view = view;

        //Set the current Manager
        if (this.am.idInList(this.userID)) {
            currentManager = this.am;
        } else if (this.om.idInList((this.userID))) {
            currentManager = this.om;
        } else if (this.sm.idInList((this.userID))){
            currentManager = this.sm;
        }else{
            currentManager = this.vipm;
        }

    }

    /**
     * Adding the Message to the messages hashmaps of both the receiver and the sender,
     * and returns true iff successful
     *
     * @param receiverID     the ID of the other User the current User is sending message to
     * @param messageContent the content of the message
     * @return true iff the Message is sent successfully
     */
    public boolean sendMessage(int receiverID, String messageContent) {
        if (getAttendeeManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add Message to receiver's hashmap
            getAttendeeManager().addMessageID(messageID, receiverID, userID);
            //add Message to current User's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;

        } else if (getOrganizerManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add Message to receiver's hashmap
            getOrganizerManager().addMessageID(messageID, receiverID, userID);
            //add Message to current User's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;

        } else if (getSpeakerManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add Message to receiver's hashmap
            getSpeakerManager().addMessageID(messageID, receiverID, userID);
            //add Message to current User's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;

        } else if (getVipManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add Message to receiver's hashmap
            getVipManager().addMessageID(messageID, receiverID, getUser());
            //add Message to current User's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;
        }
        return false;
    }

    /**
     * Return the current UserID
     *
     * @return the current UserID
     */
    public int getUser() {
        return userID;
    }

    /**
     * Return the EventManager
     *
     * @return the <code>EventManager</code> in the conference
     */
    public EventManager getEventManager() {
        return em;
    }

    /**
     * Return the MessageManager
     *
     * @return the <code>MessageManager</code> in the conference
     */
    public MessageManager getMessageManager() {
        return mm;
    }

    /**
     * Return the RoomManager
     *
     * @return the <code>RoomManager</code> in the conference
     */
    public RoomManager getRoomManager() {
        return rm;
    }

    /**
     * Return the AttendeeManager
     *
     * @return the <code>AttendeeManager</code> in the conference
     */
    public AttendeeManager getAttendeeManager() {
        return am;
    }

    /**
     * Return the OrganizerManager
     *
     * @return the <code>OrganizerManager</code> in the conference
     */
    public OrganizerManager getOrganizerManager() {
        return om;
    }

    /**
     * Return the SpeakerManager
     *
     * @return the <code>SpeakerManager</code> in the conference
     */
    public SpeakerManager getSpeakerManager() {
        return sm;
    }

    /**
     * Return the VipManager
     *
     * @return the <code>VipManager</code> in the conference
     */
    public VipManager getVipManager() {
        return vipm;
    }

    /**
     * Return the VipEventManager
     *
     * @return the <code>VipEventManager</code>
     */
    public VipEventManager getVipEventManager() {
        return vipe;
    }

    /**
     * Return the currentManager
     *
     * @return return the <code>AttendeeManager</code> in the conference if the User on the keyboard is an attendee,
     *         return the <code>OrganizerManager</code> in the conference if the User on the keyboard is an organizer,
     *         return the <code>AttendeeManager</code> in the conference if the User on the keyboard is an attendee,
     */
    public UserManager getCurrentManager() {
        return currentManager;
    }

    /**
     * Return the current UserID
     *
     * @return the current UserID
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Return the list of IDS of all Events that the User is going to attend
     *
     * @return the list of IDs of all Events that the User is going to attend
     */
    public String viewMyEvents() {
        List<Integer> eventIDs = getCurrentManager().getEventList(getUserID());
        if (eventIDs.size() == 0) {
            return "You haven't signed up for any event yet!";
        }
        String output = "";
        for (int ID : eventIDs) {
            if (getEventManager().idInList(ID)){
                output = output + ID + ".\t" + getEventManager().getName(ID) + "\t" + getEventManager().getStartTime(ID) + "\t" + getEventManager().getEndTime(ID)
                        + "\t" + getRoomManager().getRoomName(getEventManager().getRoomID(ID)) + "\t" + getEventManager().getCapacity(ID)
                        +"\n";
            }
            else{
                output = output + ID + ".\t" + getVipEventManager().getName(ID) + "\t" + getVipEventManager().getStartTime(ID) + "\t" + getVipEventManager().getEndTime(ID)
                        + "\t" + getRoomManager().getRoomName(getVipEventManager().getRoomID(ID)) + "\t" + getVipEventManager().getCapacity(ID)
                        +"\n";
            }
        }
        return output;
    }

    /**
     * Return the UserName given the User's ID
     * @param userID the ID of a User
     * @return UserName of the User with given UserID
     */
    public String getUserName(int userID) {
        if (getAttendeeManager().idInList(userID)) {
            // User is an Attendee
            return getAttendeeManager().getnameById(userID);
        }
        else if (getOrganizerManager().idInList(userID)) {
            // User is a Organizer
            return getOrganizerManager().getnameById(userID);
        }
        else if (getSpeakerManager().idInList(userID)) {
            // User is a Speaker
            return getSpeakerManager().getnameById(userID);
        }
        else if (getVipManager().idInList(userID)) {
            // User is a Vip
            return getVipManager().getnameById(userID);
        }
        return "This is not an valid ID.";
    }

    /**
     * Return the HashMap of contactList that key is condition of contacts and value is the list of
     * String representation of contacts that satisfied the condition
     *
     * @return the HashMap of contactList that key is condition of contacts and value is the list of
     *         String representation of contacts that satisfied the condition in the format:
     *         friendID + "\t" + username
     */
    public HashMap<String, List<String>> viewContactList() {
        //create a new HashMap that key is the condition of contacts and values are the empty list.
        HashMap<String, List<String>> contactList = new HashMap<>();
        List<String> readList = new ArrayList<>();
        List<String> unreadList = new ArrayList<>();
        contactList.put("read", readList);
        contactList.put("unread", unreadList);

        //get the contactList of user
        List<Integer> contact = new ArrayList<>();
        contact.addAll(getAttendeeManager().getUserIDs());
        contact.addAll(getOrganizerManager().getUserIDs());
        contact.addAll(getSpeakerManager().getUserIDs());
        contact.addAll(getVipManager().getUserIDs());

        // remove the user himself
        contact.remove((Integer) userID);

        //get the message HashMap of user
        HashMap<Integer, List<Integer>> allMessage = getCurrentManager().getMessages(userID);

        // TODO: If no messages are found in the message hashmap, we won't get messages
        //add the string representation of contacts to the final HashMap
        for (int friendID : contact) {
            List<Integer> messageList = allMessage.get(friendID);
            //check whether there are unread message and add the contact to the corresponding list
            if(messageList != null) {
                boolean hasUnread = false;
                for (int messageID : messageList) {
                    int sendID = getMessageManager().getSenderIDByMessId(messageID);
                    if (!getMessageManager().getConditionByID(messageID) && sendID == friendID) {
                        hasUnread = true;
                    }
                }
                if (hasUnread){
                    contactList.get("unread").add(friendID + "\t" + getUserName(friendID));
                } else {
                    contactList.get("read").add(friendID + "\t" + getUserName(friendID));
                }
            } else {
                contactList.get("read").add(friendID + "\t" + getUserName(friendID));
            }
        }
        return contactList;
    }

    /**
     * Return the list of strings representation of all messages in the chat history with the friendID,
     * content and the condition of the Message
     *
     * @return the list of strings representation of all Messages in the chat history in the format:
     *         friendID + ":\t" + content + "\t" + condition (iff the condition is unread)
     */
    public List<String> viewChatHistory(int friendID) {
        List<String> chatHistory = new ArrayList<>();

        // check if the friendID is valid
        //get the contactList of user
        List<Integer> contact = new ArrayList<>();
        contact.addAll(getAttendeeManager().getUserIDs());
        contact.addAll(getOrganizerManager().getUserIDs());
        contact.addAll(getSpeakerManager().getUserIDs());
        contact.addAll(getVipManager().getUserIDs());

        if (!contact.contains(friendID)){
            view.pushMessage("Please enter a valid friend ID");
            return null;
        }

        // get the chat history between user and given friend
        List<Integer> messageIDList = getCurrentManager().getMessages(userID).get(friendID);
        if (messageIDList != null) {
            for (Integer messageID : messageIDList) {
                int sendID = getMessageManager().getSenderIDByMessId(messageID);
                //if the sender of message is friend and the condition of this message is unread, add the unread mark at the end of this message
                if (!getMessageManager().getConditionByID(messageID) && sendID == friendID) {
                    chatHistory.add(messageID + "\t" + getUserName(sendID) + ":\t" + getMessageManager().getMescontentById(messageID) + "\t(unread)");
                }
                else {
                    chatHistory.add(messageID + "\t" + getUserName(sendID) + ":\t" + getMessageManager().getMescontentById(messageID));
                }
            }
            return chatHistory;
        }
        else{
            return null;
        }
    }

    /**
     * Set all Messages receiving from the friend given by ID as already read
     *
     * @param friendID the ID of the friend that the current User is chatting to
     */
    public void readAllMessage(int friendID) {
        // TODO: How to handle this null-pointer exception and we can't interact with message directly
        HashMap<Integer, List<Integer>> allMessages = getCurrentManager().getMessages(userID);
        if (allMessages.keySet().contains(friendID)){
            if (getCurrentManager().getMessages(userID).get(friendID) != null) {
                for (Integer messageID : getCurrentManager().getMessages(userID).get(friendID)) {
                    int senderID = getMessageManager().getSenderIDByMessId(messageID);
                    if (senderID == friendID) {
                        getMessageManager().changeMessageCondition(messageID);
                    }
                }
            }
        }
    }

    /**
     * Return the next ID that is going to be assigned to the new User being created
     *
     * @return the next ID that is going to be assigned to the new User being created
     */
    public int getNewID() {
        int size = getAttendeeManager().getUsers().size() + getOrganizerManager().getUsers().size() + getSpeakerManager().getUsers().size()
                + getVipManager().getUsers().size();
        return size + 1;
    }

    /**
     * Set the View of current application
     *
     * @param view the new View to be set
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Getter of the current View
     *
     * @return the current View
     */
    public View getView() {
        return view;
    }

    /**
     * Set the name of an User
     *
     * @param name the new name of the User
     */
    public void setName(String name) {
        getCurrentManager().setName(getUser(), name);
    }

    /**
     * Return the String representation of the current class
     *
     * @return the String representation of the current class: "UserController"
     */
    public String getType() {
        return "UserController";
    }

    /**
     * marks View that push Messages
     */
    public interface View {
        void pushMessage(String info);
    }



    /**
     * Mark the given Message as unread to the receiver, who is the current user.
     * Return true iff the Message is successfully marked
     *
     * @param messageID the Message id
     * @return true iff the Message is successfully marked
     */
    // TODO: check whether messageId given is out of range
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean markAsUnread(int messageID, int friendId) {

        // check if the messageID is valid (friendID, out of range)
        List<Integer> chatHistory = getCurrentManager().getMessages(userID).get(friendId);
        if (!chatHistory.contains(messageID)){
            view.pushMessage("Please enter a valid message ID");
            return false;
        }

        // check if the sender is not user himself
        if (getMessageManager().getSenderIDByMessId(messageID) == userID) {
            view.pushMessage("You can't mark your own message as unread");
            return false;
        }

        getMessageManager().markAsUnread(messageID);
        return true;
    }

    /**
     * Set the instances of UserMenu to the given Managers
     * @param am     the instance of <code>AttendeeManager</code> in the conference
     * @param om     the instance of <code>OrganizerManager</code> in the conference
     * @param sm     the instance of <code>SpeakerManager</code> in the conference
     * @param rm     the instance of <code>RoomManager</code> in the conference
     * @param em     the instance of <code>EventManager</code> in the conference
     * @param mm     the instance of <code>MessageManager</code> in the conference
     * @param vipm   the instance of <code>VipManager</code> in the conference
     * @param vipe   the instance of <code>VipEventManager</code> in the conference
     */
    public void setManagers(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                            EventManager em, MessageManager mm, VipManager vipm, VipEventManager vipe) {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.vipm = vipm;
        this.vipe = vipe;

        //SET CURRENTMANAGER
        if (am.idInList(this.userID)) {
            currentManager = am;
        } else if (om.idInList((this.userID))) {
            currentManager = om;
        } else if (sm.idInList((this.userID))){
            currentManager = sm;
        }else{
            currentManager = vipm;
        }
    }


    /**
     * Check whether the User ID exists
     * @param userID the User ID to be checked
     * @return true iff the User ID exists (is contained in at least one UserManager)
     */
    public boolean hasUserID(int userID) {
        if (am.idInList(userID)) {
            return true;
        }
        if (om.idInList(userID)) {
            return true;
        }
        if (sm.idInList(userID)) {
            return true;
        }
        if (vipm.idInList(userID)) {
            return true;
        }
        return false;
    }

    /**
     * Check whether an UserName exists
     * @param username the UserName to be checked
     * @return true iff the UserName is already existed
     */
    public boolean hasUserName(String username) {
        if (am.hasUserName(username)) {
            return true;
        }
        if (om.hasUserName(username)) {
            return true;
        }
        if (sm.hasUserName(username)) {
            return true;
        }
        if (vipm.hasUserName(username)) {
            return true;
        }
        return false;
    }

    /**
     * Return a hashmap of friend ids to number of common Events.
     *
     * @return a hashmap of friend ids to number of common Events, with key of the id of the friend
     * and value of the number of common Events the friend has as the current User
     */
    public HashMap<Integer, Integer> friendToNumOfCommonEvent() {
        // create a new hash map
        HashMap<Integer, Integer> friendToNumOfCommonEvent = new HashMap<>();
        // get a list of all attendees, vip attendees, and organizers
        List<Integer> contact = new ArrayList<>();
        contact.addAll(getAttendeeManager().getUserIDs());
        contact.addAll(getOrganizerManager().getUserIDs());
        contact.addAll(getVipManager().getUserIDs());
        // remove the user himself
        contact.remove((Integer) userID);
        // loop through all the events the current user has signed up for
        for (int eventID : getCurrentManager().getEventList(userID)) {
            // loop through all contacts in the list
            for (int friendID : contact) {
                // add 1 to the corresponding friend key in the hash map if the friend also has
                // the event
                List<Integer> friendEvents = getFriendEvents(friendID);
                if (friendEvents.contains(eventID)) {
                    if (!friendToNumOfCommonEvent.containsKey(eventID)) {
                        friendToNumOfCommonEvent.put(friendID, 1);
                    } else {
                        int curr = friendToNumOfCommonEvent.get(friendID);
                        friendToNumOfCommonEvent.put(friendID, curr + 1);
                    }
                }
            }
        }
        return friendToNumOfCommonEvent;
    }

    /**
     * Return a list of Events the friend is attending given the friend's ID.
     *
     * @param friendID the friend ID
     * @return a list of Events the friend is attending given the friend's ID.
     */
    public List<Integer> getFriendEvents(int friendID) {
        if (getOrganizerManager().getUserIDs().contains(friendID)) {
            return getOrganizerManager().getEventList(friendID);
        } else if (getAttendeeManager().getUserIDs().contains(friendID)) {
            return getAttendeeManager().getEventList(friendID);
        } else if (getVipManager().getUserIDs().contains(friendID)) {
            return getVipManager().getEventList(friendID);
        } else {
            return getSpeakerManager().getEventList(friendID);
        }
    }

    /**
     * Return a hashmap for number of common Events and list of recommended friends
     *
     * @return a hashmap of the number of common Events and list of recommended friends,
     * with key of the value of the number of common Events the friend has as the current User
     * and the list of recommended friends information
     */
    public HashMap<String, List<String>> viewRecommendedFriend() {
        HashMap<Integer, Integer> friendToNumOfCommonEvent = friendToNumOfCommonEvent();
        HashMap<String, List<String>> viewRecommendedFriend = new HashMap<>();
        for (Integer friendID : friendToNumOfCommonEvent.keySet()) {
            String numberOfSameEvent = String.valueOf(friendToNumOfCommonEvent.get(friendID));
            if (viewRecommendedFriend.containsKey(numberOfSameEvent)) {
                viewRecommendedFriend.get(numberOfSameEvent).add(friendID + "\t" + getUserName(friendID));
            } else {
                List<String> friendList = new ArrayList<>();
                friendList.add(friendID + "\t" + getUserName(friendID));
                viewRecommendedFriend.put(numberOfSameEvent, friendList);
            }
        }
        return viewRecommendedFriend;
    }
}



