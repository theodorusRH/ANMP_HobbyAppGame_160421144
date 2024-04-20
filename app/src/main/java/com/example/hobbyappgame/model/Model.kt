package com.example.hobbyappgame.model

import com.google.gson.annotations.SerializedName

data class Game(
    val id:String?,
    val name:String?,
    val titleName:String?,
    val deskripsi:String?,
    val news:List<String>?,
    val photourl:String?
)

data class User(
    val id:String?,
    val username:String?,
    val namaDepan:String?,
    val namaBelakang:String?,
    val email:String?,
    val password:String?,
)