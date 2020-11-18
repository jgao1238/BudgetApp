package com.example.studentcashapptracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_expense.*

class AddExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense)

//        TODO: commit to a database
        val addExpense = findViewById<View>(R.id.addButton) as Button
        addExpense.setOnClickListener {
            val category = editTextTextPersonName2.text
            val cost = editTextNumberDecimal.text
            val date = editTextDate2.text
            val description = editTextTextMultiLine.text

            editTextTextPersonName2.setText(" ")
            editTextNumberDecimal.setText(" ")
            editTextDate2.setText(" ")
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
            editTextDate2.setText(" ")
            editTextTextMultiLine.setText(" ")
            Toast.makeText(getApplicationContext(), "Info cleared", Toast.LENGTH_SHORT).show();
        }
    }
}
