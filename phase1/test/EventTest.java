import org.junit.*;
import static org.junit.Assert.*;
import java.time.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventTest {
    // the constructor
    @Test(timeout = 50)
    public void testEvent() {
        Event speak1 = new Event(LocalDateTime.of(2020, 1, 1, 20, 0, 0),
                LocalDateTime.of(2020, 1, 1, 21, 0, 0), 101,
                "CSC207", 30);
    }
    // test getRoomID
    @Test(timeout = 50)
    public void testGetRoomID(){
        Event speak1 = new Event(LocalDateTime.of(2020, 1, 1, 20, 0, 0),
                LocalDateTime.of(2020, 1, 1, 21, 0, 0), 101,
                "CSC207", 30);
        assertSame("incorrect RoomID\n", 101, speak1.getRoomID());
    }

    //test getStartTime
    @Test(timeout = 50)
    public void testGetStartTime(){
        LocalDateTime start1 = LocalDateTime.of(2020, 1, 1, 20, 0, 0);
        LocalDateTime end1 = LocalDateTime.of(2020, 1, 1, 21, 0, 0);
        Event speak1 = new Event(start1, end1, 101, "CSC207", 30);
        assertSame("incorrect StartTime\n", start1, speak1.getStartTime());

    }
    @Test(timeout = 50)
    public void testGetEndTime() {
        LocalDateTime start1 = LocalDateTime.of(2020, 1, 1, 20, 0, 0);
        LocalDateTime end1 = LocalDateTime.of(2020, 1, 1, 21, 0, 0);
        Event speak1 = new Event(start1, end1, 101, "CSC207", 30);
        assertSame("incorrect StartTime\n", end1, speak1.getEndTime());
    }

    @Test(timeout = 50)
    public void testGetName(){
        Event speak1 = new Event(LocalDateTime.of(2020, 1, 1, 20, 0, 0),
                LocalDateTime.of(2020, 1, 1, 21, 0, 0), 101,
                "CSC207", 30);
        assertSame("incorrect name\n", "CSC207", speak1.getName());
    }

    @Test(timeout = 50)
    public void testGetUserIDs(){
        Event speak1 = new Event(LocalDateTime.of(2020, 1, 1, 20, 0, 0),
                LocalDateTime.of(2020, 1, 1, 21, 0, 0), 101,
                "CSC207", 30);
        speak1.addUserID(123);
        speak1.addUserID(321);
        speak1.addUserID(1234);
        speak1.removeUserID(321);
        assertSame("incorrect UserID",123, speak1.getUserIDs().get(0) );
        assertSame("incorrect UserID",1234, speak1.getUserIDs().get(1) );
    }

    @Test(timeout = 50)
    public void testGetSpeakerIDs() {
        Event speak1 = new Event(LocalDateTime.of(2020, 1, 1, 20, 0, 0),
                LocalDateTime.of(2020, 1, 1, 21, 0, 0), 101,
                "CSC207", 30);
        speak1.addSpeakerID(567);
        speak1.addSpeakerID(4567);
        speak1.addSpeakerID(23355);
        speak1.removeSpeakerID(4567);
        assertSame("incorrect SpeakerID", 567, speak1.getSpeakerIDs().get(0));
        assertSame("incorrect SpeakerID", 23355, speak1.getSpeakerIDs().get(1));
    }

    @Test(timeout = 50)
    public void testGetRoomIDs() {
        Event speak1 = new Event(LocalDateTime.of(2020, 1, 1, 20, 0, 0),
                LocalDateTime.of(2020, 1, 1, 21, 0, 0), 101,
                "CSC207", 30);
        assertSame("incorrect RoomID", 101, speak1.getRoomID());
        speak1.changeRoomID(102);
        assertSame("incorrect RoomID", 102, speak1.getRoomID());
    }

}