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

class DetailViewModel(application: Application): AndroidViewModel(application) {

    val gameLD = MutableLiveData<Game>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(gameId:String) {

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.246.148/dataANMP/gameDetail.php?id=${gameId}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, { response ->
                gameLD.value = Gson().fromJson(response, Game::class.java)

                Log.d("showvolley", response)
            }, {
                Log.d("error", it.toString())
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