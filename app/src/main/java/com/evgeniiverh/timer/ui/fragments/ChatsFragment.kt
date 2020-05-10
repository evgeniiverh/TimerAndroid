package com.evgeniiverh.timer.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniiverh.timer.DBHelper.DBHelper
import com.evgeniiverh.timer.MainActivity
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.adapter.OnTimerItemClikListher
import com.evgeniiverh.timer.adapter.TimerAdapter
import com.evgeniiverh.timer.asset.Person
import com.evgeniiverh.timer.ui.objects.Strong
import com.evgeniiverh.timer.ui.objects.TimerItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_chats.*
import java.sql.Time
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList

internal lateinit var db: DBHelper
internal var listPerson:List<Person> = ArrayList<Person>()


class ChatsFragment : BaseFragment(R.layout.fragment_chats) , OnTimerItemClikListher{

    val c =Calendar.getInstance()






    override fun onResume() {



        db=DBHelper(activity as MainActivity)
        refreshData()
        super.onResume()
        addTimerItem.setOnClickListener{sendCode()}
    }

    private fun refreshData() {
        listPerson=db.allPerson
        recycler_view.adapter = TimerAdapter(listPerson,this)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.setHasFixedSize(true)

    }


    override fun onItemClick(item: Person, position: Int) {
        Strong.Name= item.name.toString()

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.dataContainer,
                Timer_detail()
            )?.commit()

    }
    private fun sendCode(){
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hours = c.get(Calendar.HOUR_OF_DAY)
        val minutes = c.get(Calendar.MINUTE)

        var y:Int
        var m:Int
        var d:Int
        val tpd = TimePickerDialog(context as MainActivity, android.R.style.Theme_Holo_Dialog, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute->


        },hours,minutes,true)

        val dpd = DatePickerDialog(context as MainActivity,android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            y=year
            m=monthOfYear+1
            d=dayOfMonth
            tpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tpd.setMessage("    Выберите время события")
            tpd.show()

        }, year, month, day)
        dpd.setTitle("Привет")
        dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dpd.show()
    }


}
