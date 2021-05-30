package com.example.haushaltsapp.shopping

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.haushaltsapp.R
import com.example.haushaltsapp.types.ShoppingListSummary
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ShoppingRecyclerViewAdapter(options: FirestoreRecyclerOptions<ShoppingListSummary?>) :
    FirestoreRecyclerAdapter<ShoppingListSummary, ShoppingRecyclerViewAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ShoppingListSummary) {
        holder.setItem(model)
        holder.contentView.text = model.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shopping_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val contentView: TextView = view.findViewById(R.id.shopping_content)
        private lateinit var item: ShoppingListSummary

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        fun setItem(item: ShoppingListSummary) {
            this.item = item
        }

        init {
            view.setOnClickListener { v: View ->
                Log.i(TAG, "Navigating to shopping list with id " + item.id)
                Navigation.findNavController(v)
                    .navigate(ShoppingListFragmentDirections.actionNavShoppingToShoppingDetailFragment(
                        item.id))
            }
        }
    }

    companion object {
        private val TAG = ShoppingRecyclerViewAdapter::class.java.canonicalName
    }
}