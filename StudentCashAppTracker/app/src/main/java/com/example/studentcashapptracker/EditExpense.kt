package com.example.studentcashapptracker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_expense.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class EditExpense: AppCompatActivity() {
    companion object {
        private const val TAG = "EditExpense"
        private const val FILE_NAME = "TestFile.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_expense)

        //Get intent extras
        var oldCategory = intent.getStringExtra("category")
        var oldCost = intent.getDoubleExtra("cost",0.0)
        var oldDate = intent.getStringExtra("date")
        var oldDescription = intent.getStringExtra("description")
        var trackPeriod = intent.getIntExtra("period",0)
        var lineNum = intent.getIntExtra("lineNum",0)

        //Source referenced: Spinner
        //set up the category spinner
        val category: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            category.adapter = adapter
        }
        //Set up month spinner
        val monthSpinner: Spinner = findViewById(R.id.spinner3)
        ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            monthSpinner.adapter = adapter
        }
        //Set up day spinner
        val daySpinner: Spinner = findViewById(R.id.spinner4)
        ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            daySpinner.adapter = adapter
        }
        //Set up year spinner
        val yearSpinner: Spinner = findViewById(R.id.spinner5)
        ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.adapter = adapter
        }
        //Set up references to remaining form's fields
        var mCost = findViewById<EditText>(R.id.costEditText)
        var mDesc = findViewById<EditText>(R.id.descriptionEditText)

        //Populate every field of the form with the passed in information
        when(oldCategory){ //Load category
            "Food" -> category.setSelection(0,true)
            "Rent" -> category.setSelection(1,true)
            "Car" -> category.setSelection(2,true)
            "School" -> category.setSelection(3,true)
            "Other" -> category.setSelection(4,true)
        }
        mCost.setText(oldCost.toString()) //Load cost
        val date = oldDate?.split("/")?.toTypedArray() //Break the string into parts
        when(date?.get(0)){ //Load month
            "1" -> monthSpinner.setSelection(0,true)
            "2" -> monthSpinner.setSelection(1,true)
            "3" -> monthSpinner.setSelection(2,true)
            "4" -> monthSpinner.setSelection(3,true)
            "5" -> monthSpinner.setSelection(4,true)
            "6" -> monthSpinner.setSelection(5,true)
            "7" -> monthSpinner.setSelection(6,true)
            "8" -> monthSpinner.setSelection(7,true)
            "9" -> monthSpinner.setSelection(8,true)
            "10" -> monthSpinner.setSelection(9,true)
            "11" -> monthSpinner.setSelection(10,true)
            "12" -> monthSpinner.setSelection(11,true)
        }
        when(date?.get(1)){ //Load date
            "1" -> daySpinner.setSelection(0,true)
            "2" -> daySpinner.setSelection(1,true)
            "3" -> daySpinner.setSelection(2,true)
            "4" -> daySpinner.setSelection(3,true)
            "5" -> daySpinner.setSelection(4,true)
            "6" -> daySpinner.setSelection(5,true)
            "7" -> daySpinner.setSelection(6,true)
            "8" -> daySpinner.setSelection(7,true)
            "9" -> daySpinner.setSelection(8,true)
            "10" -> daySpinner.setSelection(9,true)
            "11" -> daySpinner.setSelection(10,true)
            "12" -> daySpinner.setSelection(11,true)
            "13" -> daySpinner.setSelection(12,true)
            "14" -> daySpinner.setSelection(13,true)
            "15" -> daySpinner.setSelection(14,true)
            "16" -> daySpinner.setSelection(15,true)
            "17" -> daySpinner.setSelection(16,true)
            "18" -> daySpinner.setSelection(17,true)
            "19" -> daySpinner.setSelection(18,true)
            "20" -> daySpinner.setSelection(19,true)
            "21" -> daySpinner.setSelection(20,true)
            "22" -> daySpinner.setSelection(21,true)
            "23" -> daySpinner.setSelection(22,true)
            "24" -> daySpinner.setSelection(23,true)
            "25" -> daySpinner.setSelection(24,true)
            "26" -> daySpinner.setSelection(25,true)
            "27" -> daySpinner.setSelection(26,true)
            "28" -> daySpinner.setSelection(27,true)
            "29" -> daySpinner.setSelection(28,true)
            "30" -> daySpinner.setSelection(29,true)
            "31" -> daySpinner.setSelection(30,true)
        }
        when(date?.get(2)){ //Load year
            "2019" -> yearSpinner.setSelection(0,true)
            "2020" -> yearSpinner.setSelection(1,true)
            "2021" -> yearSpinner.setSelection(2,true)
            "2022" -> yearSpinner.setSelection(3,true)
            "2023" -> yearSpinner.setSelection(4,true)
        }
        mDesc.setText(oldDescription) //Load description

        //Create a listener for the update button
        val acceptExpense = findViewById<View>(R.id.acceptButton) as Button
        acceptExpense.setOnClickListener{
            val category = spinner.selectedItem
            val cost = costEditText.text
            var month = spinner3.selectedItem
            val day = spinner4.selectedItem
            val year = spinner5.selectedItem
            val description = descriptionEditText.text
            when(month){
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

            //Source referenced: Read and parse data with JSON
            //Create a new json object
            val jsonObject = JSONObject()
            jsonObject.put("category",category)
            jsonObject.put("cost",cost)
            jsonObject.put("date",date)
            jsonObject.put("description",description)
            jsonObject.put("period",trackPeriod)
            var entry = jsonObject.toString()

            //The expense field can be zero but it cannot be empty
            if(cost.toString() != "" && cost.toString() != "0"){
                //First thing we need to do is create a JSONArray of the current file
                //Then we reset the file to be empty
                //line number = index + 1
                //If the line number is equal to lineNum, then we put the new object there
                //Else, we add in the normal object in JSONArray

                //We now have the JSONArray
                var reader = BufferedReader(InputStreamReader(openFileInput("TestFile.txt")))
                var line = reader.readLine()
                var entries = JSONArray()
                while(line != null){
                    val sb = StringBuilder()
                    sb.append(line)  //Might need to use sb.append(line).append("\n") if error
                    val jsonObject = JSONObject(sb.toString())
                    entries.put(jsonObject)
                    line = reader.readLine()
                }
                reader.close()

                //Reset file to be empty
                var writer = BufferedWriter(OutputStreamWriter(openFileOutput("TestFile.txt", Context.MODE_PRIVATE)))
                writer.write("")

                //If lifeNum is right, place new. Else add in normal JSONArray object
                writer = BufferedWriter(OutputStreamWriter(openFileOutput("TestFile.txt", Context.MODE_APPEND)))
                for(i in 0 until entries.length()){
                    //If the line doesn't equal the deleteObj, then let's add it
                    val currentLine = i + 1
                    if(currentLine == lineNum){
                        writer.write(entry)
                        writer.write("\n")
                    } else {
                        writer.write(entries.getJSONObject(i).toString())
                        writer.write("\n")
                    }
                }
                writer.close()

                //Finish and go back to previous page
                finish()
            } else {
                Toast.makeText(applicationContext,"New expense cannot be empty!",Toast.LENGTH_SHORT).show()
            }
        }

        //Cancel leads back to the main screen
        val cancel = findViewById<View>(R.id.cancelButton) as Button
        cancel.setOnClickListener {
            finish()
        }

        //Clear resets the form to the default values
        val clear = findViewById<View>(R.id.clearButton) as Button
        clear.setOnClickListener {
            category.setSelection(0, true)
            categoryTextView.setText("")
            costEditText.setText("")
            spinner3.setSelection(0, true)
            spinner4.setSelection(0, true)
            spinner5.setSelection(0, true)
            descriptionEditText.setText("")
            Toast.makeText(getApplicationContext(), "Info cleared", Toast.LENGTH_SHORT).show();
        }
    }
}