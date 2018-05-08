package com.example.nicholas1.alphamobileapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUserNameLog = (EditText) findViewById(R.id.etNameLog);
        final EditText etPassLog= (EditText) findViewById(R.id.etPassLog);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView tvForgot= (TextView) findViewById(R.id.tvForgot);
        final TextView tvReg = (TextView) findViewById(R.id.tvReg);

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUserNameLog.getText().toString();
                final String password = etPassLog.getText().toString();

                Response.Listener<String> logListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //php
                        try {
                            JSONObject jsonLog = new JSONObject(response);
                            //php
                            boolean sucess = jsonLog.getBoolean("sucess");

                            if (sucess){
                                Intent intent = new Intent(LoginActivity.this, ExhibitionActivity.class);
                                LoginActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login failed");
                                builder.setNegativeButton("@string/Retry", null);
                                builder.create();
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, logListener);
                RequestQueue Logqueue = Volley.newRequestQueue(LoginActivity.this);
                Logqueue.add(loginRequest);

            }
        });
    }
}
