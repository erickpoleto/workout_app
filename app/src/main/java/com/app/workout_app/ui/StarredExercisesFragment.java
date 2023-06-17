package com.app.workout_app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.workout_app.ExercisesActivity;
import com.app.workout_app.ExercisesItemActivity;
import com.app.workout_app.adapters.ExerciseAdapter;
import com.app.workout_app.constants.SharedIndexes;
import com.app.workout_app.database.CreateStarredDatabase;
import com.app.workout_app.databinding.FragmentStarredExercicesBinding;
import com.app.workout_app.models.Exercise;
import com.app.workout_app.models.RecyclerViewInterface;
import com.app.workout_app.repository.StarredExercisesRepository;
import com.app.workout_app.services.ExerciseService;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StarredExercisesFragment extends Fragment {
    ArrayList<Exercise> exercises = new ArrayList<>();
    private FragmentStarredExercicesBinding binding;
    private StarredExercisesRepository starredExercisesRepository;
    private  SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStarredExercicesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = this.getActivity().getSharedPreferences(SharedIndexes.loggedUserIndex, Context.MODE_PRIVATE);

        RecyclerView recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        new ExerciseService().fetch(this.getActivity().getApplicationContext(), response -> {
            starredExercisesRepository = new StarredExercisesRepository(getActivity().getBaseContext());

            Cursor starredExercises = starredExercisesRepository.fetch(null, sharedPreferences.getInt(SharedIndexes.loggedUserIdKey, 0));

            if (starredExercises.getCount() != 0) {

                while (starredExercises.moveToNext()) {
                    Exercise found = response.stream()
                            .filter(exercise -> exercise.getId().equals(starredExercises.getInt(0)))
                            .peek(res -> System.out.println(res))
                            .findFirst().orElse(null);
                    if (found != null) {
                        exercises.add(found);
                    };
                }
                ExerciseAdapter adapter = new ExerciseAdapter( this.getActivity(), exercises, null);
                recyclerView.setAdapter(adapter);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });

        binding.back2.setOnClickListener(event -> {
            Intent intent = new Intent(this.getActivity(), ExercisesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}