package com.evgeniiverh.timer.ui.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.timer_item.view.*
import java.util.*
import kotlin.collections.ArrayList

internal lateinit var db: DBHelper
internal var listPerson:List<Person> = ArrayList<Person>()
private lateinit var rewardedAd: RewardedAd



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

        rewardedAd = RewardedAd(context, "ca-app-pub-3940256099942544/5224354917")
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {

            }
            override fun onRewardedAdFailedToLoad(errorCode: Int) {

            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)





        addTimerItem.setOnClickListener{sendCodeItem()}




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
                                val sn = viewHolder.itemView.itemName.text.toString()
                                updateData(ss,sn)
                            }
                        })
                )
            }
        }
    }

    public fun refreshData() {
        listPerson=db.allPerson
        recycler_view.adapter = TimerAdapter(listPerson,this)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.setHasFixedSize(true)
        Log.d("myTag", "${listPerson.size}");
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

    private fun updateData(pos:Int, name: String){

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hours = c.get(Calendar.HOUR_OF_DAY)
        val minutes = c.get(Calendar.MINUTE)


        val tpd = TimePickerDialog(context as MainActivity, android.R.style.Theme_Holo_Dialog, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute->
            h=hourOfDay
            min=minute

            val dd=if (d<10)"0"+d.toString()
            else d.toString()
            val mm=if (m<10)"0"+m.toString()
            else m.toString()

            val hh=if (h<10)"0"+h.toString()
            else h.toString()
            val mmin=if (min<10)"0"+min.toString()
            else min.toString()


            val personitem = Person(
                pos,
                "$name",
                "$dd.$mm.$y",
                "$hh:$mmin"
            )
            db.updatePerson(personitem)
            refreshData()
            Toast.makeText(activity,"Успешно изменено",Toast.LENGTH_SHORT).show()




        },hours,minutes,true)

        val dpd = DatePickerDialog(context as MainActivity,android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            y=year
            m=monthOfYear+1
            d=dayOfMonth
            tpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tpd.setMessage("Выберите время события")
            tpd.show()

        }, year, month, day)
        dpd.setTitle("Выберите дату события")
        dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dpd.show()

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
    private fun sendCodeItem(){
        if(listPerson.size<3)
            sendCode()
        else if (listPerson.size>=3 && listPerson.size<6)
            if (rewardedAd.isLoaded) {

                val adCallback = object: RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        // Ad opened.
                    }
                    override fun onRewardedAdClosed() {
                        // Ad closed.
                    }
                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        // User earned reward.
                        sendCode()
                    }
                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        // Ad failed to display.
                        Toast.makeText(activity,"Ошибка, попрубуйте позже",Toast.LENGTH_SHORT).show()
                    }
                }
                rewardedAd.show(activity, adCallback)
            }
            else {
                Log.d("TAG", "The rewarded ad wasn't loaded yet.")
            }

        else Toast.makeText(activity,"Купите полную версию",Toast.LENGTH_SHORT).show()
    }



    private fun sendCode(){
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hours = c.get(Calendar.HOUR_OF_DAY)
        val minutes = c.get(Calendar.MINUTE)


        val name = EditText(context)

        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = LengthFilter(10)
        name.setFilters(FilterArray)

        val alert = AlertDialog.Builder(context as MainActivity)
            alert.setTitle("Введите наименование")
                .setView(name)
                .setPositiveButton("Создать",DialogInterface.OnClickListener{view,i->
                    val dd=if (d<10)"0"+d.toString()
                    else d.toString()
                    val mm=if (m<10)"0"+m.toString()
                    else m.toString()

                    val hh=if (h<10)"0"+h.toString()
                    else h.toString()
                    val mmin=if (min<10)"0"+min.toString()
                    else min.toString()


                    val personitem = Person(
                        0,
                        "${name.text}",
                        "$dd.$mm.$y",
                        "$hh:$mmin"
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
        dpd.setTitle("Выберите дату события")
        dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dpd.show()
    }


}


