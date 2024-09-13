package com.example.listviewtodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var editTextItem: EditText
    private lateinit var buttonAdd: Button
    private val items = mutableListOf<Item>()
    private lateinit var adapter: ItemAdapter
    private var clicked = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        editTextItem = findViewById(R.id.editTextItem)
        buttonAdd = findViewById(R.id.buttonAdd)

        // Custom adapter for handling the list items
        adapter = ItemAdapter()
        listView.adapter = adapter

        // Add new item to the list
        buttonAdd.setOnClickListener {
            val newItemText = editTextItem.text.toString().trim()
            if (newItemText.isNotEmpty()) {
                items.add(Item(newItemText))
                adapter.notifyDataSetChanged()
                editTextItem.text.clear() // Clear input
            }
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            clicked = clicked + 1

            if(clicked > 1){
                showEditDeleteDialog(position)
            }
        }
    }

    private fun showEditDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Action")

        builder.setMessage("Do you want to edit or delete this item?")
        builder.setPositiveButton("Edit") { _, _ ->
            // Allow editing the item
            val item = items[position]
            val editText = EditText(this@MainActivity) // Create a new EditText for input
            editText.setText(item.text)  // Set current item text

            val editDialog = AlertDialog.Builder(this)
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
            // Remove the item from the list
            items.removeAt(position)
            adapter.notifyDataSetChanged()
            clicked = 0
        }
        builder.setNeutralButton("Cancel", null)
        val dialog = builder.create()
        clicked = 0
        dialog.show()
    }


    inner class ItemAdapter : BaseAdapter() {
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.list_item, parent, false)

            val item = items[position]

            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            val textItem = view.findViewById<TextView>(R.id.textItem)
            val imageItem = view.findViewById<ImageView>(R.id.imageItem)

            textItem.text = item.text
            checkBox.isChecked = item.isChecked
            imageItem.setImageResource(R.drawable.ic_launcher_foreground) // Example image

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }

            return view
        }
    }

    // Data class for each item in the list
    data class Item(
        var text: String,
        var isChecked: Boolean = false
    )
}