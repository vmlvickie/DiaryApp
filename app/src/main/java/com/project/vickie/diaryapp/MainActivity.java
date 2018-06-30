package com.project.vickie.diaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.CheckDB;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleLogInClient;



    DatabaseHelper helper = null;
    CheckDB checkDB = null;

    EditText etUsername;
    EditText etPassword;


    private int LOG_IN_CODE = 17;
    public String TAG = "com.project.vickie.diaryapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper = new DatabaseHelper(this);
        checkDB = new CheckDB(this);

       /* etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);*/

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleLogInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }

    public void onLogin(View view){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                logIn();
                break;
        }

    }

    private void logIn() {
        Intent logInIntent = googleLogInClient.getSignInIntent();

        startActivityForResult(logInIntent, LOG_IN_CODE);
    }

    @Override
    public void onActivityResult(int rqstCode, int resultCode, Intent data) {
        super.onActivityResult(rqstCode, resultCode, data);
        Log.d(TAG, "rqstCode:"+rqstCode+" LOG_IN_CODE:"+LOG_IN_CODE);
        if (rqstCode == LOG_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            logInSuccessful(account);
        } catch (ApiException e) {
            /*Log.w(TAG, "Log in Failed. Returned code: " + e.getStatusCode());*/
            e.printStackTrace();
            //logInSuccessful(null);
            Toast.makeText(this, "Sign In Unsuccesful", Toast.LENGTH_SHORT).show();
        }
    }

    private void logInSuccessful(GoogleSignInAccount googleSignInAccountObj) {
/*        String email = googleSignInAccountObj.getEmail();
        String id = googleSignInAccountObj.getId();
        String token = googleSignInAccountObj.getIdToken();*/
        //on succesful login take user to view entries
        Intent i = new Intent(this, Diary.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        logInSuccessful(account);
    }
}
