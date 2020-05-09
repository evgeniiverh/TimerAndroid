package com.evgeniiverh.timer.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
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
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList

internal lateinit var db: DBHelper
internal var listPerson:List<Person> = ArrayList<Person>()


class ChatsFragment : BaseFragment(R.layout.fragment_chats) , OnTimerItemClikListher{

    val c =Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)



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
        val dpd =DatePickerDialog(context as MainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> }, year, month, day)
        dpd.setTitle("Привет")
        //dpd.setButton(DatePickerDialog.BUTTON_POSITIVE,tag,"Привет")
        dpd.show()
        Toast.makeText(activity,"Мама дурачка", Toast.LENGTH_SHORT).show()
    }


}
