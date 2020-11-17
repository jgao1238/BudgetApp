package com.example.studentcashapptracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var build: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editButton = findViewById<View>(R.id.editButton) as Button
        editButton.setOnClickListener {

            build = AlertDialog.Builder(this).setTitle("Edit")
            val dialogBox = build.create()
            dialogBox.show()
        }


        val addExpenseButton = findViewById<View>(R.id.addExpenseButton) as Button
        addExpenseButton.setOnClickListener {
            build = AlertDialog.Builder(this).setTitle("Add Expense")
            val customLayout: View = layoutInflater.inflate(R.layout.add_expense, null)

//            add button
            build.setPositiveButton("Add") { dialog, which ->
                val category = customLayout.findViewById<EditText>(R.id.category)
                Toast.makeText(this, category.text, Toast.LENGTH_SHORT).show()
                Thread.sleep(1000L)
                val cost = customLayout.findViewById<EditText>(R.id.cost)
                Toast.makeText(this, cost.text, Toast.LENGTH_SHORT).show()
                Thread.sleep(1000L)
                val date = customLayout.findViewById<EditText>(R.id.date)
                Toast.makeText(this, date.text, Toast.LENGTH_SHORT).show()
                Thread.sleep(1000L)
                val description = customLayout.findViewById<EditText>(R.id.description)
                Toast.makeText(this, description.text, Toast.LENGTH_SHORT).show()
            }
//            cancel button
            build.setNegativeButton("Cancel") { dialog, which ->
            }


            build.setView(customLayout)
            val dialogBox = build.create()
            dialogBox.show()
        }

    }
    //    crate the menu in the top right of the screen
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_file, menu)
        return true
    }
//    menu dialog
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        build = AlertDialog.Builder(this).setMessage("Display Previous Months as clickable")
        val dialogBox = build.create()
        dialogBox.show()
        return true
    }
}
