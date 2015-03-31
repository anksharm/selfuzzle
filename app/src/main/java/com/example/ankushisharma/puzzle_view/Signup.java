package com.example.ankushisharma.puzzle_view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Signup extends Activity {

    Button mcancel;
    Button mSignup;

    EditText mUsername;
    EditText mPassword;
    EditText mPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Parse.initialize(this, "ed7ZVefe17JFFyPnQFrBbp4pfhIdrznrGqO8RGSk", "EsfAjbnU9KA5navFOt5QOKSPYkGFaoZSN1fPuQMm");

    mcancel = (Button) findViewById(R.id.cancel);
        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Signup.this, HomeActivity.class);
                startActivity(home);
            }
        });
        mUsername = (EditText) findViewById(R.id.signup_username);
        mPassword = (EditText) findViewById(R.id.signup_password);
        mPasswordAgain = (EditText) findViewById(R.id.signup_passwordAgain);

        mSignup = (Button) findViewById(R.id.signup_enter);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder("please ");
                if (isEmpty(mUsername)) {
                    validationError = true;
                    validationErrorMessage.append("enter username ");
                }
                if (isEmpty(mPassword)) {
                    if (validationError) {
                        validationErrorMessage.append(" and password ");
                    }
                    validationError = true;
                    validationErrorMessage.append(" or ");
                }
                if (!isMatching(mPassword, mPasswordAgain)) {
                    if (validationError) {
                        validationErrorMessage.append("or");
                    }
                    validationError = true;
                    validationErrorMessage.append(" Passwords do not match ");
                }
                validationErrorMessage.append("check username and passwords");

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(Signup.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(Signup.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();

                // Set up a new Parse user
                ParseUser user = new ParseUser();
                user.setUsername(mUsername.getText().toString());
                user.setPassword(mPassword.getText().toString());

                // Call the Parse signup method
                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Toast.makeText(Signup.this, R.string.signup_message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Signup.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }

            });
    }

    private boolean isMatching(EditText password, EditText passwordAgain) {
        if (password.getText().toString().equals(passwordAgain.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmpty(EditText empty) {
        if (empty.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
