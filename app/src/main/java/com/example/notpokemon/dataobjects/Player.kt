package com.example.notpokemon.dataobjects

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject

data class Player(
    val id: String,
    val username: String,
    val role: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }

        fun fromJsonObject(jsonObject:JsonObject):Player{
            return Player(
                jsonObject.get("id").asString,
                jsonObject.get("username").asString,
                jsonObject.get("role").asString
            )
        }
    }

}