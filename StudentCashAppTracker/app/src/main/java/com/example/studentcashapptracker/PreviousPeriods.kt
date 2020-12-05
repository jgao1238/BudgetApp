package com.example.studentcashapptracker

import android.app.ListActivity
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

        try {
            val reader = BufferedReader(InputStreamReader(openFileInput(FILE_NAME)))
            var line = reader.readLine()
            var testing = JSONArray()
            while (line != null) {
                val sb = StringBuilder()
                sb.append(line)
                val jsonObject = JSONObject(sb.toString())
                testing.put(jsonObject)
                line = reader.readLine()
            }
            reader.close()
            var trackingPeriod = 0
            var entry = testing.getJSONObject(0)
            var startDate = entry.getString("date").toString()
            var endDate = entry.getString("date").toString()
            var totalCost =entry.get("cost").toString().toDouble()
            for (i in 1 until testing.length()) {
                entry = testing.getJSONObject(i)
                //get the total cost over the period, and if the period has changed log the sum of the entries along with the start and end date
                if (entry.get("period").toString().toInt() > trackingPeriod) {
                    var item = HashMap<String, String>()
                    item.put("startDateString", "Start Date: $startDate")
                    item.put("endDateString", "End Date: $endDate")
                    item.put("totalExpenseString", "Total Expenses: $totalCost")
                    item.put("trackingPeriod", "$trackingPeriod")
                    list.add(item)
                    startDate = entry.getString("date").toString()
                    totalCost = 0.0
                    trackingPeriod++
                }
                //TODO: Smart start date and end date
                var cost = entry.getString("cost").toString().toDouble()
                totalCost += cost

                //var date = entry.getString("date").toString()
                //var dateTransfer = date.split("/").toTypedArray()

                endDate = entry.getString("date").toString()
            }
            //covers edge case where the is no entries in the current period

            var sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE)

            if(sharedPreferences.getInt("trackPeriod",0) > trackingPeriod){
                var item = HashMap<String, String>()
                item.put("startDateString", "Start Date: $startDate")
                item.put("endDateString", "End Date: $endDate")
                item.put("totalExpenseString", "Total Expenses: $totalCost")
                item.put("trackingPeriod", "$trackingPeriod")
                list.add(item)
            }

            val adapt = SimpleAdapter(this, list, R.layout.previous_periods,
                arrayOf("startDateString","endDateString","totalExpenseString"),
                intArrayOf(R.id.startDateString,R.id.endDateString,R.id.totalExpenseString)
            )
            periodList.adapter = adapt
        } catch (e: IOException) {
            Log.i("Error in Pevious Period", "IOException")
        }
        periodList.onItemClickListener =  AdapterView.OnItemClickListener { _, _, i, _ ->
            var period  = list.get(i)
            var intent = Intent(this@PreviousPeriods, PreviousPeriodsDetail::class.java)
            intent.putExtra("TRACKING_PERIOD", period.get("trackingPeriod")?.toInt())
            startActivity(intent)
        }

    }
}