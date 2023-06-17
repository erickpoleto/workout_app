package com.app.workout_app.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.workout_app.ExercisesActivity;
import com.app.workout_app.databinding.FragmentExerciseItemBinding;
import com.app.workout_app.repository.StarredExercisesRepository;
import com.app.workout_app.services.ExerciseService;

public class ExerciseItem extends Fragment {

    private FragmentExerciseItemBinding binding;
    private Boolean startAdded = false;
    private StarredExercisesRepository starredExercises;
    private Integer exerciseId;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentExerciseItemBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        starredExercises = new StarredExercisesRepository(getActivity().getBaseContext());
        exerciseId = Integer.parseInt(this.getActivity().getIntent().getExtras().getString("exerciseId"));

        Cursor fetchedCursor = starredExercises.fetch(exerciseId, 10);

        if (fetchedCursor.getCount() == 0) {
            binding.starOn.setVisibility(View.INVISIBLE);
            startAdded = false;
        } else {
            startAdded = true;
        }

        new ExerciseService().get(
            exerciseId,
            this.getActivity().getApplicationContext(),
            response -> {
                binding.textviewFirstExName.setText(response.getName());
                binding.descriptionView.setText(response.getInstructions());
            }
        );
        binding.back.setOnClickListener(click -> {
            Intent intent = new Intent(this.getActivity(), ExercisesActivity.class);
            startActivity(intent);
        });
        binding.starOff.setOnClickListener(click -> {
            changeStarred();
        });

        binding.starOn.setOnClickListener(click -> {
            changeStarred();
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void changeStarred() {
        if (startAdded) {
            binding.starOn.setVisibility(View.INVISIBLE);
            startAdded = false;
            starredExercises.delete(exerciseId, 10);
        } else {
            binding.starOn.setVisibility(View.VISIBLE);
            startAdded = true;
            starredExercises.insert(exerciseId, 10);
        }

    }

}