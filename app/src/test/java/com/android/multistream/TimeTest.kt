package com.android.multistream

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class TimeTest {

    @Test
    fun addition_isCorrect() {
       val time = getTime(17, 5)
        assertEquals(time, time)
    }
}

 fun getTime(hour: Int, minutes: Int): Long {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinutes = calendar.get(Calendar.MINUTE)
    val isToday: Boolean = when  {
        currentHour == hour -> currentMinutes < minutes
        currentHour > hour -> false
        else -> true
    }
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minutes)
        if (!isToday) {
            val currentDay = get(Calendar.DAY_OF_YEAR)
            set(Calendar.DAY_OF_YEAR, currentDay + 1)
        }
    }
    return calendar.timeInMillis
}
