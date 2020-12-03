package com.example.studentcashapptracker

import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import java.util.*

class Period {
        var startDate: String? = null
        var endDate: String? = null
        var totalSpending: Int? = null

        constructor(startDate: String?, endDate: String?, totalSpending: Int?) {
            this.startDate = startDate
            this.endDate = endDate
            this.totalSpending = totalSpending
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
            return "Start Date: $startDate End Date: $endDate Spending: $totalSpending"
        }
}