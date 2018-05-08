package com.example.nicholas1.alphamobileapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //php
    private static final String REG_REQ_URL = "domain of web server/Register.php";
    private  Map<String, String> params;

    public RegisterRequest(String name, String username, String Surname, String Type, String password, Response.Listener<String> listener){
        super(Method.POST, REG_REQ_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("Surname", Surname);
        params.put("Type", Type);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
