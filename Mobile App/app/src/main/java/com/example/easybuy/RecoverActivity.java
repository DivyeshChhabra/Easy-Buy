package com.example.easybuy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easybuy.utility.MailUtility;
import com.example.easybuy.utility.MemberUtility;

public class RecoverActivity extends AppCompatActivity {

    TextView warning;

    EditText emailBox;

    Button recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#24A0ED")));

        warning = findViewById(R.id.warning);
        emailBox = findViewById(R.id.emailBox);
        recover = findViewById(R.id.recover);
    }

    public void recover(View view) {

        try{
            MemberUtility memberUtility = new MemberUtility();

            String emailID = emailBox.getText().toString();
            AsyncTask<String, Void, String> password = memberUtility.recoverPassword(emailID);

            if(password.get() == null) warning.setVisibility(View.VISIBLE);

            String message = "Hi Dear!!\n" +
                    "Below is your Easy-Buy Login details\n\n" +
                    "EMail ID: " + emailID + "\n" +
                    "Password: " + password.get();
            String subject = "Account Recovery";

            MailUtility mailUtility = new MailUtility(emailID,message,subject);
            mailUtility.sendMail();

            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}