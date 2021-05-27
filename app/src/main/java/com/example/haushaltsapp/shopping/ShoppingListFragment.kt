package com.example.haushaltsapp.shopping

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.haushaltsapp.R
import com.example.haushaltsapp.shopping.ShoppingViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShoppingListFragment : Fragment() {

    private lateinit var shoppingViewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        shoppingViewModel = ViewModelProvider(this).get(ShoppingViewModel::class.java)

        val recyclerView: RecyclerView = root.findViewById(R.id.shopping_list)
        recyclerView.adapter = shoppingViewModel.createShoppingListAdapter(viewLifecycleOwner)

        root.findViewById<FloatingActionButton>(R.id.add_shopping_list_fab)
            .setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Neue Liste erstellen")
                // Set up the input
                val input = EditText(context)
                input.hint = "blabla"
                input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                builder.setView(input)
                builder.setPositiveButton("OK") { _, _ ->
                    val text = input.text.toString()
                    shoppingViewModel.add(text)
                }
                builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                builder.show()
            }
        return root
    }
}