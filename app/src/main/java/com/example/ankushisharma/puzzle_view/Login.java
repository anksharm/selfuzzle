package com.example.ankushisharma.puzzle_view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class Login extends Activity {

    private static final String LOGIN_STATUS = "";
    EditText mUsername;
    EditText mPassword;
    Button mSubmit;
    Button mWannaRegister;
    Button mcancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            Parse.initialize(Login.this, "ed7ZVefe17JFFyPnQFrBbp4pfhIdrznrGqO8RGSk", "EsfAjbnU9KA5navFOt5QOKSPYkGFaoZSN1fPuQMm");

        } catch (Exception e){
            Log.d("except", String.valueOf(e));
        }
        mUsername = (EditText) findViewById(R.id.enter_username);
        mPassword = (EditText) findViewById(R.id.enter_password);

        mWannaRegister = (Button) findViewById(R.id.wanna_register);
        mWannaRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join = new Intent(Login.this, Signup.class);
                startActivity(join);
            }
        });

        mcancel = (Button) findViewById(R.id.cancel);
        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Login.this, HomeActivity.class);
                startActivity(home);
            }
        });

        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validate the edit text fields
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder("Please ");
                if (isEmpty(mUsername)) {
                    validationError = true;
                    validationErrorMessage.append("enter username");
                }
                if (isEmpty(mPassword)) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("password");
                }
                validationErrorMessage.append(".");

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(Login.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                final ProgressDialog dlg = new ProgressDialog(Login.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Logging in.  Please wait.");
                dlg.show();

                ParseUser.logInInBackground(mUsername.getText().toString(), mPassword.getText()
                        .toString(), new LogInCallback() {

                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            if (ParseUser.getCurrentUser() != null) {
                                // Start an intent for the logged in activity
                                Intent new_home = new Intent(Login.this, HomeActivity.class);
                                new_home.putExtra(LOGIN_STATUS, "LOGGEDIN");
                                new_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(new_home);
                            } else {
                                // Start and intent for the logged out activity
                                startActivity(new Intent(Login.this, Signup.class));
                            }

                        }
                    }
                });


            }
        });
    }

    private boolean isEmpty(EditText Text) {
        if (Text.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
