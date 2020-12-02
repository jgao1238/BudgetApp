package com.example.studentcashapptracker

import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import java.util.*

class Expense {
        var cost: Int? = null
        var date: String? = null
        var description: String? = null

        constructor(cost: Int?, date: String?, description: String?) {
            this.cost = cost
            this.date = date
            this.description = description
        }

        /*fun packageToIntent(): Intent {
            val intent = Intent()
            intent.putExtra("mFlagUrl", flagUrl)
            intent.putExtra("mCountryName", countryName)
            intent.putExtra("mPlaceName", place)
            intent.putExtra("mLocation", location)
            intent.putExtra("mDateVisited", dateVisited)
            intent.putExtra("mFlagBitmap", flagBitmap)
            return intent
        }*/

        override fun toString(): String {
            return "Cost: $cost Date: $date Description: $description"
        }
}