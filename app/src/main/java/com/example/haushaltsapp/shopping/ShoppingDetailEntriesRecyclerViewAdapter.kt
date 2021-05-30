package com.example.haushaltsapp.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haushaltsapp.R
import com.example.haushaltsapp.types.ShoppingListEntry
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import javax.annotation.Nonnull

class ShoppingDetailEntriesRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ShoppingListEntry>,
    private val listener: (ShoppingListEntry) -> Unit,
) : FirestoreRecyclerAdapter<ShoppingListEntry, ShoppingDetailEntriesRecyclerViewAdapter.ViewHolder>(
    options) {
    @Nonnull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shopping_detail_entries_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ShoppingListEntry) {
        holder.item = model
        holder.name.text = model.name
        holder.done.visibility = if (model.isDone) View.VISIBLE else View.INVISIBLE
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.shopping_entry_name)
        val done: ImageView = view.findViewById(R.id.shopping_entry_done)
        lateinit var item: ShoppingListEntry

        @Nonnull
        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }

        init {
            view.setOnClickListener { listener(item) }
        }
    }
}