package com.example.sayurr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sayurr.databinding.ItemOnMainBinding

internal class HomeRVAdapter(private val dataSet: ArrayList<dataRV>) : RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {
    var listener : RecyclerViewClickListener? = null
    private val data = dataSet

    inner class ViewHolder(binding: ItemOnMainBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName : TextView
        val itemPrice : TextView
        val category : TextView
        val itemRule : TextView
        val item : ConstraintLayout

        init {
            itemName = binding.textMerk
            itemPrice = binding.textPrice
            category = binding.textCat
            itemRule = binding.textRule
            item = binding.itemLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOnMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = dataSet.get(position)
        holder.itemName.text = result.getName()
        holder.itemPrice.text = "Rp"+result.getPrice().toString()
        holder.itemRule.text = "/ "+result.getRule()
        holder.category.text = result.getCat()

        holder.item.setOnClickListener {
            listener?.onClick(it, data[position])
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface RecyclerViewClickListener {
        fun onClick(view: View, data: dataRV)
    }
}

class dataRV(name : String, price : Int, rule : String, category: String) {
    private var name : String
    private var price : Int
    private var rule : String
    private var category : String
    private var amount : Int

    init {
        this.name = name
        this.price = price
        this.category = category
        this.rule = rule
        amount = 0
    }

    fun setName(name : String) {
        this.name = name
    }

    fun setPrice(price : Int) {
        this.price = price
    }

    fun setCategory(category: String) {
        this.category = category
    }

    fun setRule(rule: String) {
        this.rule = rule
    }

    fun setAmount(amount : Int) {
        this.amount = amount
    }

    fun getName() : String {
        return name
    }

    fun getPrice() : Int {
        return price
    }

    fun getCat() : String {
        return category
    }

    fun getRule() : String {
        return rule
    }

    fun getAmount() : Int {
        return amount
    }
}