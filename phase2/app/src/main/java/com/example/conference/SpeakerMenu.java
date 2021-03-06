package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.presenter.LogInPresenter;
import com.example.presenter.SpeakerController;
import com.example.presenter.UserController;

import java.io.Serializable;

public class SpeakerMenu extends Activity implements View.OnClickListener, SpeakerController.View, Serializable {

    private SpeakerController sc;

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speakermenu);
        LogInPresenter presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");

        sc = new SpeakerController(presenter.getAm(), presenter.getOm(), presenter.getSm(), presenter.getRm(), presenter.getEm(),
                presenter.getMm(),presenter.getVipManager(), presenter.getVipEvent(), presenter.getUserID(),this);
    }


    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewMyEvents:
                Toast.makeText(this, "These are all my events", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(v.getContext(), seeMyEventsActivity.class);
                myIntent2.putExtra("controller", sc);
                startActivityForResult(myIntent2, 4);
                break;
            case R.id.manage:
                Toast.makeText(this, "Manage my account", Toast.LENGTH_SHORT).show();
                Intent myIntent3 = new Intent(v.getContext(), manageMyAccountActivity.class);
                myIntent3.putExtra("controller", sc);
                startActivityForResult(myIntent3, 5);
                break;
            case R.id.viewContactList:
                Toast.makeText(this, "These are all my Contacts", Toast.LENGTH_SHORT).show();
                Intent myIntent4 = new Intent(v.getContext(), viewContactListActivity.class);
                myIntent4.putExtra("controller", sc);
                startActivityForResult(myIntent4, 6);
                break;
            case R.id.exit:
                //Serialize objects
                ReadWrite.saveAttendees(getApplicationContext(),sc.getAttendeeManager());
                ReadWrite.saveOrganizers(getApplicationContext(), sc.getOrganizerManager());
                ReadWrite.saveSpeakers(getApplicationContext(), sc.getSpeakerManager());
                ReadWrite.saveEvent(getApplicationContext(), sc.getEventManager());
                ReadWrite.saveMessage(getApplicationContext(), sc.getMessageManager());
                ReadWrite.saveRoom(getApplicationContext(), sc.getRoomManager());
                ReadWrite.saveVips(getApplicationContext(), sc.getVipManager());
                ReadWrite.saveVipEventManager(getApplicationContext(), sc.getVipEventManager());
                //Send information back to main activity
                Intent myIntent6 = new Intent(SpeakerMenu.this, MainActivity.class);
                myIntent6.putExtra("controller", sc);
                setResult(2, myIntent6);
                finish();


        }
    }

    // Get the data from previous activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 3 || requestCode == 4 || requestCode == 5 || requestCode == 6 ){
            if (resultCode == 3){
                UserController passedData = (UserController) data.getSerializableExtra("cc");
                sc.setManagers(passedData.getAttendeeManager(), passedData.getOrganizerManager(), passedData.getSpeakerManager(), passedData.getRoomManager(),
                        passedData.getEventManager(), passedData.getMessageManager(), passedData.getVipManager(), passedData.getVipEventManager());
                sc.setView(this);
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


