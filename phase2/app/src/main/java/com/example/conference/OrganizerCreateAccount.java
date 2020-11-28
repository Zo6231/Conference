package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.model.useCases.OrganizerManager;
import com.example.presenter.LogInPresenter;

import java.io.Serializable;

public class OrganizerCreateAccount extends Activity implements View.OnClickListener, LogInPresenter.View, Serializable{
    private LogInPresenter presenter;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");
        presenter.setView(this);

    }
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //Get user inputted string
        EditText name = findViewById(R.id.nameinput);
        String nameString = name.getText().toString();
        EditText username = findViewById(R.id.Usernameinput);
        String usernameString = username.getText().toString();
        EditText password = findViewById(R.id.passwordinput);
        String passwordString = password.getText().toString();
        switch (v.getId()){
            case R.id.createaccount:

                break;
            case R.id.createattendee:
                break;

        }
    }
}