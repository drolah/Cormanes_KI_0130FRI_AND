package com.example.bottomnavigation

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val profiles = mutableListOf<Profile>()

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var checkBoxStudent: CheckBox
    private lateinit var checkBoxSingle: CheckBox
    private lateinit var buttonSave: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        listView = view.findViewById(R.id.profileListView)
        editTextName = view.findViewById(R.id.editTextName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)
        checkBoxStudent = view.findViewById(R.id.checkBoxStudent)
        checkBoxSingle = view.findViewById(R.id.checkBoxSingle)
        buttonSave = view.findViewById(R.id.buttonSave)

        // Initialize adapter
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, profiles.map { it.name })
        listView.adapter = adapter


        // Save button click event
        buttonSave.setOnClickListener {
            saveProfile()
        }

        // ListView item click listener
        listView.setOnItemClickListener { _, _, position, _ ->
            showEditDeleteDialog(position)
        }
    }

    private fun saveProfile() {
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val gender = when (radioGroupGender.checkedRadioButtonId) {
            R.id.radioMale -> "Male"
            R.id.radioFemale -> "Female"
            else -> "Unknown"
        }
        val isStudent = checkBoxStudent.isChecked
        val isSingle = checkBoxSingle.isChecked

        // Add the new profile
        val profile = Profile(name, email, gender, isStudent, isSingle)
        profiles.add(profile)
        updateListView()

        // Clear input fields after saving
        editTextName.text.clear()
        editTextEmail.text.clear()
        radioGroupGender.clearCheck()
        checkBoxStudent.isChecked = false
        checkBoxSingle.isChecked = false
    }

    private fun updateListView() {
        adapter.clear()
        adapter.addAll(profiles.map { it.name })
        adapter.notifyDataSetChanged()
    }

    private fun showEditDeleteDialog(position: Int) {
        val selectedProfile = profiles[position]

        // Create dialog to show profile details and options to edit/delete
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose Action")
        builder.setMessage("Do you want to edit or delete this item?")

        // Edit button
        builder.setPositiveButton("Edit") { _, _ ->
            // Set current profile details in the input fields for editing
            editTextName.setText(selectedProfile.name)
            editTextEmail.setText(selectedProfile.email)
            if (selectedProfile.gender == "Male") {
                radioGroupGender.check(R.id.radioMale)
            } else {
                radioGroupGender.check(R.id.radioFemale)
            }
            checkBoxStudent.isChecked = selectedProfile.student
            checkBoxSingle.isChecked = selectedProfile.single

            // Update the list on save
            buttonSave.setOnClickListener {
                selectedProfile.name = editTextName.text.toString()
                selectedProfile.email = editTextEmail.text.toString()
                selectedProfile.gender = when (radioGroupGender.checkedRadioButtonId) {
                    R.id.radioMale -> "Male"
                    R.id.radioFemale -> "Female"
                    else -> "Unknown"
                }
                selectedProfile.student = checkBoxStudent.isChecked
                selectedProfile.single = checkBoxSingle.isChecked

                updateListView()

                // Reset save button to add new profiles
                buttonSave.setOnClickListener { saveProfile() }

                // Clear input fields after saving
                editTextName.text.clear()
                editTextEmail.text.clear()
                radioGroupGender.clearCheck()
                checkBoxStudent.isChecked = false
                checkBoxSingle.isChecked = false
            }
        }

        // Delete button
        builder.setNegativeButton("Delete") { _, _ ->
            profiles.removeAt(position)
            updateListView()
        }

        // Cancel button
        builder.setNeutralButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
}
