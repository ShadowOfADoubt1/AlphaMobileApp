package com.example.nicholas1.alphamobileapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPass = (EditText) findViewById(R.id.etPass);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.Surname);
        final EditText etRePass = (EditText) findViewById(R.id.etRePass);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final RadioButton rbBid= (RadioButton) findViewById(R.id.rbBid);
        final RadioButton rbEX = (RadioButton) findViewById(R.id.rbEx);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPass.getText().toString();
                final String Surname = etSurname.getText().toString();
                final String repassword = etRePass.getText().toString();
                String type = "";
                if(rbBid.isChecked()){
                    type = rbBid.getText().toString();
                }else if(rbEX.isChecked()){
                    type = rbEX.getText().toString();
                }
                if(!password.equals(repassword))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Passwords don't match");
                    builder.setNegativeButton("@string/Retry", null);
                    builder.create();
                    builder.show();
                }

                Response.Listener<String> resListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //php
                        try {
                            JSONObject jsonRes = new JSONObject(response);
                            //php
                            boolean sucess = jsonRes.getBoolean("sucess");

                            if (sucess){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration failed");
                                builder.setNegativeButton("@string/Retry", null);
                                builder.create();
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username, Surname, type, password, resListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
