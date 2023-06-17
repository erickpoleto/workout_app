package com.app.workout_app.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    static public User fromJson(JSONObject jsonObject) throws JSONException {
        return new User(
                jsonObject.getString("name"),
                jsonObject.getString("email")
        );
    }
}
