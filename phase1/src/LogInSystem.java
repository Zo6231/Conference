import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogInSystem implements UserController {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;

    public LogInSystem(AttendeeManager am, OrganizerManager om, SpeakerManager sm)
    {
        this.am = am;
        this.om = om;
        this.sm = sm;
    }

    @Override
    public User run()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        System.out.println("1. Login\n2. Create new Account");
        try {
            String input = br.readLine();
            if (input.equals("1")) {
                System.out.println("Please enter your credentials");
                while (!input.equals("exit") && prompts.hasNext()) {
                    System.out.println(prompts.next());
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        inputs.add(input);
                    }
                }
                User user =  am.validate(inputs.get(1), inputs.get(2));
                if (!(user == null))
                {
                    return user;
                }
                user = om.validate(inputs.get(1), inputs.get(2));
                if (!(user == null))
                {
                    return user;
                }
                return sm.validate(inputs.get(1), inputs.get(2));
            }
            else if (input.equals("2"))
            {
                System.out.println("Please follow the steps to create an account:");
                while (!input.equals("exit") && prompts.hasNext()) {
                    System.out.println(prompts.next());
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        inputs.add(input);
                    }
                }
                if (am.hasUserName(inputs.get(1)) || om.hasUserName(inputs.get(1)) || sm.hasUserName(inputs.get(1)))
                {
                    System.out.println("That username is already in use");
                    return null;
                }
                else
                {
                    Attendee newAccount = am.createAttendee(inputs.get(0), inputs.get(1), inputs.get(2));
                    am.addUser(newAccount);
                    return newAccount;
                }
            }
            else {
                return null;
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
            return null;
        }
    }

}
