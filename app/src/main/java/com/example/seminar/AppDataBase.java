package com.example.seminar;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Avion.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract AvionDao avionDao();
}
