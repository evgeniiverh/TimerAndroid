package com.evgeniiverh.timer.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.asset.Person
import kotlinx.android.synthetic.main.timer_item.view.*

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
        val textView1: TextView = itemView.itemName
        val textView2: TextView = itemView.item_do_sob
        fun initialize(item: Person, action: OnTimerItemClikListher){
            textView1.text=item.name
            textView2.text=item.date

            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
        }

    }

}

interface OnTimerItemClikListher{
    fun onItemClick(item: Person, position: Int)
}