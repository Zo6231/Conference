package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.presenter.LogInPresenter;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;

public class OrganizerMenu extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private OrganizerController controller;

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizermenu);
        LogInPresenter presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");
        controller = new OrganizerController(presenter.getAm(), presenter.getOm(), presenter.getSm(), presenter.getRm(), presenter.getEm(),
                presenter.getMm(),presenter.getVipManager(), presenter.getVipEvent(), presenter.getUserID(),this);
    }


    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewAllEvents:
                Toast.makeText(this, "These are all the events", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(v.getContext(), seeAllEventsActivity.class);
                myIntent.putExtra("controller", controller);
                startActivityForResult(myIntent, 1);
                break;
            case R.id.viewMyEvents:
                Toast.makeText(this, "These are all my events", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(v.getContext(), seeMyEventsActivity.class);
                myIntent2.putExtra("controller", controller);
                startActivityForResult(myIntent2, 2);
                break;
            case R.id.organizerCreateAccount:
                Intent accountIntent = new Intent(v.getContext(), OrganizerCreateAccount.class);
                accountIntent.putExtra("cc", controller);
                startActivityForResult(accountIntent, 3);
                break;
            case R.id.manage:
                Toast.makeText(this, "Manage my account", Toast.LENGTH_SHORT).show();
                Intent myIntent3 = new Intent(v.getContext(), manageMyAccountActivity.class);
                myIntent3.putExtra("controller", controller);
                startActivityForResult(myIntent3, 4);
                break;
            case R.id.viewContactList:
                Toast.makeText(this, "These are all my Contacts", Toast.LENGTH_SHORT).show();
                Intent myIntent4 = new Intent(v.getContext(), viewContactListActivity.class);
                myIntent4.putExtra("controller", controller);
                startActivityForResult(myIntent4, 5);
                break;
            case R.id.social:
                Toast.makeText(this, "Networking", Toast.LENGTH_SHORT).show();
                Intent myIntent5 = new Intent(v.getContext(), SocialNetworking.class);
                myIntent5.putExtra("controller", controller);
                startActivityForResult(myIntent5, 6);

                break;
            case R.id.createRoom:
                Toast.makeText(this, "Enter room", Toast.LENGTH_SHORT).show();
                Intent myIntentCreate = new Intent(v.getContext(), CreateRoom.class);
                myIntentCreate.putExtra("controller", controller);
                startActivityForResult(myIntentCreate, 7);

                break;
            case R.id.messageAll:
                Toast.makeText(this, "Here you message all users", Toast.LENGTH_SHORT).show();
                Intent myIntentMessageAll = new Intent(v.getContext(), OrganizerMessageAllActivity.class);
                myIntentMessageAll.putExtra("controller", controller);
                startActivityForResult(myIntentMessageAll, 8);

                break;
            case R.id.stats:
                Toast.makeText(this, "Here are the stats of the system", Toast.LENGTH_SHORT).show();
                Intent myIntentstats = new Intent(v.getContext(), OrganizerViewStats.class);
                myIntentstats.putExtra("controller", controller);
                startActivityForResult(myIntentstats, 9);

                break;
            case R.id.exit:
                //Serialize objects
                ReadWrite.saveAttendees(getApplicationContext(),controller.getAttendeeManager());
                ReadWrite.saveOrganizers(getApplicationContext(), controller.getOrganizerManager());
                ReadWrite.saveSpeakers(getApplicationContext(), controller.getSpeakerManager());
                ReadWrite.saveEvent(getApplicationContext(), controller.getEventManager());
                ReadWrite.saveMessage(getApplicationContext(), controller.getMessageManager());
                ReadWrite.saveRoom(getApplicationContext(), controller.getRoomManager());
                ReadWrite.saveVips(getApplicationContext(), controller.getVipManager());
                ReadWrite.saveVipEventManager(getApplicationContext(), controller.getVipEventManager());

                //Send information back to main activity
                Intent myIntent6 = new Intent(OrganizerMenu.this, MainActivity.class);
                myIntent6.putExtra("controller", controller);
                setResult(2, myIntent6);
                finish();
                break;
        }
    }

    // Get the data from previous activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1 || requestCode == 2 || requestCode == 3 || requestCode == 4 || requestCode == 5 || requestCode == 6 || requestCode == 7 || requestCode == 8 || requestCode == 9){
            if (resultCode == 3){
                UserController passedData = (UserController) data.getSerializableExtra("cc");
                controller.setManagers(passedData.getAttendeeManager(), passedData.getOrganizerManager(), passedData.getSpeakerManager(), passedData.getRoomManager(),
                        passedData.getEventManager(), passedData.getMessageManager(), passedData.getVipManager(), passedData.getVipEventManager());
                controller.setView(this);
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
