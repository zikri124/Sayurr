package com.example.sayurr

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sayurr.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home), HomeRVAdapter.RecyclerViewClickListener {
    private lateinit var mDB : FirebaseFirestore
    private lateinit var binding : FragmentHomeBinding
    private lateinit var dataHomeRVAdapter: HomeRVAdapter
    private var homeBinding : FragmentHomeBinding? = null
    private var documents : QuerySnapshot? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        homeBinding = binding
        setData()
        setUser()
        setRadioListener()
        getItemsData("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null
    }

    private fun setData() {
        mDB = FirebaseFirestore.getInstance()
    }

    private fun setUser() {
        val bundle = arguments
        binding.textHai.text = "Hai, " + bundle!!.getString("name")
    }

    private fun setRecyclerView(dataset: ArrayList<dataRV>) {
        val recyclerView : RecyclerView = binding.listItem
        dataHomeRVAdapter = HomeRVAdapter(dataset)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dataHomeRVAdapter
    }

    private fun getItemsData(category : String) {
        mDB.collection("items")
            .get()
            .addOnSuccessListener { datas ->
                documents = datas
                setItemsData(category)
            }
            .addOnFailureListener { exception ->
                Log.w("DataFromFs", "Error getting documents: ", exception)
            }
    }

    private fun setItemsData(category : String) {
        val dataset = ArrayList<dataRV>()
        for (document in documents!!) {
            val cat = document.data.getValue("category").toString()
            if ((category == "" || category == cat) && document.data.getValue("isAvailable").toString().toBoolean()) {
                val name = document.data.getValue("name").toString()
                val price = document.data.getValue("price").toString().toInt()
                val rule = document.data.getValue("rule").toString()
                val data = dataRV(name, price, rule, cat)
                dataset.add(data)
            }
        }
        setRecyclerView(dataset)
        dataHomeRVAdapter.listener = this
    }

    private fun setRadioListener() {
        var checked = binding.radioCRec
        var button = binding.radioCRec
        binding.radioGroupCat.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.radioCRec -> {
                    button = binding.radioCRec
                    setItemsData("")
                }
                R.id.radioCFruit -> {
                    button = binding.radioCFruit
                    setItemsData("Buah")
                }
                R.id.radioCVeg -> {
                    button = binding.radioCVeg
                }
                R.id.radioCEgg -> {
                    button = binding.radioCEgg
                }
                R.id.radioCMie -> {
                    button = binding.radioCMie
                    setItemsData("Mie Instan")
                }
            }
            setRadioColor(button, checked)
            checked = button
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setRadioColor(radioButton: RadioButton, radioButton2: RadioButton) {
        radioButton.background.setTint(resources.getColor(R.color.Primary))
        radioButton.setTextColor(resources.getColor(R.color.keabuan))

        radioButton2.background.setTint(resources.getColor(R.color.keabuan))
        radioButton2.setTextColor(resources.getColor(R.color.Primary))
    }

    override fun onClick(view: View, data: dataRV) {
        Log.d("Tess", data.getName())
    }
}