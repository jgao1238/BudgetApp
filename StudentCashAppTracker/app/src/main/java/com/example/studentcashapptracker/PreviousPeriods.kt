package com.example.studentcashapptracker

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class PreviousPeriods: ListActivity(){
    private  val FILE_NAME = "TestFile.txt"
    private lateinit var mAdapter: PeriodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //list setup stuff
        val periodListView = listView
        mAdapter = PeriodAdapter(applicationContext)
        listAdapter = mAdapter

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
            var endDate : String? = null
            var totalCost = 0.0
            for (i in 1 until testing.length()) {
                entry = testing.getJSONObject(i)
                //get the total cost over the period, and if the period has changed log the sum of the entries along with the start and end date
                if (entry.get("period").toString().toInt() > trackingPeriod) {
                        mAdapter.add(Period(startDate, endDate, totalCost, trackingPeriod))
                        mAdapter.notifyDataSetChanged()
                        startDate = entry.getString("date").toString()
                        totalCost = 0.0
                        trackingPeriod++
                    }
                var cost = entry.getString("cost").toString().toDouble()
                totalCost += cost
                endDate = entry.getString("date")
                }
            if(intent.getIntExtra("CURR_PERIOD", 0) > trackingPeriod){
                mAdapter.add(Period(startDate, endDate, totalCost, trackingPeriod))
                mAdapter.notifyDataSetChanged()
            }
        } catch (e: IOException) {
            Log.i("Error in Menus", "IOException")
        }

        periodListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            var record = mAdapter!!.getItem(i) as Period
            var intent = Intent(this@PreviousPeriods, PreviousPeriodsDetail::class.java)
            intent.putExtra("TRACKING_PERIOD", record.period)
            startActivity(intent)
        }

    }
}