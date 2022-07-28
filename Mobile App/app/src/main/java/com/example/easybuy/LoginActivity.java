package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easybuy.model.Member;
import com.example.easybuy.utility.MemberUtility;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    TextView warning;

    EditText emailBox;
    EditText passwordBox;

    Button login;

    SharedPreferences sharedPreferences;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#24A0ED")));

        sharedPreferences = this.getSharedPreferences("com.example.easybuy", Context.MODE_PRIVATE);

        warning = findViewById(R.id.warning);

        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);

        login = findViewById(R.id.login);

        email = sharedPreferences.getString("email",null);
        password = sharedPreferences.getString("password",null);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(email != null && password != null){
            Intent intent = new Intent(this,OrderActivity.class);
            startActivity(intent);
        }
    }

    public void login(View view) throws ExecutionException, InterruptedException {

        MemberUtility memberUtility = new MemberUtility();
        AsyncTask<String, Void, Member> member = memberUtility.login(emailBox.getText().toString(),passwordBox.getText().toString());


        if(member.get()==null || !member.get().getType().equals("Carrier")){
            warning.setVisibility(View.VISIBLE);
        }else{
            sharedPreferences.edit().putString("email",emailBox.getText().toString()).apply();
            sharedPreferences.edit().putString("password",passwordBox.getText().toString()).apply();
            sharedPreferences.edit().putString("name",member.get().getFirstName() + " " + member.get().getLastName()).apply();
            sharedPreferences.edit().putString("phone",member.get().getPhoneNumber()).apply();

            Toast.makeText(this, "Welcome " + member.get().getFirstName() + "!!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,OrderActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void recoverPassword(View view){
        Intent intent = new Intent(this,RecoverActivity.class);
        startActivity(intent);
    }
}