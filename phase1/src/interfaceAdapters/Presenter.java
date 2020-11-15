package interfaceAdapters;

import entities.Event;
import entities.Room;
import entities.User;
import useCases.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Presenter {

    public static void print(String printStuff){
        System.out.println(printStuff);
    }

    public static void printOrganizermenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Create new Accounts\n6. Add New Room\n7. Log Out");
    }

    public static void printAttendeeMenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Log Out");
    }

    public static void printSpeakerMenu(){
        System.out.println("1. View your Events \n2. View contact list \n3. Manage account \n4. Log Out");
    }

    public static void viewContacts(ArrayList<Integer> contactList, AttendeeManager am, OrganizerManager om, SpeakerManager sm){
        System.out.println("Here are people you can send messages to: ");
        String divider = "------------------------";
        for (Integer userId : contactList) {
            if (am.idInList(userId))
            {
                System.out.println(userId + "." + am.getnameById(userId));
            }
            else if (om.idInList(userId))
            {
                System.out.println(userId + "." + om.getnameById(userId));
            }
            else
            {
                System.out.println(userId + "." + sm.getnameById(userId));
            }
        }
        // want to add unread feature.
        System.out.println(divider);
    }

        public static void viewChat (int receiverID, HashMap<Integer, ArrayList<Integer>> messageIDList,MessageManager mm,
                                     AttendeeManager am, OrganizerManager om, SpeakerManager sm){
            String divider = "------------------------";
            System.out.println("Here are your chat history:");
            if (messageIDList.get(receiverID) == null)
            {
                System.out.println("No chat history");
            }
            else{
                for (Integer messageId : messageIDList.get(receiverID)) {
                    int sendID = mm.getSenderIDByMessId(messageId);
                    String senderName = "";
                    if (am.idInList(sendID))
                    {
                        senderName = am.getnameById(sendID);
                    }
                    else if (om.idInList(sendID))
                    {
                        senderName = om.getnameById(sendID);
                    }
                    else
                    {
                        senderName = sm.getnameById(sendID);
                    }
                    System.out.println(senderName + ":" + mm.getMescontentById(messageId));
                }
            }

            System.out.println(divider);
        }


    public static void viewMyEvents(ArrayList<Event> eventsTheyAttended, EventManager em, RoomManager rm) {
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Number of Attendees";
        String heading4 = "Room";

        System.out.println("Here are your scheduled events:");
        System.out.printf("%-15s %-15s %43s %11s %n", heading1, heading2, heading3, heading4);
        for (Event event : eventsTheyAttended) {
            int roomID = em.getRoomID(event);
            Room room = rm.getRoomByID(roomID);
            System.out.printf("%-15s %-10s %10s %20s %n", em.getIDByEvent(event) + "." + em.getName(event), em.getStartTime(event) + " "
                    + em.getEndTime(event) + " ", em.getNumOfAttendee(event), rm.getRoomName(room));
        }
        System.out.println(divider);
    }


    public static void viewAllEvents(ArrayList<Event> allEventsInSystem, EventManager em, RoomManager rm) {
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Vacancy";
        String heading4 = "Room";

        System.out.println("Here are all the scheduled events:");
        System.out.printf("%-15s %-35s %-15s %-15s %n", heading1, heading2, heading3, heading4);
        for (Event event : allEventsInSystem) {
            int roomID = em.getRoomID(event);
            Room room = rm.getRoomByID(roomID);
            System.out.printf("%-15s %-15s %-15s %-15s %n", em.getIDByEvent(event) + "." + em.getName(event), em.getStartTime(event) + " "
                    + em.getEndTime(event) + " ", em.getVacancy(event) + " ", rm.getRoomName(room));
        }
        System.out.println(divider);
    }


    public static void printSpeakers(ArrayList<User> speakers, UserManager current) {
        String heading = "Speaker's name";
        int i = 1;
        System.out.println("Here are all speakers registered");
        System.out.println(" ");
        System.out.printf("%-4s %n", heading);
        for (User speaker : speakers) {
            int speakerID = current.getIDByUser(speaker);
            System.out.println(current.getIDByUser(speaker) + "." + current.getnameById(speakerID));
            i += 1;
        }
    }

    public static void printRooms(ArrayList<Room> rooms, RoomManager rm) {
        String heading = "Room's name";

        System.out.println("Here are all rooms available");
        System.out.println(" ");
        System.out.printf("%-4s %n", heading);
        for (Room room : rooms) {
            System.out.println(rm.getIDbyRoom(room) + "." + rm.getRoomName(room));

        }
    }
}






