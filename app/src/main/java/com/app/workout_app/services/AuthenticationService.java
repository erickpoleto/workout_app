package com.app.workout_app.services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.app.workout_app.models.Auth;
import com.app.workout_app.models.Exercise;
import com.app.workout_app.models.RequestExecuted;
import com.app.workout_app.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AuthenticationService {

    final String url = "http://10.0.2.2:3000";
    final String path = "/users";

    public void login(Auth auth, Context context, RequestExecuted<User> requestExecuted) {
        RequestQueue queue = Volley.newRequestQueue(context);
        System.out.println(this.url + this.path + "?username="+auth.getUsername() + "&password="+ auth.getPassword());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                this.url + this.path + "?username="+auth.getUsername() + "&password="+ auth.getPassword(), null,
                response -> {
                    System.out.println("here");
                    User user = null;
                    try {
                        user = response.length() > 0 ? User.fromJson(response.getJSONObject(0)) : null;
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    requestExecuted.onRequestDone(user);

                },
                error->{});
        queue.add(request);

    }
}