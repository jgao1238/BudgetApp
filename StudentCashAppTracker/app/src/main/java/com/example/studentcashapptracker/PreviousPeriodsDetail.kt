package com.example.studentcashapptracker

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

class PreviousPeriodsDetail : AppCompatActivity() {
    private lateinit var build: AlertDialog.Builder

    private var budgetValue: Int = 0
    private var budgetPos: Int = 0
    var trackPeriod:Int = 0

    protected lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.previoius_period_detailed)

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE)

        trackPeriod = intent.getIntExtra("TRACKING_PERIOD", 0)
        budgetValue = sharedpreferences.getInt("budgetValue",0)
        budgetPos = sharedpreferences.getInt("budgetPos",0)

        //food button
        val foodButton = findViewById<View>(R.id.pastFoodButton) as Button
        foodButton.setOnClickListener {
            val food = Intent(this@PreviousPeriodsDetail, Food::class.java)
            food.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(food)
        }
        //car button
        val carButton = findViewById<View>(R.id.pastCarButton) as Button
        carButton.setOnClickListener {
            val car = Intent(this@PreviousPeriodsDetail, Car::class.java)
            car.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(car)
        }

        //other button
        val otherButton = findViewById<View>(R.id.pastOtherButton) as Button
        otherButton.setOnClickListener {
            val other = Intent(this@PreviousPeriodsDetail, Other::class.java)
            other.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(other)
        }
        //rent button
        val rentButton = findViewById<View>(R.id.pastRentButton) as Button
        rentButton.setOnClickListener {
            val rent = Intent(this@PreviousPeriodsDetail, Rent::class.java)
            rent.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(rent)
        }
        //school button
        val schoolButton = findViewById<View>(R.id.pastSchoolButton) as Button
        schoolButton.setOnClickListener {
            val school = Intent(this@PreviousPeriodsDetail, Health::class.java)
            school.putExtra("TRACKING_PERIOD", trackPeriod)
            startActivity(school)
        }

    }

    override fun onStart() {
        //Initializing the total summary at the top + update when we come back from add screen
        var foodButton = findViewById<Button>(R.id.pastFoodButton)
        var rentButton = findViewById<Button>(R.id.pastRentButton)
        var carButton = findViewById<Button>(R.id.pastCarButton)
        var schoolButton = findViewById<Button>(R.id.pastSchoolButton)
        var otherButton = findViewById<Button>(R.id.pastOtherButton)

        var amtSpent = findViewById<TextView>(R.id.pastAmountSpentValue)
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
            foodButton.setText("Food: $" + mFood.toString())
            rentButton.setText("Rent: $" + mRent.toString())
            carButton.setText("Car: $" + mCar.toString())
            schoolButton.setText("School: $" + mSchool.toString())
            otherButton.setText("Other: $" + mOther.toString())
            amtSpent.text = mTotal.toString()
        } catch(e: IOException){
            Log.i(TAG,"IOException")
        }
        super.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val mypreference = "mypref"
        private const val TAG = "MainActivity"
        private const val FILE_NAME = "TestFile.txt"
    }
}
