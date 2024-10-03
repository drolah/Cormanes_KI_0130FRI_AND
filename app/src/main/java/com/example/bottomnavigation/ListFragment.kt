package com.example.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var listView: ListView
    private lateinit var editTextItem: EditText
    private lateinit var buttonAdd: Button
    private val items = mutableListOf<Item>()
    private lateinit var adapter: ItemAdapter
    private var clicked = 0
    private var position1 = 0
    private var position2 = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        listView = view.findViewById(R.id.listView)
        editTextItem = view.findViewById(R.id.editTextItem)
        buttonAdd = view.findViewById(R.id.buttonAdd)

        // Set up adapter for ListView
        adapter = ItemAdapter()
        listView.adapter = adapter

        // Add new item
        buttonAdd.setOnClickListener {
            val newItemText = editTextItem.text.toString().trim()
            if (newItemText.isNotEmpty()) {
                items.add(Item(newItemText))
                adapter.notifyDataSetChanged()
                editTextItem.text.clear() // Clear input
            }
        }

        // Handle item click for edit/delete functionality
        listView.setOnItemClickListener { _, _, position, _ ->
            clicked += 1

            if (clicked == 1) {
                position1 = position
            } else {
                position2 = position
                if (position1 == position2) {
                    showEditDeleteDialog(position)
                } else {
                    position1 = position2
                    clicked -= 1
                }
            }
        }
    }

    // Show dialog to edit or delete an item
    private fun showEditDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose Action")
        builder.setMessage("Do you want to edit or delete this item?")

        builder.setPositiveButton("Edit") { _, _ ->
            val item = items[position]
            val editText = EditText(requireContext())
            editText.setText(item.text)

            val editDialog = AlertDialog.Builder(requireContext())
                .setTitle("Edit Item")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val newText = editText.text.toString().trim()
                    if (newText.isNotEmpty()) {
                        item.text = newText
                        adapter.notifyDataSetChanged()
                        clicked = 0
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()
            clicked = 0
            editDialog.show()
        }

        builder.setNegativeButton("Delete") { _, _ ->
            items.removeAt(position)
            adapter.notifyDataSetChanged()
            clicked = 0
        }

        builder.setNeutralButton("Cancel", null)
        val dialog = builder.create()
        clicked = 0
        dialog.show()
    }

    // Adapter for list items
    inner class ItemAdapter : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(requireContext())
                .inflate(R.layout.list_item, parent, false)

            val item = items[position]
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            val textItem = view.findViewById<TextView>(R.id.textItem)
            val imageItem = view.findViewById<ImageView>(R.id.imageItem)

            textItem.text = item.text
            checkBox.isChecked = item.isChecked
            imageItem.setImageResource(R.drawable.ic_launcher_foreground)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }

            return view
        }
    }

    // Data class for each list item
    data class Item(
        var text: String,
        var isChecked: Boolean = false
    )
}
