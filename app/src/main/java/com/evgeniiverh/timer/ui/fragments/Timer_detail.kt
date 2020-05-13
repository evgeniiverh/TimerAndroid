package com.evgeniiverh.timer.ui.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.system.Os.close
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.databinding.FragmentTimerDetailBinding
import com.evgeniiverh.timer.ui.objects.Strong
import kotlinx.android.synthetic.main.fragment_timer_detail.*
import java.text.SimpleDateFormat
import java.util.*


class Timer_detail :BaseFragment(R.layout.fragment_timer_detail) {

    lateinit var binding: FragmentTimerDetailBinding
    val mainHandler = Handler(Looper.getMainLooper())
    private var i=100

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)+1
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hours = c.get(Calendar.HOUR_OF_DAY)
    val minutes = c.get(Calendar.MINUTE)

    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
    val dateS = sdf.parse("${Strong.Date} ${Strong.Time}")
    val dateT = sdf.parse("$day.$month.$year $hours:$minutes")

    override fun onResume() {
        super.onResume()


        nameDetailItem.text = Strong.Name
        dataDetailItem.text = Strong.Date
        timeDetailItem.text = Strong.Time


        mainHandler.post(updateTextTask)

    }

    fun minusOneSecond() {





        val itemSob = if(dateT.compareTo(dateS)<0)
            "До события"
        else
            "Прошло"

        val dtime = if(dateT.compareTo(dateS)<0)
            dateS.time-dateT.time
        else
            dateT.time-dateS.time


        val dyear = dtime/(31536000000)
        val dmount = dtime/(2592000000)
        val dnedel = dtime/(86400000*7)
        val dday = dtime/86400000
        val dhour = dtime/3600000
        val dminute = dtime/60000
        val dsecond = dtime/1000

        deskDetailItem.text=itemSob

        yarsDetailItem.text=dyear.toString()+" лет"
        monthDetailItem.text=dmount.toString()+" месяцев"
        weekDetailItem.text=dnedel.toString()+" недель"
        dayDetailItem.text=dday.toString()+" дней"
        hourDetailItem.text=dhour.toString()+" часов"
        minutDetailItem.text=dminute.toString()+" минут"
        secondDetailItem.text=dsecond.toString()+" секунд"
        dateT.time=dateT.time+1000


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private val updateTextTask = object : Runnable {
        override fun run() {
            minusOneSecond()
            mainHandler.postDelayed(this, 1000)
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

}
