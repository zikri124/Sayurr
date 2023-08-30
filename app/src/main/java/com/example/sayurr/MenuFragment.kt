package com.example.sayurr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.sayurr.databinding.FragmentMenuBinding
import com.google.firebase.auth.FirebaseAuth

class MenuFragment : Fragment(R.layout.fragment_menu), View.OnClickListener {
    private var menuBinding : FragmentMenuBinding? = null
    private lateinit var binding : FragmentMenuBinding
    private lateinit var btnEditProfile : Button
    private lateinit var btnAddressList : Button
    private lateinit var btnInsight : Button
    private lateinit var btnCallUs : Button
    private lateinit var btnLogout : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)
        menuBinding = binding
        setView()
        setUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        menuBinding = null
    }

    private fun setView() {
        btnEditProfile = binding.btnEditProfile
        btnAddressList = binding.btnAddressList
        btnCallUs = binding.btnCallUs
        btnInsight = binding.btnInsight
        btnLogout = binding.btnLogout

        btnEditProfile.setOnClickListener(this)
        btnAddressList.setOnClickListener(this)
        btnInsight.setOnClickListener(this)
        btnCallUs.setOnClickListener(this)
        btnLogout.setOnClickListener(this)
    }


    private fun setUser() {
        val bundle = arguments
        binding.textName.text = bundle?.getString("name")
        binding.textEmail.text = bundle?.getString("email")
        binding.textPhone.text = bundle?.getString("phone")
    }

    override fun onClick(view : View?) {
        when(view?.id) {
            btnEditProfile.id -> {

            }
            btnAddressList.id -> {

            }
            btnCallUs.id -> {

            }
            btnInsight.id -> {

            }
            btnLogout.id -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent (activity, LoginActivity::class.java)
                activity?.startActivity(intent)
                activity?.finishAffinity()
            }
        }
    }
}