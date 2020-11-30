package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.presenter.UserController;

public class viewContactListActivity extends Activity implements UserController.View, View.OnClickListener {
    private UserController currentController;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontactlist);
        currentController = (UserController) getIntent().getSerializableExtra("cc");
        currentController.setView(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewHistory:
                // TODO
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
                //TODO Also do it for the VIPMenu

        }
    }



    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

}
