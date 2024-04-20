package com.example.hobbyappgame.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyappgame.model.User
import com.google.gson.Gson

class ProfileViewModel (application: Application): AndroidViewModel(application){
    val userLD = MutableLiveData<User>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(userId:String) {

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.246.148/dataANMP/userDetail.php?id=${userId}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, { response ->
                userLD.value = Gson().fromJson(response, User::class.java)

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