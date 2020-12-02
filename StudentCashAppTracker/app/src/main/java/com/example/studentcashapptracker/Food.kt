package com.example.studentcashapptracker

import android.app.ListActivity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class Food : ListActivity(){
    private  val FILE_NAME = "TestFile.txt"
    private lateinit var mAdapter: ExpenseAdapter
    private lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //menu setup stuff
        val expensesListView = listView
        mAdapter = ExpenseAdapter(applicationContext)
        listAdapter = mAdapter
        category = intent.getStringExtra("CATEGORY")!!

        var header = layoutInflater.inflate(R.layout.food_layout, expensesListView, false)
        header.findViewById<TextView>(R.id.headerText).text = category
        expensesListView.addHeaderView(header)


        //goes through the database and finds all of the instances of the category happening in the period
        try {
            val reader = BufferedReader(InputStreamReader(openFileInput(FILE_NAME)))
            var line = reader.readLine()
            var testing = JSONArray()
            while (line != null) {
                val sb = StringBuilder()
                sb.append(line)  //Might need to use sb.append(line).append("\n") if error
                val jsonObject = JSONObject(sb.toString())
                testing.put(jsonObject)
                line = reader.readLine()
            }
            reader.close()
            var trackingPeriod = intent.getIntExtra("TRACKING_PERIOD", 0)
            for (i in 0 until testing.length()) {
                val entry = testing.getJSONObject(i)
                //There's going to be a lot of entries, so only add the current period's
                if (entry.get("period").toString().toInt() == trackingPeriod) {
                    if (entry.get("category").toString() == category) {
                        var cost = entry.get("cost").toString().toInt()
                        var date = entry.get("date").toString()
                        var description = entry.get("description").toString()
                        mAdapter.add(Expense(cost, date, description))
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }
        } catch (e: IOException) {
            Log.i("Error in Menus", "IOException")
        }

        //sets each menu entry so that when it is clicked it gives the user the option to edit
        expensesListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Edit Entry")
            builder.setMessage("Would you Like to Edit this entry?")
            builder.setCancelable(false)
            builder.setPositiveButton("Edit", DialogInterface.OnClickListener { dialog, id ->
                //Do something like take the user to an edit screen, we could just re-use the add expense screen for this
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
            })
            builder.create().show()
        }
    }
}