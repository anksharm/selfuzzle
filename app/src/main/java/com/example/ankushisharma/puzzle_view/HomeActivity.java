package com.example.ankushisharma.puzzle_view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;


public class HomeActivity extends Activity {

    Button mAboutbutton;
    Button mPlaybutton;
    Button mLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle log_val = getIntent().getExtras();
        mAboutbutton = (Button) findViewById(R.id.aboutbutton);
        mAboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle(R.string.title_about);
                builder.setMessage(R.string.content_about);
                builder.setPositiveButton(R.string.label_ok, null);
                builder.setIcon(R.drawable.selfu_02);
                AlertDialog dlg = builder.create();
                dlg.show();
                ((TextView) dlg.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        mPlaybutton = (Button) findViewById(R.id.playbutton);
        mPlaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               play(v);
            }
        });

        mLogin = (Button) findViewById(R.id.login);
        mLogin.setVisibility(View.VISIBLE);

        if (log_val != null){
            String Log_Status = log_val.getString("LOGIN_STATUS");
            Log.d("val of", "logged");
            mLogin.setVisibility(View.INVISIBLE);
           // mLogout.setVisibility(View.VISIBLE);

        }

        //add parse twitter login
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }

    public void play(View v){
        Intent intent = new Intent(this,start_puzzle_view.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (mLogin.getVisibility() != View.VISIBLE){
            getMenuInflater().inflate(R.menu.home, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ParseUser.getCurrentUser().logOut();
            mLogin.setVisibility(View.VISIBLE);
            Toast.makeText(HomeActivity.this, "Logged out successfully", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
