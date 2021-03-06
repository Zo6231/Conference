package com.example.model.entities;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event in the conference.
 */
public class Event implements Serializable {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int roomID;
    private List<Integer> userIDs;
    private List<Integer> speakerIDs;
    private String name;
    private int eventID;
    private int capacity;


    /**
     * Create a new event object with the given startTime, endTime, roomID, name
     *
     * @param startTime of this event
     * @param endTime   of this event
     * @param roomID    of this event
     * @param name      of this event
     * @param capacity  of this event
     * @param eventID   of this event
     */
    public Event(LocalDateTime startTime, LocalDateTime endTime, int roomID, String name, int capacity, int eventID) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomID = roomID;
        this.name = name;
        this.capacity = capacity;
        this.eventID = eventID;
        userIDs = new ArrayList<>();
        speakerIDs = new ArrayList<>();

    }

    /**
     * Returns the roomID of the event
     *
     * @return the roomID of the event
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Returns the startTime of the event
     *
     * @return the startTime of the event
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Change the start time to the given time.
     *
     * @param time that will be set as start time
     */
    public void setStartTime(LocalDateTime time) {
        startTime = time;
    }

    /**
     * Returns the endTime of the event
     *
     * @return the endTime of the event
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Change the end time to the given time.
     *
     * @param time that will be set as end time
     */
    public void setEndTime(LocalDateTime time) {
        endTime = time;
    }

    /**
     * Returns the name of the event
     *
     * @return the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an speaker ID to the speakerIDs list
     *
     * @param speakerID to be added in the entities.Event
     */
    public void addSpeakerID(int speakerID) {
        speakerIDs.add(speakerID);
    }

    /**
     * Adds an user ID to the userIDs (Attendees) list
     *
     * @param userID to be added in the entities.Event
     */
    public void addUserID(int userID) {
        userIDs.add(userID);
    }

    /**
     * removes an user ID from the userIDs (Attendees) list
     *
     * @param userID to be removed in the entities.Event
     */
    public void removeUserID(int userID) {
        Integer userId = userID;
        userIDs.remove(userId);
    }

    /**
     * Returns the shallow copy of UserIDs of the event
     *
     * @return the UserIDs of the event
     */
    public List<Integer> getUserIDs() {
        return new ArrayList<>(userIDs);
    }

    /**
     * Returns the shallow copy of SpeakerIDs of the event
     *
     * @return the SpeakerIDs of the event
     */
    public List<Integer> getSpeakerIDs() {
        return new ArrayList<>(speakerIDs);
    }

    /**
     * Returns the eventID of the event
     *
     * @return the eventID of the event
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * removes an speaker ID from the speakerIDs list
     *
     * @param speakerID to be removed in the entities.Event
     */
    public void removeSpeakerID(int speakerID) {
        Integer speakerId = speakerID;
        speakerIDs.remove(speakerId);
    }

    /**
     * change the room ID of the event
     *
     * @param roomID new roomID
     */
    public void changeRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     * Returns the capacity of the event
     *
     * @return the capacity of the event
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Change the capacity of the event
     *
     * @param capacity the new capacity of the event
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the number of attendees in this event
     */
    public int getNumOfAttendee() {
        return userIDs.size();
    }
}



