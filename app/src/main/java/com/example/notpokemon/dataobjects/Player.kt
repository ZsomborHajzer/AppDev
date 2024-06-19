package com.example.notpokemon.dataobjects

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.notpokemon.R
import com.google.gson.JsonObject

class Player(
    val id: String,
    val username: String,
    val role: String,
    var imageResource:Int = imageResources[0]
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
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
            Log.w("ImageCycling", "imageResource $imageResource, could not be found in list")
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
            var imageResource:Int
            if (jsonObject.get("imageResource") != null){
                imageResource = jsonObject.get("imageResource").asInt
            }
            else{
                imageResource = Player.imageResources[0]
            }
            return Player(
                jsonObject.get("id").asString,
                jsonObject.get("username").asString,
                jsonObject.get("role").asString,
                imageResource
            )
        }

        val imageResources = intArrayOf(R.drawable.low_res_tanuki, R.drawable.kel_fullbody_profile, R.drawable.char_aquaboy, R.drawable.char_emogirl, R.drawable.char_nerdyboy, R.drawable.char_spoopygirl)
    }

}