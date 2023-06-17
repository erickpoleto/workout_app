package com.app.workout_app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.workout_app.ExercisesItemActivity;
import com.app.workout_app.R;
import com.app.workout_app.adapters.ExerciseAdapter;
import com.app.workout_app.constants.SharedIndexes;
import com.app.workout_app.databinding.FragmentExercicesBinding;
import com.app.workout_app.models.Exercise;
import com.app.workout_app.models.RecyclerViewInterface;
import com.app.workout_app.models.RequestExecuted;
import com.app.workout_app.services.ExerciseService;

import org.json.JSONException;

import java.util.ArrayList;

public class ExercicesFragment extends Fragment implements RecyclerViewInterface {
    ArrayList<Exercise> exercises;
    private FragmentExercicesBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentExercicesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SharedIndexes.loggedUserIndex, Context.MODE_PRIVATE);
        String defaultValue = preferences.getString(SharedIndexes.loggedUserNameKey, "");
        binding.textviewFirst.setText("Olá, "+defaultValue);

        RecyclerView recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        new ExerciseService().fetch(this.getActivity().getApplicationContext(), response -> {
            exercises = response;
            ExerciseAdapter adapter = new ExerciseAdapter( this.getActivity(), exercises, this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClicked(int pos) {
        Intent intent = new Intent(this.getActivity(), ExercisesItemActivity.class);
        intent.putExtra(SharedIndexes.intentExerciseId, this.exercises.get(pos).getId().toString());
        System.out.println(intent.getStringExtra(SharedIndexes.intentExerciseId)+this.exercises.get(pos).getId().toString());
        startActivity(intent);
    }
}