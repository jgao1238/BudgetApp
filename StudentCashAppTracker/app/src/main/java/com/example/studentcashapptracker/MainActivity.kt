package com.example.studentcashapptracker

import android.content.Intent
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
//            val edit = Intent(this@MainActivity, EditInfo::class.java)
//            startActivityForResult(edit,0)
        }

        val addExpenseButton = findViewById<View>(R.id.addExpenseButton) as Button
        addExpenseButton.setOnClickListener {
            val addExpense = Intent(this@MainActivity, AddExpense::class.java)
            startActivityForResult(addExpense,0)
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
//        val previousActivity = Intent(this@MainActivity, PreviousPeriods::class.java)
//        startActivityForResult(previousActivity,0 )
        build = AlertDialog.Builder(this).setMessage("Display Previous Months as clickable")
        val dialogBox = build.create()
        dialogBox.show()
        return true
    }
}
