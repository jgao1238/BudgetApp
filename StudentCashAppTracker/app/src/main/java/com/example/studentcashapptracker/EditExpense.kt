package com.example.studentcashapptracker

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_expense.*

class EditExpense: AppCompatActivity() {
    companion object {
        private const val TAG = "AddExpense"
        private const val FILE_NAME = "TestFile.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_expense)

        //set up the category spinner
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        //set up the 3 spinners for the date
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

        //TODO: all fields are now populated by that entry
            //spinner.setSelection(index,true)

        //TODO: EDITING FIELD
        val acceptExpense = findViewById<View>(R.id.acceptButton) as Button
        acceptExpense.setOnClickListener{

        }

        //Cancel leads back to the main screen
        val cancel = findViewById<View>(R.id.cancelButton) as Button
        cancel.setOnClickListener {
            finish()
        }

        //Clear resets the form to the default values
        val clear = findViewById<View>(R.id.clearButton) as Button
        clear.setOnClickListener {
            spinner.setSelection(0, true)
            editTextTextPersonName2.setText(" ")
            editTextNumberDecimal.setText(" ")
            spinner3.setSelection(0, true)
            spinner4.setSelection(0, true)
            spinner5.setSelection(0, true)
            editTextTextMultiLine.setText(" ")
            Toast.makeText(getApplicationContext(), "Info cleared", Toast.LENGTH_SHORT).show();
        }
    }
}