package com.example.hobbyappgame.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyappgame.databinding.FragmentProfileBinding
import com.example.hobbyappgame.model.User
import com.example.hobbyappgame.viewmodel.ProfileViewModel
import com.google.gson.Gson


class ProfileFragment : Fragment() {

    private lateinit var detailView: ProfileViewModel
    private lateinit var binding:FragmentProfileBinding

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    val userLD = MutableLiveData<User>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailView = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val profileUser = requireActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)
        var idUser = profileUser.getInt("id",0)
        Log.d("ID TES",idUser.toString())
        detailView.refresh(idUser.toString())

        detailView.userLD.observe(viewLifecycleOwner, Observer {
            Log.d("Tessssssss","Ga masuk detailview")

            if(idUser == null){
                Log.d("Tessssssss","IT NULL")
            }
            else{
                binding.txtEditFirstName.setText(it.namaDepan)
                binding.txtEditLastName.setText(it.namaBelakang)
                binding.txtEditPassword.setText(it.password)

                binding.btnUpdate.setOnClickListener {

                    var firstName = binding.txtEditFirstName.text.toString()
                    var lastName = binding.txtEditLastName.text.toString()
                    var password = binding.txtEditPassword.text.toString()
                    updateUser(firstName, lastName, password)

//                    updateUser(binding.txtEditUsername.text.toString(), binding.txtEditFirstName.text.toString(), binding.txtEditLastName.text.toString(), binding.txtEditPassword.text.toString())
                }
            }
            binding.btnLogOut.setOnClickListener {
                val logoutPref = requireActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)
                var logOut = logoutPref.edit()

                logOut.remove("id")

                val action = ProfileFragmentDirections.actionitemProfileloginActivity()
                Navigation.findNavController(view).navigate(action)
            }
        })
    }

    private fun updateUser(firstName: String,lastName:String, password: String) {
        if (firstName.isEmpty() || lastName.isEmpty() ||password.isEmpty()) {
            Toast.makeText(requireActivity(), "Please enter every data for update", Toast.LENGTH_SHORT).show()
            return
        }

        val profileUser = requireActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)
        var idUser = profileUser.getInt("id",0)


        queue = Volley.newRequestQueue(requireContext())
        val url = "http://192.168.246.148/dataANMP/updateProfile.php?id=${idUser}&namaDepan=${firstName}&namaBelakang=${lastName}&password=${password}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, { response ->
                userLD.value = Gson().fromJson(response, User::class.java)
//                if (userLD.value != null) {
//
//                }
            }, {
                Log.d("error", it.toString())
                Toast.makeText(requireActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }


}