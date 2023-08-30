package com.example.sayurr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sayurr.databinding.ItemOnCartBinding

internal class CartRVAdapter(private val dataSet: ArrayList<dataRV>) : RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {
    var listener : RecyclerViewClickListener? = null
    private val data = dataSet

    inner class ViewHolder(binding: ItemOnCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName : TextView
        val itemPrice : TextView
        val category : TextView
        val itemRule : TextView
        var amount : TextView
        val buttonAdd : ImageButton
        val buttonMin : ImageButton
        val buttonDel : ImageButton
        val item : ConstraintLayout

        init {
            itemName = binding.textMerk
            itemPrice = binding.textPrice
            category = binding.textCat
            itemRule = binding.textRule
            amount = binding.textAmount
            buttonAdd = binding.buttonAddSum
            buttonMin = binding.buttonMinSum
            buttonDel = binding.buttonDelSum
            item = binding.itemLayoutCart
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOnCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = dataSet.get(position)
        holder.itemName.text = result.getName()
        holder.itemPrice.text = "Rp"+result.getPrice().toString()
        holder.itemRule.text = "/ "+result.getRule()
        holder.category.text = result.getCat()
        holder.amount.text = result.getAmount().toString()

        holder.buttonAdd.setOnClickListener {
            listener?.onClick(it, data[position])
            holder.amount.text = result.getAmount().toString()
        }
        holder.buttonMin.setOnClickListener {
            listener?.onClick(it, data[position])
            holder.amount.text = result.getAmount().toString()
        }
        holder.buttonDel.setOnClickListener {
            listener?.onClick(it, data[position])
            holder.amount.text = result.getAmount().toString()
        }
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