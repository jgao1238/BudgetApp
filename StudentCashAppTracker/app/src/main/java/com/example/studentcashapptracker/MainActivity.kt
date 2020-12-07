package com.example.studentcashapptracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var budgetValue: Int = 0
    private var budgetPos: Int = 0
    var trackPeriod:Int = 0

    protected lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE)

        //Reload these saved values
        trackPeriod = sharedpreferences.getInt("trackPeriod",0)
        budgetValue = sharedpreferences.getInt("budgetValue",0)
        budgetPos = sharedpreferences.getInt("budgetPos",0)

        //Restore positions and text on screen
        val maxBudget = findViewById<View>(R.id.maxLimitAmountSlider) as SeekBar
        val maxField = findViewById<View>(R.id.maxLimitAmountText) as EditText
        maxField.setText(budgetValue.toString())
        maxBudget.progress = budgetPos

        //Change the seekBar if EditText changes
        maxField.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try{
                    budgetValue = p0.toString().toInt()
                    maxBudget.setProgress(budgetValue)
                    budgetPos = maxBudget.progress
                    val editor = sharedpreferences.edit()
                    editor.putInt("budgetValue",budgetValue)
                    editor.putInt("budgetPos",budgetPos)
                    editor.apply()
                    //Change the color if over budget
                    if(findViewById<TextView>(R.id.amountSpentValue).text.toString().toDouble() > sharedpreferences.getInt("budgetValue",0)){
                        findViewById<TextView>(R.id.amountSpentValue).setTextColor(Color.RED)
                    } else {
                        findViewById<TextView>(R.id.amountSpentValue).setTextColor(Color.BLACK)
                    }
                } catch(e: Exception) {}
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        //Source referenced: Seekbar
        //Change the EditText if the seekBar changes
        maxBudget.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                budgetPos = progress
                maxField.setText(progress.toString())
                budgetValue = maxField.text.toString().toInt()
                val editor = sharedpreferences.edit()
                editor.putInt("budgetValue",budgetValue)
                editor.putInt("budgetPos",budgetPos)
                editor.apply()
                //Change the color if over budget
                if(findViewById<TextView>(R.id.amountSpentValue).text.toString().toDouble() > sharedpreferences.getInt("budgetValue",0)){
                    findViewById<TextView>(R.id.amountSpentValue).setTextColor(Color.RED)
                } else {
                    findViewById<TextView>(R.id.amountSpentValue).setTextColor(Color.BLACK)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        //Launches screen to add expenses
        val addExpenseButton = findViewById<View>(R.id.addExpenseButton) as Button
        addExpenseButton.setOnClickListener {
            val addExpense = Intent(this@MainActivity, AddExpense::class.java)
            addExpense.putExtra("trackPeriod",trackPeriod)
            startActivityForResult(addExpense,0)
        }

        //food button
        val foodButton = findViewById<View>(R.id.foodButton) as Button
        foodButton.setOnClickListener {
            val food = Intent(this@MainActivity, Food::class.java)
            food.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(food)
        }
        //car button
        val carButton = findViewById<View>(R.id.carButton) as Button
        carButton.setOnClickListener {
            val car = Intent(this@MainActivity, Car::class.java)
            car.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(car)
        }

        //other button
        val otherButton = findViewById<View>(R.id.otherButton) as Button
        otherButton.setOnClickListener {
            val other = Intent(this@MainActivity, Other::class.java)
            other.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(other)
        }
        //rent button
        val rentButton = findViewById<View>(R.id.rentButton) as Button
        rentButton.setOnClickListener {
            val rent = Intent(this@MainActivity, Rent::class.java)
            rent.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(rent)
        }
        //school button
        val schoolButton = findViewById<View>(R.id.schoolButton) as Button
        schoolButton.setOnClickListener {
            val school = Intent(this@MainActivity, Health::class.java)
            school.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(school)
        }

        //Creates an alert dialog confirming that the user wants to end the tracking period
        val endPeriod = findViewById<View>(R.id.endTrackingPeriodButton) as Button
        endPeriod.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("End Tracking Period")
            builder.setMessage("Are you sure you want to end this tracking period?")
            builder.setCancelable(false)
            builder.setPositiveButton("Accept", DialogInterface.OnClickListener{dialog, id ->
                //Increase the tracking period for future records and reset the on screen values
                findViewById<TextView>(R.id.amountSpentValue).setText("0")
                findViewById<Button>(R.id.foodButton).setText("Food: $0")
                findViewById<Button>(R.id.rentButton).setText("Rent: $0")
                findViewById<Button>(R.id.carButton).setText("Car: $0")
                findViewById<Button>(R.id.schoolButton).setText("School: $0")
                findViewById<Button>(R.id.otherButton).setText("Other: $0")
                trackPeriod += 1
                val editor = sharedpreferences.edit()
                editor.putInt("trackPeriod",trackPeriod)
                editor.putInt("budgetValue",0) //reset max budget value
                editor.putInt("budgetPos",0) //reset seekBar position
                editor.apply()
                maxField.setText(0.toString()) //do the actual visual changes
                maxBudget.setProgress(0)
                findViewById<TextView>(R.id.amountSpentValue).setTextColor(Color.BLACK)
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, id ->
            })
            builder.create().show()
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        //Initializing the total summary at the top + update when we come back from add screen
        var foodButton = findViewById<Button>(R.id.foodButton)
        var rentButton = findViewById<Button>(R.id.rentButton)
        var carButton = findViewById<Button>(R.id.carButton)
        var schoolButton = findViewById<Button>(R.id.schoolButton)
        var otherButton = findViewById<Button>(R.id.otherButton)
        var amtSpent = findViewById<TextView>(R.id.amountSpentValue)

        //Source referenced: Read and parse data with JSON
        //Reading in JSON values
        try {
            val reader = BufferedReader(InputStreamReader(openFileInput(FILE_NAME)))
            var line = reader.readLine()
            var testing = JSONArray()
            while(line != null){
                val sb = StringBuilder()
                sb.append(line)  //Might need to use sb.append(line).append("\n") if error
                val jsonObject = JSONObject(sb.toString())
                testing.put(jsonObject)
                line = reader.readLine()
            }
            reader.close()

            //Calculating the values for each button display
            var mTotal = 0.0; var mFood = 0.0; var mRent = 0.0; var mCar = 0.0; var mSchool = 0.0; var mOther = 0.0;
            for(i in 0 until testing.length()){
                val entry = testing.getJSONObject(i)
                //There's going to be a lot of entries, so only add the current period's
                if(entry.get("period").toString().toInt() == trackPeriod){
                    var mCost = entry.get("cost").toString().toDouble()
                    mTotal += mCost
                    if(entry.get("category").toString() == "Car"){
                        mCar += mCost
                    } else if (entry.get("category").toString() == "Food") {
                        mFood += mCost
                    } else if (entry.get("category").toString() == "Rent") {
                        mRent += mCost
                    } else if (entry.get("category").toString() == "School") {
                        mSchool += mCost
                    } else if (entry.get("category").toString() == "Other") {
                        mOther += mCost
                    }
                }
            } //Load the value into each button now
            foodButton.setText("Food: $" + String.format("%.2f", mFood))
            rentButton.setText("Rent: $" + String.format("%.2f", mRent))
            carButton.setText("Car: $" + String.format("%.2f", mCar))
            schoolButton.setText("School: $" + String.format("%.2f", mSchool))
            otherButton.setText("Other: $" + String.format("%.2f", mOther))
            amtSpent.text = String.format("%.2f", mTotal)

            //if the amount spent is greater than budget, change color to red to draw attention
            if(mTotal > sharedpreferences.getInt("budgetValue",0)){
                amtSpent.setTextColor(Color.RED)
            } else {
                amtSpent.setTextColor(Color.BLACK)
            }
        } catch(e: IOException){
            Log.i(TAG,"IOException")
        }
        super.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    //create the menu in the top right of the screen
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_file, menu)
        return true
    }

    //menu dialog
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            val testReader = BufferedReader(InputStreamReader(openFileInput(FILE_NAME)))
            var line = testReader.readLine()
            if (line != null) {
                val previousActivity = Intent(this@MainActivity, PreviousPeriods::class.java)
                previousActivity.putExtra("CURR_PERIOD", trackPeriod)
                startActivity(previousActivity)
            }
        } catch (e : IOException){
            Log.i(TAG, "Problem selecting previous periods")
        }
        return true
    }

    companion object {
        private const val mypreference = "mypref"
        private const val TAG = "MainActivity"
        private const val FILE_NAME = "TestFile.txt"
    }
}