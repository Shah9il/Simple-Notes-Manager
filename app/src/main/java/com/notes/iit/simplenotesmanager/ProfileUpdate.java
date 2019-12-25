package com.notes.iit.simplenotesmanager;

import android.database.Cursor;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileUpdate extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Integer usrID;
    String usrEmail,usrPass;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    Button buttonSave;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        sqliteHelper = new SqliteHelper(this);

        Cursor userData = sqliteHelper.retriveUser();
        if(userData.getCount() == 0){
            //message
        }
        while(userData.moveToNext()){
            usrID = userData.getInt(0);
            usrEmail = userData.getString(1);
            usrPass = userData.getString(2);
        }

        initTextViewNotesList();
        initViews(usrEmail,usrPass);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();

                    if (usrID == 1) {

                        sqliteHelper.updateUserProfile(usrID,email,password);
                        Snackbar.make(buttonSave, "Information saved successfully! ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        Snackbar.make(buttonSave, "Could not save data properly ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    private void initTextViewNotesList() {
        TextView textViewNotesList = (TextView) findViewById(R.id.textViewNotesList);
        textViewNotesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews(String usrEmail,String usrPass) {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextEmail.setText(usrEmail, TextView.BufferType.EDITABLE);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword.setText(usrPass, TextView.BufferType.EDITABLE);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        buttonSave = (Button) findViewById(R.id.buttonSave);

    }

    public boolean validate() {
        boolean valid = false;

        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            valid = true;
            textInputLayoutPassword.setError(null);
        }

        return valid;
    }
}
