package com.example.studentcashapptracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var build: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//         val editButton = findViewById<View>(R.id.editButton) as Button
//         editButton.setOnClickListener {
// //            val edit = Intent(this@MainActivity, EditInfo::class.java)
// //            startActivityForResult(edit,0)
//         }

        val addExpenseButton = findViewById<Button>(R.id.addExpenseButton)
        addExpenseButton.setOnClickListener {
            val addExpense = Intent(this@MainActivity, AddExpense::class.java)
            startActivityForResult(addExpense,0)
        }

        val foodButton = findViewById<Button>(R.id.foodButton)
        foodButton.setOnClickListener {
            val toFood = Intent(this@MainActivity, Food::class.java)
            startActivity(toFood)
        }

        val carButton = findViewById<Button>(R.id.carButton)
        carButton.setOnClickListener {
            val toCar = Intent(this@MainActivity, Car::class.java)
            startActivity(toCar)
        }

        val healthButton = findViewById<Button>(R.id.healthButton)
        healthButton.setOnClickListener {
            val toHealth = Intent(this@MainActivity, Health::class.java)
            startActivity(toHealth)
        }

        val rentButton = findViewById<Button>(R.id.rentButton)
        rentButton.setOnClickListener {
            val toRent = Intent(this@MainActivity, Rent::class.java)
            startActivity(toRent)
        }

        val otherButton = findViewById<Button>(R.id.foodButton)
        otherButton.setOnClickListener {
            val toOther = Intent(this@MainActivity, Other::class.java)
            startActivity(toOther)
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
