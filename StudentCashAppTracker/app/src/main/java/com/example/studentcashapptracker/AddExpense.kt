package com.example.studentcashapptracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_expense.*

class AddExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense)
//        TODO: commit to a database
        val addExpense = findViewById<View>(R.id.addButton) as Button
        addExpense.setOnClickListener {

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
            editTextDate2.setText(" ")
            editTextNumberDecimal.setText(" ")
            editTextTextMultiLine.setText(" ")
            editTextTextPersonName2.setText(" ")
        }
    }
}