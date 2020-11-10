import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AttendeeMenu extends UserMenu implements UserController{


    public AttendeeMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        super(am, om, sm, rm, em, mm, user);
    }
    public ArrayList<Event> eventsTheyCanSignUpFor(){
        ArrayList<Event> eventsTheyCanSignUpFor = new ArrayList<>();
        for (Event event: getEventManager().getEvents()){
            if (canSignUp(event)){
                eventsTheyCanSignUpFor.add(event);
            }
        }
        return eventsTheyCanSignUpFor;
    }

    private boolean canSignUp(Event event){
        LocalDateTime startTime = getEventManager().getStartTime(event);
        LocalDateTime endTime = getEventManager().getEndTime(event);
        int vacancy = getEventManager().getCapacity(event) - getEventManager().getSpeakerIDs(event).size() - getEventManager().getUserIDs(event).size();
        for (Integer eventToSignUpFor: getCurrentManager().getEventList(getUser())){
            Event actualEventToSignUpFor = getEventManager().getEventByID(eventToSignUpFor);
            LocalDateTime newStartTime = getEventManager().getStartTime(actualEventToSignUpFor);
            LocalDateTime newEndTime = getEventManager().getEndTime(actualEventToSignUpFor);
            if ((newStartTime.isAfter(startTime) && newStartTime.isBefore(endTime) && newEndTime.isBefore(endTime)&& newEndTime.isAfter(startTime)) || vacancy == 0){
                return false;
            }
        }
        return true;
    }

    public boolean signUp(int eventID){
        if (getEventManager().getEventByID(eventID) == null ||getUser().getEventsAttend().contains(eventID)){
            return false;
        }else{
            getUser().addEvent(eventID);
            getEventManager().addUserID(getUser().getUserId(), getEventManager().getEventByID(eventID));
            return true;
        }
    }

    // receiverID has to be in the user's contact list
    public boolean sendMessage(int receiverID, String messageContent){
            boolean canSend = false;
            Message message = getMessageManager().createMessage(messageContent, this.getUser().getUserId(), receiverID);

            if (getAttendeeManager().idInList(receiverID)) {
                canSend = true;
                getAttendeeManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            else if (getOrganizerManager().idInList(receiverID))
            {
                canSend = true;
                getOrganizerManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            else if (getSpeakerManager().idInList(receiverID)){
                canSend = true;
                getSpeakerManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            if (!canSend){
                Presenter.print("Receiver ID doesn't exist");
                return false;
            }
            else{
                Presenter.print("Messages sent");
                getMessageManager().addMessage(message);
                return true;
            }
    }
    public boolean cancelEnrollment(int eventID){
        if (getUser().getEventsAttend().contains(eventID)||getEventManager().getEventByID(eventID) != null) {
            getUser().removeEvent(eventID);
            getEventManager().removeUserID(getUser().getUserId(), getEventManager().getEventByID(eventID));
            Presenter.print("Cancellation successful!");
            return true;
        }else{
            Presenter.print("Cancellation unsuccessful!");
            return false;
        }
    }

    public ArrayList<Event> viewAllEvents()
    {
        ArrayList<Integer> events = getCurrentManager().getEventList(getUser());
        ArrayList<Event> actualEvents = new ArrayList<>();
        for (Integer eventID: events){
            Event event = getEventManager().getEventByID(eventID);
            actualEvents.add(event);
        }
        return actualEvents;
    }
    public User run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        Presenter.printAttendeemenu();
        try{
            String input = br.readLine();
            while (!input.equals("5"))
            {
                if (input.equals("1"))
                {
                    Presenter.viewAllEvents(viewAllEvents(), getEventManager());
                    runViewAllEvents();
                }
                else if (input.equals("2"))
                {
                    Presenter.viewAllEvents(viewMyEvents(), getEventManager());
                    runViewMyEvents();
                }
                else if (input.equals("3"))
                {
                    Presenter.print("Here is your contact list");
                    runViewContacts();
                }
                else if (input.equals("4"))
                {
                    runManage();
                }
                Presenter.printAttendeemenu();
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
            return null;
        }

        Presenter.print("See you again soon");
        return null;
    }
    public void runViewAllEvents() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. Sign up for event\n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter an event number: ");
                    String input2 = br.readLine();
                    int index = Integer.parseInt(input2) - 1;
                    while (index <= 0 || index >= this.viewMyEvents().size()) {
                        Presenter.print("Please enter a valid option: ");
                        input2 = br.readLine();
                        index = Integer.parseInt(input2) - 1;
                    }
                    signUp(index);
                    Presenter.print("Successfully signed up!");
                }
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        }
        catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID!!");
        }
    }
    public void runViewMyEvents() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. Cancel Event\n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter an event number: ");
                    String input2 = br.readLine();
                    int index = Integer.parseInt(input2) - 1;
                    while (index <= 0 || index >= this.viewMyEvents().size()) {
                        Presenter.print("Please enter a valid option: ");
                        input2 = br.readLine();
                        index = Integer.parseInt(input2) - 1;
                    }
                    cancelEnrollment(index);
                    Presenter.print("Enrolment canelled!");
                }
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        }
        catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID!!");
        }
    }

    public void runViewContacts() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.viewMyContacts();
        Presenter.print("1. View chat history \n2. Go back to the main menu");
        try {
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter a friend number: ");
                    String input2 = br.readLine();
                    int index = Integer.parseInt(input2) - 1;
                    while (index <= 0 || index >= this.viewMyContacts().size()) {
                        Presenter.print("Please enter a valid option: ");
                        input2 = br.readLine();
                        index = Integer.parseInt(input2) - 1;
                    }
                    int receiverID = super.getUser().getContactList().get(index);
                    runViewChat(receiverID);
                }
                Presenter.print("1. View chat history \n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        } catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID");
        }
    }
    public void runViewChat(int receiverID) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.viewChat(receiverID);
        Presenter.print("1. Send Message \n2. Go Back to Contacts List");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    Presenter.print("Please type your message here: ");
                    String input2 = br.readLine();
                    sendMessage(receiverID, input2);
                    this.readAllMessage(receiverID);
                   //TODO Why this method?  this.viewChat(receiverID);
                }
                Presenter.print("1. Send Message \n2. Go Back to Contacts List");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
        }
    }
    public void runManage() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. Change Name  \n2. Go back to main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    Presenter.print("Please type new name ");
                    String name = br.readLine();
                    getUser().setName(name);
                }
                Presenter.print("1. Change Name  \n2. Go back to main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
        }
    }
}