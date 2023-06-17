package com.app.workout_app.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.workout_app.constants.Envs;
import com.app.workout_app.models.Auth;
import com.app.workout_app.models.Exercise;
import com.app.workout_app.models.RequestExecuted;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ExerciseService {

    final String url = Envs.API_URL;
    final String path = "/exercises";


    public void fetch(Context context, RequestExecuted<ArrayList<Exercise>> requestExecuted) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                this.url + this.path, null,
                response -> {
                    ArrayList<Exercise> exercises = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            exercises.add(Exercise.fromJson(response.getJSONObject(i)));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    requestExecuted.onRequestDone(exercises);

                },
                error->{});
        queue.add(request);
    }

    public void get(Integer id, Context context, RequestExecuted<Exercise> requestExecuted) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                this.url + this.path + "?id=" + id, null,
                response -> {
                    Exercise exercise = null;
                    try {
                        System.out.println(id);
                        exercise = Exercise.fromJson(response.getJSONObject(0));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    requestExecuted.onRequestDone(exercise);

                },
                error->{});
        queue.add(request);
    }
}