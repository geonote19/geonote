package com.geonote.utils

import java.util.*

fun Date.addDays(days:Int) : Date {
    var calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, days)
    return calendar.time
}



