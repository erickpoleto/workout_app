package com.app.workout_app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.workout_app.R;
import com.app.workout_app.models.Exercise;
import com.app.workout_app.models.RecyclerViewInterface;
import com.app.workout_app.services.DownloadImageTask;

import java.io.InputStream;
import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    final RecyclerViewInterface recyclerViewInterface;
    ArrayList<Exercise> exercises;
    Context context;

    // Constructor for initialization
    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.exercises = exercises;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_item, parent, false);

        // Passing view to ViewHolder
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(view, recyclerViewInterface);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ViewHolder holder, int position) {
        if (this.exercises.get(position).getImageUrl() != null) {
            new DownloadImageTask(holder.images)
                    .execute(this.exercises.get(position).getImageUrl());
        } else {
            holder.images.setImageResource(R.drawable.ic_launcher_foreground);
        }
        holder.text.setText(exercises.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView text;

        public ViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            images = view.findViewById(R.id.exerciseImage);
            text = view.findViewById(R.id.exerciseName);
            view.setOnClickListener(view1 -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClicked(pos);
                    }
                }
            });
        }
    }
}