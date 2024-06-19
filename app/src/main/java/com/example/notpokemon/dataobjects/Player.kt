package com.example.notpokemon.dataobjects

import android.os.Parcel
import android.os.Parcelable
import com.example.notpokemon.R
import com.google.gson.JsonObject

data class Player(
    val id: String,
    val username: String,
    val role: String,
    var imageResource: Int = R.drawable.kel_fullbody_profile
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

    fun cycleImage(){
        val currentIndex = imageResources.indexOf(imageResource)
        if (currentIndex == -1){
            imageResource = imageResources[0]
        }
        else if (currentIndex == imageResources.size-1){
            imageResource = imageResources[0]
        }
        else{
            imageResource = imageResources[currentIndex+1]
        }
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

        val imageResources = intArrayOf(R.drawable.kel_fullbody_profile, R.drawable.low_res_tanuki)
    }

}