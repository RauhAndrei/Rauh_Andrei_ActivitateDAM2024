package com.example.seminar;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface AvionDao {
    @Insert
    void insert(Avion avion);

    @Update
    void update(Avion avion);

    @Delete
    void delete(Avion avion);

    @Query("DELETE FROM avioane")
    void deleteAllCourses();

    @Query("SELECT * FROM avioane")
    List<Avion> getAllAvioane();
}
