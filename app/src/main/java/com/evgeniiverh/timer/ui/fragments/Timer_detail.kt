package com.evgeniiverh.timer.ui.fragments


import android.os.Handler
import android.os.Looper
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.ui.objects.Strong
import kotlinx.android.synthetic.main.fragment_timer_detail.*
import java.text.SimpleDateFormat
import java.util.*


class Timer_detail :BaseFragment(R.layout.fragment_timer_detail) {
    
    val mainHandler = Handler(Looper.getMainLooper())

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)+1
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private val hours = c.get(Calendar.HOUR_OF_DAY)
    private val minutes = c.get(Calendar.MINUTE)

    private val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
    private val dateS = sdf.parse("${Strong.Date} ${Strong.Time}")
    private val dateT = sdf.parse("$day.$month.$year $hours:$minutes")

    override fun onResume() {
        mainHandler.post(updateTextTask)
        super.onResume()
        nameDetailItem.text = Strong.Name
        dataDetailItem.text = Strong.Date
        timeDetailItem.text = Strong.Time

    }

    fun minusOneSecond() {





        val itemSob = if(dateT!! < dateS)
            "До события"
        else
            "Прошло"

         val dtime = if(dateT < dateS)
            dateS!!.time-dateT.time
        else
            dateT.time-dateS!!.time


        val dyear = dtime/(31536000000)
        val dmount = dtime/(2592000000)
        val dnedel = dtime/(86400000*7)
        val dday = dtime/86400000
        val dhour = dtime/3600000
        val dminute = dtime/60000
        val dsecond = dtime/1000

        deskDetailItem.text=itemSob


        val  itemYear=dyear.toString()+if(dyear.toInt()%10==1 && dyear.toInt()!=11)getString(R.string.yarl_pre_1)
        else if(dyear.toInt() in 2..4) getString(R.string.yarl_pre_2)
        else if(dyear.toInt()>=5) getString(R.string.yarl_pre_3)
        else getString(R.string.yarl_pre_4)

        yarsDetailItem.text=itemYear

        val  itemMounts=dmount.toString()+if(dmount.toInt()%10==1 && dmount.toInt()!=11)getString(R.string.mount_pre_1)
        else if(dmount.toInt() in 2..4) getString(R.string.mount_pre_2)
        else getString(R.string.mount_pre_3)

        monthDetailItem.text=itemMounts

        val  itemNedely=dnedel.toString()+if(dnedel.toInt()%10==1 && dmount.toInt()!=11)getString(R.string.nedely_pre_1)
        else if(dnedel.toInt() in 2..4 || dnedel.toInt()%10 in 2..4 && dnedel.toInt()>20) getString(
                    R.string.nedely_pre_2)
        else getString(R.string.nedely_pre_3)

        weekDetailItem.text=itemNedely

        val  itemDayD=dday.toString()+if(dday.toInt()%10==1 && dday.toInt()!=11)getString(R.string.day_pre_1)
        else if(dday.toInt() in 2..4 || dday.toInt()%10 in 2..4 && dday.toInt()>20 ) getString(R.string.day_pre_2)
        else getString(R.string.day_pre_3)


        dayDetailItem.text=itemDayD

        val  itemHourD = dhour.toString()+if(dhour.toInt()%10==1 && dhour.toInt()!=11)getString(R.string.hour_pre_1)
        else if(dhour.toInt() in 2..4 || dhour.toInt()%10 in 2..4 && dhour.toInt()>20 ) getString(R.string.hour_pre_2)
        else getString(R.string.hour_pre_3)

        hourDetailItem.text= itemHourD

        minutDetailItem.text=dminute.toString()+if(dminute.toInt()%10==1 && dminute.toInt()!=11)" минута"
        else if(dminute.toInt() in 2..4 || dminute.toInt()%10 in 2..4 && dminute.toInt()>20 ) " минуты"
        else " минут"

        secondDetailItem.text=dsecond.toString()+if(dsecond.toInt()%10==1 && dsecond.toInt()!=11)" секунда"
        else if(dsecond.toInt() in 2..4 || dsecond.toInt()%10 in 2..4 && dsecond.toInt()>20 ) " секунды"
        else " секунд"

        dateT.time=dateT.time+1000


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
