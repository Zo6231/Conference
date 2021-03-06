package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.presenter.AttendeeController;
import com.example.presenter.SpeakerController;
import com.example.presenter.UserController;

import java.io.Serializable;



public class seeMyEventsActivity extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private UserController currentController;

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentController = (UserController) getIntent().getSerializableExtra("controller");
        currentController.setView(this);

        if (currentController.getType().equals("SpeakerController")){
            setContentView(R.layout.speakerseemyevents);

        }else{
            setContentView(R.layout.seemyevents);
        }

        TextView myEvents = findViewById(R.id.allEvents);
        String text = currentController.viewMyEvents();
        myEvents.setText(text);
    }


    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.messageAll:
                SpeakerController speakerController = (SpeakerController)currentController;
                EditText event = findViewById(R.id.eventMessage);
                EditText message = findViewById(R.id.messageContent);
                String eventID = event.getText().toString();
                String messageContent = message.getText().toString();
                if (messageContent.equals("")){
                    pushMessage("Your message can't be empty");
                }
                try{
                    int index = Integer.parseInt(eventID);
                    speakerController.messageAll(index, messageContent);
                }catch(NumberFormatException n){
                    pushMessage("Please enter a valid eventID");
                }
                break;

            case R.id.cancel:
                EditText eventToCancel = findViewById(R.id.eventID);
                String eventIDString = eventToCancel.getText().toString();
                try{
                    int index = Integer.parseInt(eventIDString);
                    AttendeeController controller = (AttendeeController) currentController;
                    if(controller.cancelEnrollment(index)){
                        Toast.makeText(this, "Cancelled successfully", Toast.LENGTH_SHORT).show();
                    }
                }catch(NumberFormatException n){
                    pushMessage("Please enter a valid eventID");
                }
                TextView myEvents = findViewById(R.id.allEvents);
                String text = currentController.viewMyEvents();
                myEvents.setText(text);
                break;
            case R.id.back:
                if (currentController.getType().equals("SpeakerController")){
                    Intent myIntent = new Intent(this, SpeakerMenu.class);
                    myIntent.putExtra("cc", currentController);
                    setResult(3, myIntent);
                    finish();
                }
                else if (currentController.getType().equals("OrganizerController")){
                    Intent myIntent = new Intent(this, OrganizerMenu.class);
                    myIntent.putExtra("cc", currentController);
                    setResult(3, myIntent);
                    finish();
                }
                else if (currentController.getType().equals("AttendeeController")){
                    Intent myIntent = new Intent(this, AttendeeMenu.class);
                    myIntent.putExtra("cc", currentController);
                    setResult(3, myIntent);
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

    /**
     * Display a toast message given a string
     * @param info message content of the toast message
     */
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
