package com.evgeniiverh.timer.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.asset.Person
import kotlinx.android.synthetic.main.timer_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TimerAdapter(private val timerList: List<Person>, val clikListher: OnTimerItemClikListher) : RecyclerView.Adapter<TimerAdapter.TimerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.timer_item,
        parent,false)
       return TimerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
       // val currentItem = timerList[position]
       // holder.textView1.text = currentItem.text1
       // holder.textView2.text = currentItem.text2
        holder.initialize(timerList.get(position), clikListher)



    }

    override fun getItemCount() = timerList.size

    class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



        val itemName: TextView = itemView.itemName
        val itemDateTime: TextView = itemView.itemData
        val item_do: TextView = itemView.item_do_sob
        val dni: TextView = itemView.itemDay
        val itemid:TextView=itemView.itemId

        fun initialize(item: Person, action: OnTimerItemClikListher){
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)+1
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hours = c.get(Calendar.HOUR_OF_DAY)
            val minutes = c.get(Calendar.MINUTE)

            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val dateS = sdf.parse("${item.date} ${item.time}")
            val dateT = sdf.parse("$day.$month.$year $hours:$minutes")



            val itemSob = if(dateT.compareTo(dateS)<0)
                "До события"
            else
                "Прошло"

            val dtime = if(dateT.compareTo(dateS)<0)
                dateS.time-dateT.time
            else
                dateT.time-dateS.time

            val dday = dtime/86400000





            itemid.text=item.id.toString()
            itemName.text=item.name
            item_do.text=itemSob
            itemDateTime.text="${item.date} ${item.time}"

            dni.text=dday.toString()+if(dday.toInt()%10==1 && dday.toInt()!=11)" день"
            else if(dday.toInt()>1 && dday.toInt()<5 ||dday.toInt()%10>1 && dday.toInt()%10<5 && dday.toInt()>20 ) " дня"
            else " дней"


            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
        }

    }

}

interface OnTimerItemClikListher{
    fun onItemClick(item: Person, position: Int)
}