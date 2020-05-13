package com.evgeniiverh.timer.ui.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evgeniiverh.timer.DBHelper.DBHelper
import com.evgeniiverh.timer.DBHelper.MyButton
import com.evgeniiverh.timer.DBHelper.MySwipeHelper
import com.evgeniiverh.timer.Listener.MyButtonClickListener
import com.evgeniiverh.timer.MainActivity
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.adapter.OnTimerItemClikListher
import com.evgeniiverh.timer.adapter.TimerAdapter
import com.evgeniiverh.timer.asset.Person
import com.evgeniiverh.timer.ui.objects.Strong
import com.evgeniiverh.timer.ui.objects.TimerItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.timer_item.*
import kotlinx.android.synthetic.main.timer_item.view.*
import java.sql.Time
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList

internal lateinit var db: DBHelper
internal var listPerson:List<Person> = ArrayList<Person>()


class ChatsFragment : BaseFragment(R.layout.fragment_chats) , OnTimerItemClikListher{

    val c =Calendar.getInstance()
    var y=0
    var m=0
    var d=0
    var h=0
    var min=0






    override fun onResume() {



        db=DBHelper(activity as MainActivity)
        refreshData()
        super.onResume()

        addTimerItem.setOnClickListener{sendCode()}

        //addSwipe
        val swipe = object :MySwipeHelper(context as MainActivity, recycler_view,200)
        {
            override fun instantlateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                //AddButton
                buffer.add(
                    MyButton(context as MainActivity,
                        "Delete",
                        30,
                        0,
                        Color.parseColor("#FF3C30"),
                        object : MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                val ss = viewHolder.itemView.itemId.text.toString().toInt()
                                deletData(ss)

                            }
                        })
                )

                buffer.add(
                    MyButton(context as MainActivity,
                        "Update",
                        30,
                        0,
                        Color.parseColor("#FF9502"),
                        object : MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                val ss = viewHolder.itemView.itemId.text.toString().toInt()
                                Toast.makeText(activity,"Update ID"+ss,Toast.LENGTH_SHORT).show()
                            }
                        })
                )
            }
        }
    }

    private fun refreshData() {
        listPerson=db.allPerson
        recycler_view.adapter = TimerAdapter(listPerson,this)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.setHasFixedSize(true)
    }

    private fun deletData(pos:Int){

        val personitem = Person(
            pos,
            "",
            "",
            ""
        )
        db.deletePerson(personitem)
        refreshData()

        Toast.makeText(activity,"Успешно удалено",Toast.LENGTH_SHORT).show()

    }



    override fun onItemClick(item: Person, position: Int) {


        val bundle = Bundle()

        Strong.Name= item.name.toString()
        Strong.Date= item.date.toString()
        Strong.Time= item.time.toString()

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


        val name = EditText(context)

        val alert = AlertDialog.Builder(context as MainActivity)
            alert.setTitle("Важно сооющение")
                .setMessage("Наименование")
                .setView(name)
                .setPositiveButton("Создать",DialogInterface.OnClickListener{view,i->


                    val personitem = Person(
                        0,
                        "${name.text}",
                        "$d.$m.$y",
                        "$h:$min"
                    )
                    db.addPerson(personitem)
                    refreshData()
                })
                .setNegativeButton("Отмена",DialogInterface.OnClickListener{view,i->
                    view.cancel()
                })
        alert.create()




        val tpd = TimePickerDialog(context as MainActivity, android.R.style.Theme_Holo_Dialog, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute->
            h=hourOfDay
            min=minute
            alert.show()

        },hours,minutes,true)

        val dpd = DatePickerDialog(context as MainActivity,android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            y=year
            m=monthOfYear+1
            d=dayOfMonth
            tpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tpd.setMessage("Выберите время события")
            tpd.show()

        }, year, month, day)
        dpd.setTitle("Привет")
        dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dpd.show()
    }


}


