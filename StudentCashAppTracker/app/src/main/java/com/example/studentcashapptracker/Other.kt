package com.example.studentcashapptracker

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
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

class Other: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_layout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)//Enable back button
    }

    override fun onStart() {
        var entries = JSONArray() //Overall file's JSON entries
        var pageEntries = JSONArray() //JSON entries relevant to this page
        var trackPeriod = intent.getIntExtra("TRACKING_PERIOD",0)
        val list: MutableList<HashMap<String,String>> = ArrayList() //Will be used to display relevant entries in adapter

        //Source referenced as a basis: Read and parse data with JSON
        try {
            //Entries will store the JSON objects in the main file
            val reader = BufferedReader(InputStreamReader(openFileInput("TestFile.txt")))
            var line = reader.readLine()
            while(line != null){
                val sb = StringBuilder()
                sb.append(line)
                entries.put(JSONObject(sb.toString()))
                line = reader.readLine()
            }
            reader.close()

            //We are converting the relevant objects to a hashMap in a list to display using a SimpleAdapter
            for(i in 0 until entries.length()){
                val entry = entries.getJSONObject(i)
                if(entry.get("period").toString().toInt() == trackPeriod && entry.get("category").toString() == "Other"){
                    pageEntries.put(entry)
                    var item = HashMap<String, String>()
                    item.put("line1", "Date: " + entry.get("date").toString())
                    item.put("line2", "Cost: $" + entry.get("cost").toString())
                    item.put("line3", "Description: " + entry.get("description").toString())
                    list.add(item)
                }
            }

            //Source referenced: Multi-line ListView
            //Create the list view with the SimpleAdapter
            val adapt = object: SimpleAdapter(this,list, R.layout.display_entries, arrayOf("line1","line2","line3"),
                intArrayOf(R.id.line1,R.id.line2,R.id.line3)) {
                //Source referenced: ListAdapter Button
                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val v: View = super.getView(position, convertView, parent)

                    //Create Edit Button functionality for each item
                    val editButton: Button = v.findViewById(R.id.editButton)
                    editButton.setOnClickListener{
                        val editObj = pageEntries.getJSONObject(position)

                        val edit = Intent(this@Other, EditExpense::class.java)

                        //Need to pass in this object's data to the edit menu to populate the fields
                        edit.putExtra("category",editObj.get("category").toString())
                        edit.putExtra("cost",editObj.get("cost").toString().toDouble())
                        edit.putExtra("date",editObj.get("date").toString())
                        edit.putExtra("description",editObj.get("description").toString())
                        edit.putExtra("period",editObj.get("period").toString().toInt()) //not letting them edit but when overriding we need this for the new JSON
                        var lineNum = 1 //We need to compute the line number because we are replacing this line in the file
                        for(i in 0 until entries.length()){
                            //If the JSON matches the file, then stop incrementing line numbers
                            if(entries.getJSONObject(i).toString() == editObj.toString()){
                                break
                            } else {
                                lineNum += 1
                            }
                        }
                        edit.putExtra("lineNum",lineNum)

                        startActivity(edit)
                    }

                    //Create Delete button functionality for each item
                    val deleteButton: Button = v.findViewById<Button>(R.id.deleteButton)
                    deleteButton.setOnClickListener{
                        //Create an Alert to make sure sure the user actually wants to delete an expense
                        val builder = AlertDialog.Builder(this@Other)
                        builder.setTitle("Delete Expense")
                        builder.setMessage("Are you sure you want to delete this expense?")
                        builder.setPositiveButton("Accept", DialogInterface.OnClickListener{ dialog, id ->
                            val deleteObj = pageEntries.getJSONObject(position).toString()

                            //Clear the file because we are rewriting every line except for deleteObj
                            var fos = openFileOutput("TestFile.txt",Context.MODE_PRIVATE)
                            var writer = BufferedWriter(OutputStreamWriter(fos))
                            writer.write("")
                            //Now that the file is clear, we need to go through the file
                            //and write in every JSON except for the current view
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

                            //Source referenced: Remove from ListView
                            //Remove view and update the screen
                            list.removeAt(position)
                            notifyDataSetChanged()
                            //Source referenced: Reload Actvitiy
                            //Refresh this activity to reload all correct values
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        })
                        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, id ->
                        })
                        builder.create().show()
                    }
                    return v
                }
            }

            //Set the listView to our adapter
            val testing = findViewById<ListView>(R.id.otherList)
            testing.adapter = adapt
        } catch(e: IOException){
            Log.i("Other.kt","IOException")
        }
        super.onStart()
    }
}