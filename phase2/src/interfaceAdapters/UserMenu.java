package interfaceAdapters;

import entities.Event;
import entities.Message;
import entities.User;
import useCases.*;

import java.util.ArrayList;

public abstract class UserMenu {

    /**
     * Store the AttendeeManager
     */
    private AttendeeManager am;
    /**
     * Store the OrganizerManager
     */
    private OrganizerManager om;
    /**
     * Store the SpeakerManager
     */
    private SpeakerManager sm;
    /**
     * Store the RoomManager
     */
    private RoomManager rm;
    /**
     * Store the EventManager
     */
    private EventManager em;
    /**
     * Store the MessageManager
     */
    private MessageManager mm;
    /**
     * Store the current user
     */
    private User user;
    /**
     * Store the current Manager of the current user
     */
    private UserManager currentManager;

    /**
     * Construct an instance of UserMenu with the given Managers
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param user a instance of <code>User</code> that simulate the user on the keyboard
     */
    public UserMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.user = user;

        if (this.am.getUsers().contains(this.user))
        {
           currentManager = this.am;
        }
        else if (this.om.getUsers().contains((this.user)))
        {
           currentManager = this.om;
        }
        else
        {
           currentManager = this.sm;
        }
    }

    /**
     * Add the given message to the receiver given the receiverID
     * @param receiverID ID of the user who receives the message
     * @param message the message content
     * @return true iff the message is successfully sent
     */
    public boolean sendMessage(int receiverID, Message message){
        currentManager.addMessageID(mm.getIdByMessage(message), user, receiverID);
        //um.addReceivedMessageID(mm.getIdByMessage(message), um.getUserByID(receiverID), um.getIDByUser(user));
        // Remember to add recevied message in sendMessage extension
        return true;
    }

    /**
     * Return the current user
     * @return the current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Return EventManager
     * @return the <code>EventManager</code> in the conference
     */
    public EventManager getEventManager() {
        return em;
    }

    /**
     * Return MessageManager
     * @return the <code>MessageManager</code> in the conference
     */
    public MessageManager getMessageManager() {
        return mm;
    }

    /**
     * Return RoomManager
     * @return the <code>RoomManager</code> in the conference
     */
    public RoomManager getRoomManager() {
        return rm;
    }

    /**
     * Return AttendeeManager
     *  @return the <code>AttendeeManager</code> in the conference
     */
    public AttendeeManager getAttendeeManager()
    {
        return am;
    }

    /**
     * Return OrganizerManager
     * @return the <code>OrganizerManager</code> in the conference
     */
    public OrganizerManager getOrganizerManager()
    {
        return om;
    }

    /**
     * Return SpeakerManager
     * @return the <code>SpeakerManager</code> in the conference
     */
    public SpeakerManager getSpeakerManager()
    {
        return sm;
    }

    /**
     * Return currentManager
     * @return return the <code>AttendeeManager</code> in the conference if the user on the keyboard is an attendee,
     * return the <code>OrganizerManager</code> in the conference if the user on the keyboard is an organizer,
     * return the <code>AttendeeManager</code> in the conference if the user on the keyboard is an attendee,
     */
    public UserManager getCurrentManager() {
        return currentManager;
    }

    /**
     * Return list of all Events the user is going to attend
     * @return list of all Events the user is going to attend
     */
    public ArrayList<Event> viewMyEvents() {
        ArrayList<Event> eventsTheyAttended = new ArrayList<>();
        for (Integer eventID : getUser().getEventsAttend()) {
            eventsTheyAttended.add(getEventManager().getEventByID(eventID));
        }
        return eventsTheyAttended;
    }

    /**
     * Return whether the given friend's ID is in the contact list of the current user
     * @param friendID ID of the user that is going to be checked
     * @return true iff the given friend's ID is in the contact list of the current user
     */
    public boolean hasContact(int friendID){
        return currentManager.getContactList(user).contains(friendID);
    }

    /**
     * Return whether the given event's ID is in the list of all events
     * @param eventID ID of the event that is going to be checked
     * @return true iff the given event's ID is in the list of all events
     */
    public boolean hasEvent(int eventID){
        return this.getEventManager().getEvents().contains(getEventManager().getEventByID(eventID));
    }

    /**
     * Return whether the given event's ID is in the event list of the current user
     * @param eventID ID of the event that is going to be checked
     * @return true iff the given event's ID is in the event list of the current user
     */
    public boolean hasMyEvent(int eventID){
        return currentManager.getEventList(user).contains(eventID);
    }

    /**
     * Set all messages receiving from the friend given by ID as already read
     * @param friendID ID of the friend that the current user is chatting to
     */
    public void readAllMessage(int friendID) {
        for(Integer messageID:getUser().getMessages().get(friendID)){
            Message message = getMessageManager().getMessageById(messageID);
            if(message.getSenderID() == friendID){
                getMessageManager().changeMessageCondition(messageID);
            }
        }

    }

    /**
     * Return the next ID that is going to be assigned to the new User created
     * @return the next ID that is going to be assigned to the new User created
     */
    public int getNewID(){
        int size = getAttendeeManager().getUsers().size() + getOrganizerManager().getUsers().size() + getSpeakerManager().getUsers().size();
        return size + 1;
    }



}
