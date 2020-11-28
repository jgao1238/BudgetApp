package com.example.studentcashapptracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_expense.*
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.OutputStreamWriter

class AddExpense : AppCompatActivity() {

    companion object {
        private const val TAG = "AddExpense"
        private const val FILE_NAME = "TestFile.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense)

        val trackPeriod = intent.getIntExtra("trackPeriod",0) //Period number

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

        //User is able to enter in as many entries as they want without going back to the screen
        val addExpense = findViewById<View>(R.id.addButton) as Button
        addExpense.setOnClickListener {
            val category = spinner.selectedItem
            val cost = editTextNumberDecimal.text
            var month = spinner3.selectedItem
            val day = spinner4.selectedItem
            val year = spinner5.selectedItem
            val description = editTextTextMultiLine.text

            when(month){ //Changing the month to a date for displaying later
                "January" -> month = "1"
                "February" -> month = "2"
                "March" -> month = "3"
                "April" -> month = "4"
                "May" -> month = "5"
                "June" -> month = "6"
                "July" -> month = "7"
                "August" -> month = "8"
                "September" -> month = "9"
                "October" -> month = "10"
                "November" -> month = "11"
                "December" -> month = "12"
            }
            val date = (month as String) + "/" + (day as String) + "/" + (year as String)

            val jsonObject = JSONObject()
            jsonObject.put("category",category)
            jsonObject.put("cost",cost)
            jsonObject.put("date",date)
            jsonObject.put("description",description)
            jsonObject.put("period",trackPeriod)
            var entry = jsonObject.toString() //Convert JSON to string

            if(!getFileStreamPath(FILE_NAME).exists()){
                try {
                    val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
                    val writer = BufferedWriter(OutputStreamWriter(fos))
                    writer.write(entry)
                    writer.write("\n")
                    writer.close()
                } catch (e: FileNotFoundException) {
                    Log.i(TAG, "FileNotFoundException")
                }
            } else {
                //File already exists
                val fos = openFileOutput(FILE_NAME,Context.MODE_APPEND)
                val writer = BufferedWriter(OutputStreamWriter(fos))
                writer.write(entry)
                writer.write("\n")
                writer.close()
            }

            //Resets fields and creates Toast notifying success
            spinner.setSelection(0, true)
            editTextTextPersonName2.setText(" ")
            editTextNumberDecimal.setText(" ")
            spinner3.setSelection(0, true)
            spinner4.setSelection(0, true)
            spinner5.setSelection(0, true)
            editTextTextMultiLine.setText(" ")
            Toast.makeText(getApplicationContext(), "Expense added!", Toast.LENGTH_SHORT).show()
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