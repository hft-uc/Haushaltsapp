package com.example.haushaltsapp.shopping

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.haushaltsapp.R
import com.example.haushaltsapp.types.ShoppingListEntry
import com.google.android.material.textfield.TextInputEditText

/**
 * A fragment representing a list of Items.
 */
class ShoppingDetailEntriesFragment : Fragment() {
    private lateinit var shoppingViewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireParentFragment()).get(
            ShoppingViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view =
            inflater.inflate(R.layout.fragment_shopping_detail_entries_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.shopping_detail_entry_list)

        val adapter = ShoppingDetailEntriesRecyclerViewAdapter(
            shoppingViewModel.createShoppingListEntriesAdapter(viewLifecycleOwner)
        ) { item: ShoppingListEntry? -> shoppingViewModel.toggleDone(item!!) }
        recyclerView.adapter = adapter

        initAddButton(view)

        return view
    }

    private fun initAddButton(view: View) {
        view.findViewById<View>(R.id.add_shopping_list_entry_fab)
            .setOnClickListener {
                val context = context
                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.add_entry)
                val layout = LinearLayout(context)
                layout.orientation = LinearLayout.VERTICAL
                val nameInput = TextInputEditText(context!!)
                nameInput.setHint(R.string.name)
                layout.addView(nameInput, 0)
                builder.setView(layout)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        val name = nameInput.text.toString()
                        shoppingViewModel.addEntry(name)
                    }
                    .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                    .show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.shopping_detail_entries, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clean_shopping_detail_entries) {
            clearShoppingList()
            return true
        }
        return false
    }

    private fun clearShoppingList() {
        shoppingViewModel.clearShoppingList()
    }
}