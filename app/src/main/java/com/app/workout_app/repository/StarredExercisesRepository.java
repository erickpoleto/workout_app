package com.app.workout_app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.workout_app.database.CreateStarredDatabase;

public class StarredExercisesRepository {

    private SQLiteDatabase db;
    private CreateStarredDatabase database;

    public StarredExercisesRepository(Context context) {
        database = new CreateStarredDatabase(context);
    }

    public void insert(Integer exerciseId, Integer userId) {
        db = database.getWritableDatabase();
        ContentValues values;
        values = new ContentValues();
        values.put(CreateStarredDatabase.colNameExerciseId, exerciseId);
        values.put(CreateStarredDatabase.colNameUserId, userId);

        long result = db.insert(CreateStarredDatabase.tableName, null, values);
        db.close();
    }

    public Cursor fetch(Integer exerciseId, Integer userId) {
        db = database.getReadableDatabase();
        Cursor cursor;
        String[] campos =  {CreateStarredDatabase.colNameExerciseId, CreateStarredDatabase.colNameUserId};
        String where = "";
        if (exerciseId != null && userId != null) {
            where = CreateStarredDatabase.colNameExerciseId + " = " + exerciseId + " AND " +
                    CreateStarredDatabase.colNameUserId + " = " + userId;
        } else if (exerciseId == null && userId != null) {
            where = CreateStarredDatabase.colNameUserId + " = " + userId;
        } else {
            where = "";
        }
        cursor = db.query(CreateStarredDatabase.tableName, campos, where, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    public void delete(Integer exerciseId, Integer userId) {
        db = database.getReadableDatabase();

        String where = CreateStarredDatabase.colNameExerciseId + " = " + exerciseId + " AND " +
                CreateStarredDatabase.colNameUserId + " = " + userId;

        db.delete(CreateStarredDatabase.tableName, where, null);

        db.close();
    }
}
