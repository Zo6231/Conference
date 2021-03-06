package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.presenter.UserController;

import java.io.Serializable;
import java.util.List;
import java.util.HashMap;

import static java.lang.Integer.parseInt;


public class viewContactListActivity extends Activity implements UserController.View, View.OnClickListener, Serializable {
    private UserController currentController;

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontactlist);
        currentController = (UserController) getIntent().getSerializableExtra("controller");
        currentController.setView(this);
        displayContactList();
    }

    /**
     * Display the contact list in the layout
     */
    public void displayContactList(){
        String message = "UNREAD" + "\n";
        HashMap<String, List<String>> messageMap = currentController.viewContactList();
        for (String s: messageMap.get("unread")){
            message = message + s + "\n";
        }
        message = message + "READ" + "\n";

        for (String s: messageMap.get("read")){
            message = message + s + "\n";
        }
        TextView allContacts = findViewById(R.id.allContacts);
        allContacts.setText(message);
    }

    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewHistory:
                EditText userID = findViewById(R.id.userIDInput);
                String userIDString = userID.getText().toString();
                try {
                    int userID1 = parseInt(userIDString);
                    if (currentController.hasUserID(userID1)){
                        Intent myIntent = new Intent(v.getContext(), messageActivity.class);
                        myIntent.putExtra("cc", currentController);
                        myIntent.putExtra("receiverID", userID1);
                        myIntent.putExtra("parent", "contact");
                        startActivityForResult(myIntent, 3);
                    }else{
                        pushMessage("The userID you entered is not valid");
                    }
                }
                catch(NumberFormatException n){
                    pushMessage("Please enter a valid userID");
                }
                break;

            case R.id.back:
                if (currentController.getType().equals("SpeakerController")){
                    Intent myIntent1 = new Intent(this, SpeakerMenu.class);
                    myIntent1.putExtra("cc", currentController);
                    setResult(3, myIntent1);
                    finish();
                }
                else if (currentController.getType().equals("OrganizerController")){
                    Intent myIntent2 = new Intent(this, OrganizerMenu.class);
                    myIntent2.putExtra("cc", currentController);
                    setResult(3, myIntent2);
                    finish();
                }
                else if (currentController.getType().equals("AttendeeController")){
                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
                    myIntent3.putExtra("cc", currentController);
                    setResult(3, myIntent3);
                    finish();
                }
                else if (currentController.getType().equals("VIPController")){
                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
                    myIntent3.putExtra("cc", currentController);
                    setResult(3, myIntent3);
                    finish();
                }
        }
    }

    // Get the data from previous activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
      if (requestCode == 3){
            if (resultCode == 3){
                UserController passedData = (UserController) data.getSerializableExtra("cc");
                currentController.setManagers(passedData.getAttendeeManager(), passedData.getOrganizerManager(), passedData.getSpeakerManager(), passedData.getRoomManager(),
                        passedData.getEventManager(), passedData.getMessageManager(), passedData.getVipManager(), passedData.getVipEventManager());
                currentController.setView(this);
                displayContactList();
            }
        }

    }

    /**
     * Display a toast message given a string
     * @param info message content of the toast message
     */
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

}
