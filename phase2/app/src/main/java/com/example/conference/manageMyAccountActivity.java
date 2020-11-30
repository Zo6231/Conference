package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.presenter.UserController;

public class manageMyAccountActivity extends Activity implements View.OnClickListener, UserController.View {
    private UserController currentController;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managemyaccount);
        currentController = (UserController) getIntent().getSerializableExtra("cc");
        currentController.setView(this);
        setName();


    }

    public void setName(){
        String curName = currentController.getUserName(currentController.getUser());
        TextView currentNameTextView = findViewById(R.id.currentName);
        currentNameTextView.setText("Your current name is: " + curName);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                EditText name = findViewById(R.id.changeNameInput);
                String nameString = name.getText().toString();
                if (!nameString.equals("")){
                    currentController.setName(nameString);
                }
                else{
                    pushMessage("Your name can't be empty!");
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

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

}

