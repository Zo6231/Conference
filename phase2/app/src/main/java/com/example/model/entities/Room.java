package com.example.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the conference.
 * A room is where the events will occur and has a capacity limit for number of people.
 *
 */
public class Room implements Serializable {
    private int capacity;
    private List<Integer> eventsScheduled;
    private int roomID;
    private String name;

    /**
     * Create a new Room object with the given capacity
     * @param capacity maximum capacity of this room
     * @param name name of the room
     * @param roomID roomID of the room
     */
    public Room(int capacity, String name, int roomID)
    {
        this.capacity = capacity;
        eventsScheduled = new ArrayList<>();
        this.roomID = roomID;
        this.name = name;
    }

    /**
     * Returns the maximum capacity of the room.
     * @return the maximum capacity of the room
     */
    public int getCapacity()
    {
        return capacity;
    }

    /**
     * Returns ID value of the room
     * @return ID value of the room
     */
    public int getRoomID()
    {
        return roomID;
    }

    /**
     * Adds an event ID to the eventsScheduled list
     * @param eventID ID of an event to be scheduled in the Room
     */
    public void addEventID(int eventID)
    {
        eventsScheduled.add(eventID);
    }

    /**
     * Removes an event ID from the eventsScheduled list
     * @param eventID ID of an event to be removed from the schedule for Room
     */
    public void removeEventID(int eventID)
    {
        Integer eventId = eventID;
        eventsScheduled.remove(eventId);
    }

    /**
     * Returns a shallow copy of getEventsScheduled
     * @return shallow copy of getEventsScheduled
     */
    public List<Integer> getEventsScheduled() {
        return new ArrayList<>(eventsScheduled);
    }

    /**
     * Returns the name of the room
     * @return the name of the room
     */
    public String getName() {
        return name;
    }
}

