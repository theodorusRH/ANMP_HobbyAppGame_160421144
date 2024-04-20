package com.example.hobbyappgame.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyappgame.model.Game

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GameListViewModel(application: Application):AndroidViewModel(application) {
    var gamesLD = MutableLiveData<ArrayList<Game>>()
    val gameLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun refresh() {

        gameLoadErrorLD.value = false
        loadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.246.148/dataANMP/game.json"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,{
                val sType = object : TypeToken<ArrayList<Game>>() { }.type
                val result = Gson().fromJson<List<Game>>(it, sType)
                gamesLD.value = result as ArrayList<Game>?

                loadingLD.value = false
                Log.d("showvolley",it)
            },
            {
                loadingLD.value = false
                gameLoadErrorLD.value=false
                Log.d("showvolley",it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

}