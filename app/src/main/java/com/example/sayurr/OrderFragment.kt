package com.example.sayurr

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sayurr.databinding.FragmentOrderBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

class OrderFragment : Fragment(R.layout.fragment_order), OrderRVAdapter.RecyclerViewClickListener {
    private lateinit var mDB : FirebaseFirestore
    private lateinit var binding : FragmentOrderBinding
    private lateinit var dataOrderRVAdapter: OrderRVAdapter
    private var orderBinding : FragmentOrderBinding? = null
    private var documents : QuerySnapshot? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderBinding.bind(view)
        orderBinding = binding
        setData()
        setRadioListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        orderBinding = null
    }

    private fun setData() {
        mDB = FirebaseFirestore.getInstance()
    }

    private fun setRecyclerView(dataset: ArrayList<dataRVOrder>) {
        val recyclerView : RecyclerView = binding.recycleViewOrder
        dataOrderRVAdapter = OrderRVAdapter(dataset)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dataOrderRVAdapter
    }

    private fun getItemsData(category : String) {
        mDB.collection("items")
            .get()
            .addOnSuccessListener { datas ->
                documents = datas
                setItemsData()
            }
            .addOnFailureListener { exception ->
                Log.w("DataFromFs", "Error getting documents: ", exception)
            }
    }

    private fun setItemsData() {
        val dataset = ArrayList<dataRVOrder>()
        for (document in documents!!) {
            val cat = document.data.getValue("category").toString()
            val name = document.data.getValue("name").toString()
            val price = document.data.getValue("price").toString().toInt()
            val rule = document.data.getValue("rule").toString()
            val data = dataRVOrder(name, price, rule, cat)
            dataset.add(data)
        }
        setRecyclerView(dataset)
        dataOrderRVAdapter.listener = this
    }

    override fun onClick(view: View, data: dataRVOrder) {
        TODO("Not yet implemented")
    }

    private fun setRadioListener() {
        var checked = binding.radioProgress
        var button = binding.radioProgress
        binding.radioGroupOrder.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioDone -> {
                    button = binding.radioDone
                }
                R.id.radioProgress -> {
                    button = binding.radioProgress
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

}