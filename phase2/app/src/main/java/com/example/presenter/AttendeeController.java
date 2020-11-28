package com.example.presenter;

import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import com.example.model.entities.Event;
import com.example.model.interfaceAdapters.Presenter;
import com.example.model.useCases.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendeeController extends UserController{

    private View view;
    /**
     * Create an instance of AttendeeController with the given Managers
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param userID is the ID value of user currently in program
     */
    public AttendeeController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, int userID, View view)
    {
        super(am, om, sm, rm, em, mm, userID);
        this.view = view;
    }

    /**
     * return true if signed up successfully, and false if not and update the attendee list in the
     * given event and update the contact list of each speaker host the given event
     * @param eventID the id of event that attendee on the keyboard want to sign up for
     * 1. the given event is not in the conference
     * 2. attendee on the keyboard has already signed up for the given event
     * 3. there is no vacancy in the given event
     * 4. the given event is conflicted with an event that the attendee on the keyboard signed up
     */
     @RequiresApi(api = Build.VERSION_CODES.O)
     public boolean signUp(int eventID) {
         if (getEventManager().getEventByID(eventID) == null){
             view.pushMessage("That Event does not exist!");
             return false;
         }
         if (getCurrentManager().getEventList(getUser()).contains(eventID)){
             view.pushMessage("You already signed up for this event.");
             return false;
         }
         if (getEventManager().getCapacity(eventID) - getEventManager().getUserIDs(eventID).size() <= 0){
             view.pushMessage("The event is already full.");
             return false;
         }
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             if (isUserAvailable(getEventManager().getStartTime(eventID), getEventManager().getEndTime(eventID)))
             {
                 //sign Attendee up for the event
                 getCurrentManager().addEventID(getUser(), eventID);
                 getEventManager().addUserID(getUser(), eventID);
                 //update relevant contact lists
                 List<Integer> speakerIDs = getEventManager().getSpeakerIDs(eventID);
                 for (int speakerID: speakerIDs){
                     // Check if the attendee is already in speaker's contact list
                     if (!getSpeakerManager().getContactList(speakerID).contains(getUser())){
                         //add attendee to speaker's contact list
                         getSpeakerManager().addToContactsList(speakerID, getUser());}
//                     TODO: May not nead this
//                     if (!getCurrentManager().getContactList(getUser()).contains(speakerID))
//                     {
//
//                     }
                 }
                 view.pushMessage("Successfully signed up!");
                 return true;
             }
         }
         return false;
     }
     @RequiresApi(api = Build.VERSION_CODES.O)
     private boolean isUserAvailable(LocalDateTime startTime, LocalDateTime endTime)
     {
         for (int currentEvents: getCurrentManager().getContactList(getUser()))
         {
             LocalDateTime start = getEventManager().getStartTime(currentEvents);
             LocalDateTime end = getEventManager().getEndTime(currentEvents);
             if (checkTime(start, end, startTime, endTime))
             {
                 return false;
             }
         }
         return true;
     }
     @RequiresApi(api = Build.VERSION_CODES.O)
     private boolean checkTime(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime newStartTime, LocalDateTime newEndTime)
     {
         // endTime is between the newStartTime and newEndTime
         // return true if the endtime is in between
         boolean condition1 = (!endTime.isAfter(newEndTime))&&(!endTime.isBefore(newStartTime));
         // old event ends in the middle of new event or before the start of new event
         boolean condition2 = (!startTime.isAfter(newEndTime))&&(!startTime.isBefore(newStartTime));
         // old event starts in the middle of new events or before new event starts
         boolean condition3 = (!newEndTime.isAfter(endTime))&&(!newEndTime.isBefore(startTime));
         boolean condition4 = (!newStartTime.isAfter(endTime))&&(!newStartTime.isBefore(startTime));
         // if one of the conditions fails, return false
         if (condition1 || condition2 || condition3 || condition4){
             return false;
         }
         return true;
     }

     public boolean cancelEnrollment(int eventID)
     {
         if (getEventManager().getEventByID(eventID) != null)
         {
             if (getCurrentManager().getEventList(getUser()).contains(eventID))
             {
                 getCurrentManager().getEventList(getUser()).remove(eventID);
                 getEventManager().getUserIDs(eventID).remove(getUser());
                 view.pushMessage("Cancellation Successful");
                 return true;
             }
             view.pushMessage("Enter an event you have signed up for!");
             return false;
         }
         view.pushMessage("Enter a valid event ID");
         return false;
     }

    /**
     * Adds the message to the messages hashmaps of both the receiver and the sender, returns true iff successful
     * @param receiverID ID of the other user the current user is sending message to
     * @param messageContent content of the message
     * @return true iff the message is sent successfully
     */
    //TODO Message VIP Users
    public boolean sendMessage(int receiverID, String messageContent)
    {
        int messageID = getMessageManager().createMessage( messageContent, getUser(), receiverID);
        if (getAttendeeManager().idInList(receiverID))
        {
            //add message to receiver's hashmap
            getAttendeeManager().addMessageID(messageID, receiverID, getUser());
            //add message to current user's hashmap
            super.sendMessage(receiverID, messageID);
            //add message to message manager
            getMessageManager().addMessage(messageID);
            view.pushMessage("Message Sent");
            return true;
        }
        else if (getSpeakerManager().idInList(receiverID))
        {
            getSpeakerManager().addMessageID(messageID, receiverID, getUser());
            super.sendMessage(receiverID, messageID);
            getMessageManager().addMessage(messageID);
            view.pushMessage("Message Sent");
            return true;
        }
        return false;
    }

    public String viewAllEvents()
    {
        List<Integer> eventIDs = getEventManager().getEvents();
        String output ="";
        for (int ID: eventIDs)
        {
            output = output + ID + ".\t" + getEventManager().getName(ID) + "\t" + getEventManager().getStartTime(ID)
                    + "\t" + getEventManager().getEventByID(ID) + "\t" + getRoomManager().getRoomName(getEventManager().getRoomID(ID))
                    +"\n";
        }
        return output;
    }

    public interface View {
        void pushMessage(String info);
    }
}