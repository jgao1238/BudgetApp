package com.example.studentcashapptracker

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class Health : AppCompatActivity(){
    //protected lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_layout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)//Enable back button
    }

    override fun onStart() {
        var entries = JSONArray()
        var pageEntries = JSONArray()
        var trackPeriod = intent.getIntExtra("TRACKING_PERIOD",0)
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
            //Tutorial: MultiLine ListView Adapter
            for(i in 0 until entries.length()){
                //sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE)
                //trackPeriod = sharedpreferences.getInt("trackPeriod",0)
                val entry = entries.getJSONObject(i)
                if(entry.get("period").toString().toInt() == trackPeriod && entry.get("category").toString() == "School"){
                    pageEntries.put(entry)
                    var item = HashMap<String, String>()
                    item.put("line1", "Date: " + entry.get("date").toString())
                    item.put("line2", "Cost: $" + entry.get("cost").toString())
                    item.put("line3", "Description: " + entry.get("description").toString())
                    list.add(item)
                }
            }
            val adapt = object: SimpleAdapter(this,list, R.layout.display_entries, arrayOf("line1","line2","line3"),
                intArrayOf(R.id.line1,R.id.line2,R.id.line3)) { //Tutorial: StackOverFlow on SimpleAdapter Button
                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val v: View = super.getView(position, convertView, parent)
                    val editButton: Button = v.findViewById<Button>(R.id.editButton)
                    editButton.setOnClickListener{
                        Toast.makeText(applicationContext, "EDIT", Toast.LENGTH_LONG).show()

                    }
                    val deleteButton: Button = v.findViewById<Button>(R.id.deleteButton)
                    deleteButton.setOnClickListener{
                        val builder = AlertDialog.Builder(this@Health)
                        builder.setTitle("Delete Expense")
                        builder.setMessage("Are you sure you want to delete this expense?")
                        builder.setPositiveButton("Accept", DialogInterface.OnClickListener{ dialog, id ->
                            val deleteObj = pageEntries.getJSONObject(position).toString()
                            //Toast.makeText(applicationContext, position.toString(), Toast.LENGTH_SHORT).show()

                            //Clear the file because we are rewriting every line except for deleteObj
                            var fos = openFileOutput("TestFile.txt",Context.MODE_PRIVATE)
                            var writer = BufferedWriter(OutputStreamWriter(fos))
                            writer.write("")
                            fos = openFileOutput("TestFile.txt", Context.MODE_APPEND)
                            writer = BufferedWriter(OutputStreamWriter(fos))
                            for(i in 0 until entries.length()){
                                //If the line doesn't equal the deleteObj, then let's add it
                                if(entries.getJSONObject(i).toString() != deleteObj){
                                    writer.write(entries.getJSONObject(i).toString())
                                    writer.write("\n")
                                }
                            }
                            writer.close()

                            //Remove view and update the screen
                            list.removeAt(position)
                            notifyDataSetChanged()
                            //Refresh this activity to reload all correct values
                            finish();
                            startActivity(getIntent());
                        })
                        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, id ->
                        })
                        builder.create().show()
                    }
                    return v
                }
            }

            val testing = findViewById<ListView>(R.id.schoolList)
            testing.adapter = adapt
        } catch(e: IOException){
            Log.i("School.kt","IOException")
        }
        super.onStart()
    }
}