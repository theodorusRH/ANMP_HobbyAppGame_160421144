package com.example.hobbyappgame.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyappgame.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    val userLD = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveRegister.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val namaDepan = binding.txtNamaDepan.text.toString().trim()
            val namaBelakang = binding.txtNamaBelakang.text.toString().trim()
            val email = binding.txtEmail.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()
            if(password == binding.txtRetypePassword.text.toString()){
                performRegister(username,namaDepan,namaBelakang,email,password)
            } else{
                Toast.makeText(this@RegisterActivity, "Password and ReType Password doesn't match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performRegister(username: String, namaDepan: String,namaBelakang: String,email:String,password: String){
        if (username.isEmpty() || namaDepan.isEmpty() || namaBelakang.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Please enter the remaining data", Toast.LENGTH_SHORT).show()
            return
        }
        queue = Volley.newRequestQueue(applicationContext)
        val url = "http://192.168.246.148/dataANMP/registerUser.php"

        val stringRequest = object :StringRequest(
            Request.Method.POST, url, { response ->
                userLD.value = true

                if (userLD.value != false) {

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
                else {
                    Log.d("error", response)
                    Toast.makeText(this@RegisterActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }, {
                Log.d("error", it.toString())
                Toast.makeText(this@RegisterActivity, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
            }
        )
        {
            override fun getParams(): MutableMap<String,String>?{
                val params = HashMap<String,String>()
                params["username"] = username
                params["namaDepan"] = namaDepan
                params["namaBelakang"] = namaBelakang
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }


}