package com.test.pokedex.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.pokedex.R;

public class ActivityLogin extends AppCompatActivity {

    EditText usernameText, passwordText;
    Button loginBtn;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initializeComponents();
        manageIntent();
        initializeListeners();
        initializeData();
    }

    public void manageIntent() {
        username = getIntent().getStringExtra("USERNAME");
        password = getIntent().getStringExtra("PASSWORD");
        usernameText.setText(username);
        passwordText.setText(password);
    }

    public void initializeComponents() {
        usernameText = findViewById(R.id.username_login);
        passwordText = findViewById(R.id.password_login);
        loginBtn = findViewById(R.id.button_login);
    }

    public void initializeListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                login(username, password);
            }
        });
    }

    public void login(String username, String password) {
        if (username.equals("")) {
            Toast.makeText(this, "Username must be defined", Toast.LENGTH_LONG).show();
        }
        if (password.equals("")) {
            Toast.makeText(this, "Password must be defined", Toast.LENGTH_LONG).show();
        }
        if (password.equals("pokedex") && username.equals("pokedex")) {
            Intent intent = new Intent(ActivityLogin.this, ActivityList.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username or passowrd incorrect", Toast.LENGTH_LONG).show();
        }
    }

    public void initializeData() {

    }
}
