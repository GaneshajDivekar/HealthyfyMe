package com.example.me.databaseLocal.room


import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.me.data.db.SlotBookingEntity
import com.example.me.databaseLocal.room.dbHelper.DBUtils

@Database(
    entities = arrayOf(SlotBookingEntity::class),
    version = DBUtils.DB_VERSION, exportSchema = false
)
public abstract class DatabaseHelper : RoomDatabase() {

    abstract fun interfaceDao(): InterfaceDao


    companion object {

        private var INSTANCE: DatabaseHelper? = null

        fun getDatabase(context: Application): DatabaseHelper {
            if (INSTANCE == null) {
                synchronized(DatabaseHelper::class.java) {
                    if (INSTANCE == null) {
                        try {
                            INSTANCE = Room.databaseBuilder(
                                context,
                                DatabaseHelper::class.java,
                                DBUtils.DATABASE_NAME
                            )
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build()

                        } catch (e: Exception) {
                            Log.e("show", "e.message==" + e.message);
                        }
                    }
                }
            }
            return INSTANCE!!
        }
    }


}
