package com.evgeniiverh.timer.ui.fragments

import android.view.View
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

internal lateinit var db: DBHelper
internal var listPerson:List<Person> = ArrayList<Person>()

class ChatsFragment : BaseFragment(R.layout.fragment_chats) , OnTimerItemClikListher{




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
        Toast.makeText(activity,"Мама дурачка", Toast.LENGTH_SHORT).show()
    }

}
