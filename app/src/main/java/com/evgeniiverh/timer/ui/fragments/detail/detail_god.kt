package com.evgeniiverh.timer.ui.fragments.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.ui.fragments.BaseFragment
import com.evgeniiverh.timer.ui.fragments.Timer_detail
import com.evgeniiverh.timer.ui.objects.Strong
import kotlinx.android.synthetic.main.fragment_detail_god.*
import kotlinx.android.synthetic.main.fragment_timer_detail.*
import kotlinx.android.synthetic.main.fragment_timer_detail.dataDetailItem
import kotlinx.android.synthetic.main.fragment_timer_detail.deskDetailItem
import kotlinx.android.synthetic.main.fragment_timer_detail.nameDetailItem
import kotlinx.android.synthetic.main.fragment_timer_detail.timeDetailItem
import kotlinx.android.synthetic.main.fragment_timer_detail.yarsDetailItem
import java.text.SimpleDateFormat
import java.util.*


class detail_god : BaseFragment(R.layout.fragment_detail_god) {

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
        var namber = 0

        nameDetailItem.text = Strong.Name
        dataDetailItem.text = Strong.Date
        timeDetailItem.text = Strong.Time

        yarsDetailItem1.setVisibility(View.VISIBLE)
        monthDetailItem1.setVisibility(View.GONE)
        weekDetailItem1.setVisibility(View.GONE)
        dayDetailItem1.setVisibility(View.GONE)
        hourDetailItem1.setVisibility(View.GONE)
        minutDetailItem1.setVisibility(View.GONE)
        secondDetailItem1.setVisibility(View.GONE)

        super.onResume()

        val layout1 = detailLoyatgod
        layout1.setOnClickListener{
            namber=namber+1
            if(namber==1){
                yarsDetailItem1.setVisibility(View.GONE)
                monthDetailItem1.setVisibility(View.VISIBLE)
            }
            if(namber==2){
                monthDetailItem1.setVisibility(View.GONE)
                weekDetailItem1.setVisibility(View.VISIBLE)
            }
            if(namber==3){
                weekDetailItem1.setVisibility(View.GONE)
                dayDetailItem1.setVisibility(View.VISIBLE)
            }
            if(namber==4){
                dayDetailItem1.setVisibility(View.GONE)
                hourDetailItem1.setVisibility(View.VISIBLE)
            }
            if(namber==5){
                hourDetailItem1.setVisibility(View.GONE)
                minutDetailItem1.setVisibility(View.VISIBLE)
            }
            if(namber==6){
                minutDetailItem1.setVisibility(View.GONE)
                secondDetailItem1.setVisibility(View.VISIBLE)
            }

            if(namber==7){
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.dataContainer,
                    Timer_detail()
                )?.commit()
        }}


    }


    fun minusOneSecond() {

        val itemSob = if(dateT!! < dateS)
            getString(R.string.do_sob)
        else
            getString(R.string.proshlo)

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

        yarsDetailItem1.text=itemYear

        val  itemMounts=dmount.toString()+if(dmount.toInt()%10==1 && dmount.toInt()!=11)getString(R.string.mount_pre_1)
        else if(dmount.toInt() in 2..4) getString(R.string.mount_pre_2)
        else getString(R.string.mount_pre_3)

        monthDetailItem1.text=itemMounts

        val  itemNedely=dnedel.toString()+if(dnedel.toInt()%10==1 && dmount.toInt()!=11)getString(R.string.nedely_pre_1)
        else if(dnedel.toInt() in 2..4 || dnedel.toInt()%10 in 2..4 && dnedel.toInt()>20) getString(
            R.string.nedely_pre_2)
        else getString(R.string.nedely_pre_3)

        weekDetailItem1.text=itemNedely

        val  itemDayD=dday.toString()+if(dday.toInt()%10==1 && dday.toInt()!=11)getString(R.string.day_pre_1)
        else if(dday.toInt() in 2..4 || dday.toInt()%10 in 2..4 && dday.toInt()>20 ) getString(R.string.day_pre_2)
        else getString(R.string.day_pre_3)


        dayDetailItem1.text=itemDayD

        val  itemHourD = dhour.toString()+if(dhour.toInt()%10==1 && dhour.toInt()!=11)getString(R.string.hour_pre_1)
        else if(dhour.toInt() in 2..4 || dhour.toInt()%10 in 2..4 && dhour.toInt()>20 ) getString(R.string.hour_pre_2)
        else getString(R.string.hour_pre_3)

        hourDetailItem1.text= itemHourD

        val  itemMinutD = dminute.toString()+if(dminute.toInt()%10==1 && dminute.toInt()!=11)getString(
            R.string.minut_pre_1)
        else if(dminute.toInt() in 2..4 || dminute.toInt()%10 in 2..4 && dminute.toInt()>20 ) getString(
            R.string.minut_pre_2)
        else getString(R.string.minut_pre_3)

        minutDetailItem1.text=itemMinutD

        val  itemSecondD = dsecond.toString()+if(dsecond.toInt()%10==1 && dsecond.toInt()!=11)getString(
            R.string.second_pre_1)
        else if(dsecond.toInt() in 2..4 || dsecond.toInt()%10 in 2..4 && dsecond.toInt()>20 ) getString(
            R.string.second_pre_2)
        else getString(R.string.second_pre_3)


        secondDetailItem1.text=itemSecondD

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
