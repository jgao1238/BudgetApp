package com.example.studentcashapptracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class Food : AppCompatActivity(){
    protected lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_layout)

        var entries = JSONArray()
        var trackPeriod: Int = intent.getIntExtra("TRACKING_PERIOD", 0)
        val list: MutableList<HashMap<String,String>> = ArrayList()
        try {
            val reader = BufferedReader(InputStreamReader(openFileInput("TestFile.txt")))
            var line = reader.readLine()
            while(line != null){
                val sb = StringBuilder()
                sb.append(line)
                entries.put(JSONObject(sb.toString()))
                line = reader.readLine()
            }
            reader.close()
            for(i in 0 until entries.length()){
                //sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE)
                //trackPeriod = sharedpreferences.getInt("trackPeriod",0)
                val entry = entries.getJSONObject(i)
                if(entry.get("period").toString().toInt() == trackPeriod && entry.get("category").toString() == "Food"){
                    var item = HashMap<String, String>()
                    item.put("line1", "Date: " + entry.get("date").toString())
                    item.put("line2", "Cost: $" + entry.get("cost").toString())
                    item.put("line3", "Description: " + entry.get("description").toString())
                    list.add(item)
                }
            }
            val adapt = SimpleAdapter(this,list, R.layout.display_entries, arrayOf("line1","line2","line3"),
                intArrayOf(R.id.line1,R.id.line2,R.id.line3)
            )
            val testing = findViewById<ListView>(R.id.foodList)
            testing.adapter = adapt
        } catch(e: IOException){
            Log.i("Car.kt","IOException")
        }
    }
}