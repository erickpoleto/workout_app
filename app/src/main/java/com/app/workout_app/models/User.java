package com.app.workout_app.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    Integer id;
    private String name;
    private String email;

    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    static public User fromJson(JSONObject jsonObject) throws JSONException {
        return new User(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("email")
        );
    }
}
