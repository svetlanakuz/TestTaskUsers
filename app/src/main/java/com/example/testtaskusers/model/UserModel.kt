package com.example.testtaskusers.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class UserModel(
    @PrimaryKey val id: Int,
    val isActive: Boolean,
    val name: String,
    val age: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val eyeColor: String,
    val favoriteFruit: String,
    val registered: String,
    val latitude: String,
    val longitude: String,
    @TypeConverters(FriendsConverter::class)
    val friends: List<Friend>
) : Parcelable

@Parcelize
data class Friend(
    val id: Int
) : Parcelable

class FriendsConverter {
    var gson = Gson()

    @TypeConverter
    fun fromFriends(friends: List<Friend>): String {
        val type = object : TypeToken<List<Friend>>() {}.type
        return gson.toJson(friends, type)
    }

    @TypeConverter
    fun toFriends(data: String): List<Friend> {
        val type = object : TypeToken<List<Friend>>() {}.type
        return gson.fromJson(data, type)
    }
}
