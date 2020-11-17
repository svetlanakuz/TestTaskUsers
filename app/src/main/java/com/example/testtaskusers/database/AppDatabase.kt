package com.example.testtaskusers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.testtaskusers.model.FriendsConverter
import com.example.testtaskusers.model.UserModel
import com.example.testtaskusers.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(entities = [UserModel::class], version = 1)
@TypeConverters(FriendsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userModelDao(): UserModelDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context, repository: UserRepository): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "database.db"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch {
                            INSTANCE!!.userModelDao().insertAll(repository.getUserList())
                        }
                    }
                }).build()
            }
            return INSTANCE as AppDatabase
        }
    }
}
