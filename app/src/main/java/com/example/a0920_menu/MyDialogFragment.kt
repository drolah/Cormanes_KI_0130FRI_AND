package com.example.a0920_menu

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Choose")
                .setMessage("Yes: Profile   No: Exit")
                .setPositiveButton("Yes") { dialog, id ->
                    // Positive action: Go to profile
                    (activity as? MainActivity)?.loadFragment(FirstFragment())
                }
                .setNegativeButton("No") { dialog, id ->
                    // Negative action: Exit App
                    activity?.finishAffinity()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}