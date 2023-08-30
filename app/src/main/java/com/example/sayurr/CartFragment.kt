package com.example.sayurr

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sayurr.databinding.FragmentCartBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class CartFragment : Fragment(R.layout.fragment_cart), CartRVAdapter.RecyclerViewClickListener {
    private lateinit var mDB : FirebaseFirestore
    private lateinit var binding : FragmentCartBinding
    private lateinit var dataCartRVAdapter: CartRVAdapter
    private var cartBinding : FragmentCartBinding? = null
    private var documents : QuerySnapshot? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        cartBinding = binding
        setData()
        getItemsData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartBinding = null
    }

    private fun setData() {
        mDB = FirebaseFirestore.getInstance()
    }

    private fun getItemsData() {
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
        val dataset = ArrayList<dataRV>()
        for (document in documents!!) {
            val cat = document.data.getValue("category").toString()
            val name = document.data.getValue("name").toString()
            val price = document.data.getValue("price").toString().toInt()
            val rule = document.data.getValue("rule").toString()
            val data = dataRV(name, price, rule, cat)
            dataset.add(data)
        }
        setRecyclerView(dataset)
        dataCartRVAdapter.listener = this
    }

    private fun setRecyclerView(dataset : ArrayList<dataRV>) {
        val recyclerView : RecyclerView = binding.recycleViewCart
        dataCartRVAdapter = CartRVAdapter(dataset)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dataCartRVAdapter
    }

    override fun onClick(view: View, data: dataRV) {
        if (view.id == R.id.buttonAddSum) {
            if (data.getAmount() < 999) {
                data.setAmount(data.getAmount()+1)
            }
        } else if (view.id == R.id.buttonMinSum) {
            if (data.getAmount() > 0) {
                data.setAmount(data.getAmount()-1)
            }
        } else if (view.id == R.id.buttonDelSum) {
            data.setAmount(0)
        }

        Log.d("Tess", data.getName())
    }

}