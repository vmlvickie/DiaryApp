package com.project.vickie.diaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.CheckDB;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    DatabaseHelper helper = null;
    CheckDB checkDB = null;

    EditText etEmail;
    EditText etPassword;
    EditText etPasswordConfirm;
    Button buttonRegister;
    TextView tvSingIn;

    FirebaseAuth firebaseAuth;



    private int LOG_IN_CODE = 17;
    public String TAG = "com.project.vickie.diaryapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Intent i = new Intent(this, Diary.class);
            startActivity(i);
        }

        helper = new DatabaseHelper(this);
        checkDB = new CheckDB(this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);

        tvSingIn = (TextView) findViewById(R.id.tvSingIn);
        tvSingIn.setOnClickListener(this);

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

    private void registerUser(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etPasswordConfirm.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        //create user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            //got to Diary activity
                            finish();
                            Intent i = new Intent(MainActivity.this, Diary.class);
                            startActivity(i);

                        }else{
                            Toast.makeText(MainActivity.this, "Unable to register. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == tvSingIn){
            //will open log in activity
            finish();
            Intent i = new Intent(this, LogInActivity.class);
            startActivity(i);;
        }
    }


    /*private void onRegister(View view) {
        Intent logInIntent = googleLogInClient.getSignInIntent();

        startActivityForResult(logInIntent, LOG_IN_CODE);
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
            *//*Log.w(TAG, "Log in Failed. Returned code: " + e.getStatusCode());*//*
            e.printStackTrace();
            //logInSuccessful(null);
            Toast.makeText(this, "Sign In Unsuccesful", Toast.LENGTH_SHORT).show();
        }
    }

    private void logInSuccessful(GoogleSignInAccount googleSignInAccountObj) {
*//*        String email = googleSignInAccountObj.getEmail();
        String id = googleSignInAccountObj.getId();
        String token = googleSignInAccountObj.getIdToken();*//*
        //on succesful login take user to view entries
        Intent i = new Intent(this, Diary.class);
        startActivity(i);
        finish();
    }*/

   @Override
    protected void onStart() {
        super.onStart();
       if(firebaseAuth.getCurrentUser() != null){
           Intent i = new Intent(this, Diary.class);
           startActivity(i);
       }
    }
}
