package com.app.workout_app.models;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class Exercise {
    Integer id;
    String name;
    String type;
    String muscle;
    String equipment;
    String difficulty;
    String instructions;

    String imageUrl;

    public Exercise(Integer id,
                    String name,
                    String type,
                    String muscle,
                    String equipment,
                    String difficulty,
                    String instructions,
                    String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    static public Exercise fromJson(JSONObject jsonObject) throws JSONException {
        return new Exercise(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("type"),
                jsonObject.getString("muscle"),
                jsonObject.getString("equipment"),
                jsonObject.getString("difficulty"),
                jsonObject.getString("instructions"),
                jsonObject.getString("imageUrl")
        );
    }
}
