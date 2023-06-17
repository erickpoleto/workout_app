package com.app.workout_app.models;

import org.json.JSONObject;

public interface Deserializer<T> {
    T fromJson(JSONObject jsonObject);
}
