package com.example.studentcashapptracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_expense.*

class AddExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense)

//        set up the category spinner
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
//      set up the 3 spinners for the date
        val monthSpinner: Spinner = findViewById(R.id.spinner3)
        ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            monthSpinner.adapter = adapter
        }
        val daySpinner: Spinner = findViewById(R.id.spinner4)
        ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            daySpinner.adapter = adapter
        }
        val yearSpinner: Spinner = findViewById(R.id.spinner5)
        ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.adapter = adapter
        }

//        TODO: commit to a database
        val addExpense = findViewById<View>(R.id.addButton) as Button
        addExpense.setOnClickListener {
            val category = spinner.selectedItem
            val cost = editTextNumberDecimal.text
            val month = spinner3.selectedItem
            val day = spinner4.selectedItem
            val year = spinner5.selectedItem
            val description = editTextTextMultiLine.text

//             clear inputs after hitting add
            editTextTextPersonName2.setText(" ")
            editTextNumberDecimal.setText(" ")
//            editTextDate2.setText(" ")
            editTextTextMultiLine.setText(" ")

            Toast.makeText(getApplicationContext(), "Expense added!", Toast.LENGTH_SHORT).show();
        }

//        TODO: go back to main screen
        val cancel = findViewById<View>(R.id.cancelButton) as Button
        cancel.setOnClickListener {
            val main = Intent(this@AddExpense, MainActivity::class.java)
            startActivityForResult(main,0 )
        }
//        TODO: clear the input boxes
        val clear = findViewById<View>(R.id.clearButton) as Button
        clear.setOnClickListener {
            editTextTextPersonName2.setText(" ")
            editTextNumberDecimal.setText(" ")
//            editTextDate2.setText(" ")
            editTextTextMultiLine.setText(" ")
            Toast.makeText(getApplicationContext(), "Info cleared", Toast.LENGTH_SHORT).show();
        }
    }
}
