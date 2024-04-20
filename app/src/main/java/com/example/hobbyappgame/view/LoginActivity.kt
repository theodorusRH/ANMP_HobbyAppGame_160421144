package com.example.hobbyappgame.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyappgame.databinding.ActivityLoginBinding
import com.example.hobbyappgame.model.User
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    val userLD = MutableLiveData<User>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {

            // Handle login button click
            val username = binding.txtLoginUsername.text.toString().trim()
            val password = binding.txtLoginPassword.text.toString().trim()


            performLogin(username, password)
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun performLogin(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Please enter username and password", Toast.LENGTH_SHORT).show()
            return
        }
        queue = Volley.newRequestQueue(applicationContext)
        val url = "http://192.168.246.148/dataANMP/loginUser.php?username=${username}&password=${password}"

        val dataLogin = this@LoginActivity.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)
        val loginValue = dataLogin.edit()

        val stringRequest = StringRequest(
            Request.Method.GET, url, { response ->
                userLD.value = Gson().fromJson(response, User::class.java)

                if (userLD.value != null) {
                    var idUsr = userLD.value?.id.toString()
                    loginValue.putInt("id",idUsr.toInt())
                    loginValue.apply()

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                else {
                    Log.d("error", response)
                    Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }, {
                Log.d("error", it.toString())
                Toast.makeText(this@LoginActivity, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)


    }
}