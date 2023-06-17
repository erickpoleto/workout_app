package com.app.workout_app.models;

public interface RequestExecuted<T> {
    void onRequestDone(T response);
}
