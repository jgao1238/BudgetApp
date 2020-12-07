package com.example.studentcashapptracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class PreviousPeriods: AppCompatActivity(){
    private val FILE_NAME = "TestFile.txt"
    private var list : MutableList<HashMap<String,String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.previous_periods_layout)

        val periodList = findViewById<ListView>(R.id.periodList)

        //Source referenced: Read and parse data with JSON
        //Read in the file to make a JSONArray of entries
        try {
            val reader = BufferedReader(InputStreamReader(openFileInput(FILE_NAME)))
            var line = reader.readLine()
            var entries = JSONArray()
            while (line != null) {
                val sb = StringBuilder()
                sb.append(line)
                val jsonObject = JSONObject(sb.toString())
                entries.put(jsonObject)
                line = reader.readLine()
            }
            reader.close()

            //Get JSON values and load in values
            var entry = entries.getJSONObject(0)
            var startDate = entry.getString("date").toString()
            var endDate = entry.getString("date").toString()
            var trackingPeriod = entry.getString("period").toString().toInt()
            var date = entry.getString("date").toString()
            var dateTransfer = date.split("/").toTypedArray()
            var startDateInts = ArrayList<Int>()
            var endDateInts: java.util.ArrayList<Int>
            startDateInts.add(0, dateTransfer.get(0).toInt())
            startDateInts.add(1, dateTransfer.get(1).toInt())
            startDateInts.add(2, dateTransfer.get(2).toInt())
            endDateInts = ArrayList(startDateInts)

            var totalCost = entry.get("cost").toString().toDouble()
            for (i in 1 until entries.length()) {
                entry = entries.getJSONObject(i)
                //get the total cost over the period, and if the period has changed log the sum of the entries along with the start and end date
                if (entry.get("period").toString().toInt() > trackingPeriod) {
                    var item = HashMap<String, String>()
                    item.put("startDateString", "Start Date: $startDate")
                    item.put("endDateString", "End Date: $endDate")
                    item.put(
                        "totalExpenseString",
                        "Total Expenses: " + String.format("%.2f", totalCost)
                    )
                    item.put("trackingPeriod", "$trackingPeriod")
                    list.add(item)

                    startDate = entry.getString("date").toString()
                    endDate = entry.getString("date").toString()
                    date = entry.getString("date").toString()
                    dateTransfer = date.split("/").toTypedArray()
                    startDateInts.add(0, dateTransfer.get(0).toInt())
                    startDateInts.add(1, dateTransfer.get(1).toInt())
                    startDateInts.add(2, dateTransfer.get(2).toInt())
                    endDateInts = ArrayList(startDateInts)

                    totalCost = 0.0
                    trackingPeriod = entry.get("period").toString().toInt()
                }
                //checks the end date and start date and if they need updating
                var cost = entry.getString("cost").toString().toDouble()
                totalCost += cost

                date = entry.getString("date").toString()
                dateTransfer = date.split("/").toTypedArray()
                var dateInts = ArrayList<Int>()
                dateInts.add(0, dateTransfer.get(0).toInt())
                dateInts.add(1, dateTransfer.get(1).toInt())
                dateInts.add(2, dateTransfer.get(2).toInt())

                //Loads each end and start date for periods
                if (dateInts.get(2) > endDateInts.get(2)) {
                    endDateInts = dateInts
                    endDate = date
                } else if (dateInts.get(0) > endDateInts.get(0) && dateInts.get(2) == endDateInts.get(2)) {
                    endDateInts = dateInts
                    endDate = date
                } else if (dateInts.get(1) > endDateInts.get(1) && dateInts.get(0) == endDateInts.get(0) && dateInts.get(2) == endDateInts.get(2)) {
                    endDateInts = dateInts
                    endDate = date
                }

                if (startDateInts.get(2) > dateInts.get(2)) {
                    startDateInts = dateInts
                    startDate = date
                } else if (startDateInts.get(0) > dateInts.get(0) && startDateInts.get(2) == dateInts.get(2)) {
                    startDateInts = dateInts
                    startDate = date
                } else if (startDateInts.get(1) > dateInts.get(1) && startDateInts.get(0) == dateInts.get(0) && startDateInts.get(2) == dateInts.get(2)) {
                    startDateInts = dateInts
                    startDate = date
                }


            }
            //covers edge case where the is no entries in the current period

            var sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE)

            if (sharedPreferences.getInt("trackPeriod", 0) > trackingPeriod) {
                var item = HashMap<String, String>()
                item.put("startDateString", "Start Date: $startDate")
                item.put("endDateString", "End Date: $endDate")
                item.put("totalExpenseString", "Total Expenses: $totalCost")
                item.put("trackingPeriod", "$trackingPeriod")
                list.add(item)
            }

            //Source referenced: Multi-line ListView
            val adapt = SimpleAdapter(
                this, list, R.layout.previous_periods,
                arrayOf("startDateString", "endDateString", "totalExpenseString"),
                intArrayOf(R.id.startDateString, R.id.endDateString, R.id.totalExpenseString)
            )
            periodList.adapter = adapt

        } catch (e: IOException) {
            Log.i("Error in Pevious Period", "IOException")
        }

        //This code is inspired by lab 11
        periodList.onItemClickListener =  AdapterView.OnItemClickListener { _, _, i, _ ->
            var period  = list.get(i)
            var intent = Intent(this@PreviousPeriods, PreviousPeriodsDetail::class.java)
            intent.putExtra("TRACKING_PERIOD", period.get("trackingPeriod")?.toInt())
            startActivity(intent)
        }

    }
}