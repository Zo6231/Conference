import java.util.ArrayList;

public class ConferenceSystem {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private ReadWrite gateway;


    public ConferenceSystem() {
        gateway = new ReadWrite();
        am = gateway.readAttendee("phase1/src/attendeemanager.ser");
        om = gateway.readOrganizer("phase1/src/organizermanager.ser");
        sm = gateway.readSpeaker("phase1/src/speakermanager.ser");
        rm = gateway.readRoom("phase1/src/roommanager.ser");
        em = gateway.readEvent("phase1/src/eventmanager.ser");
        mm = gateway.readMessage("phase1/src/messagemanager.ser");
        gateway.setManagers(am, om, sm, em, rm, mm);
        System.out.println(am.getUsers().size());
        System.out.println(om.getUsers().size());
        System.out.println(sm.getUsers().size());
    }

    // This method also handles the contact list
    public User createOrganizerAccount(String name, String username, String password){
        User organizer = createOrganizerAccount(name, username, password);
        om.addUser(organizer);
        // Initiate the contact list
        for (User attendee: am.getUsers()){
            // add every attendee to this organizer's contact list
            om.addToContactsList(organizer, am.getIDByUser(attendee));
        }
        for (User speaker: sm.getUsers()){
            // add every speaker to this organizer's contact list
            om.addToContactsList(organizer, sm.getIDByUser(speaker));
        }
        // However, there is still more things we need to check
        // When organizer sends a message to a speaker or an attendee, add this organizer to their contact list (done)
        return organizer;

    }

    public void run()
    {
        UserController current = new LogInSystem(am, om, sm);
        boolean iterate = true;
        while (iterate) {
            User new_user = current.run();
            if (new_user != null) {
                iterate = false;
                if (am.getUsers().contains(new_user)) {
                   current = new AttendeeMenu(am, om, sm, rm, em, mm, new_user);
                   current.run();
                   gateway.saveAll();

                }
                else if (sm.getUsers().contains(new_user)) {
                    current = new SpeakerMenu(am, om, sm, rm, em, mm, new_user);
                    current.run();
                    gateway.saveAll();
                }
                else if (om.getUsers().contains(new_user))
                {
                    current = new OrganizerMenu(am, om, sm, rm, em, mm, new_user);
                    current.run();
                    gateway.saveAll();
                }
            } else {
                System.out.println("Error please try again");
            }
        }
    }

}
