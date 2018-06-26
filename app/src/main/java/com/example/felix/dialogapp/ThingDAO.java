package com.example.felix.dialogapp;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ThingDAO {

    @Query("SELECT * FROM Thing")
    List<Thing> getAll();

    @Query("SELECT * FROM Thing where name LIKE :name")
    Thing findByName(String name);

    @Query("SELECT * FROM Thing where name = :ID")
    Thing findByID(int ID);

    @Query("SELECT COUNT(*) from Thing")
    int count();

    @Insert
    void insertAll(Thing... things);

    @Insert
    void insert(Thing thing);

    @Update
    void update(Thing thing);

    @Delete
    void delete(Thing thing);

    @Query("DELETE FROM Thing")
    void deleteAll();

}