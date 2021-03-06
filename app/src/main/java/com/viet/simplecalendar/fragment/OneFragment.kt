package com.viet.simplecalendar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.viet.simplecalendar.CalendarAdapter
import com.viet.simplecalendar.MainActivity
import com.viet.simplecalendar.R
import kotlinx.android.synthetic.main.fragment_one.view.*
import java.text.SimpleDateFormat
import java.util.*

class OneFragment : Fragment() {
    lateinit var mainActivity : MainActivity
    val MAX_DAY = 43
    var calendar : Calendar = Calendar.getInstance()
    var dateFormat : SimpleDateFormat = SimpleDateFormat("MMMM yyyy")

    var dates : MutableList<Date> = mutableListOf()
    lateinit var calendarAdapter : CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_one, container, false)

        mainActivity = activity as MainActivity
        calendar = mainActivity.calendar
        while(calendar.get(Calendar.MONTH) != 0){
            if(calendar.get(Calendar.MONTH) > 0)
                calendar.add(Calendar.MONTH, -1)
            else
                calendar.add(Calendar.MONTH, 1)
        }

        var monthYear : String = dateFormat.format(calendar.time)
        view.tvMonthYear.text = monthYear
        dates.clear()
        var monthCalendar : Calendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstDayOfMonth : Int = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (dates.size < MAX_DAY - 1){
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        dates.add(calendar.time)

        view.rcvCalendar.layoutManager = GridLayoutManager(this.context,7)
        calendarAdapter = CalendarAdapter(dates)
        view.rcvCalendar.adapter = calendarAdapter

        calendarAdapter.notifyDataSetChanged()

        return view
    }

}