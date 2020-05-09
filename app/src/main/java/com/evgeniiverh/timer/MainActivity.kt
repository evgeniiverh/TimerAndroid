package com.evgeniiverh.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.evgeniiverh.timer.DBHelper.DBHelper
import com.evgeniiverh.timer.adapter.TimerAdapter
import com.evgeniiverh.timer.asset.Person

import com.evgeniiverh.timer.databinding.ActivityMainBinding
import com.evgeniiverh.timer.ui.fragments.ChatsFragment
import com.evgeniiverh.timer.ui.objects.AppDrawer

class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar
    internal lateinit var db: DBHelper
    internal var listPerson:List<Person> = ArrayList<Person>()




    override fun onCreate(savedInstanceState: Bundle?) {

        db= DBHelper(this)
        generateDummyList(10)


        super.onCreate(savedInstanceState)
        mBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)



        }

    private fun generateDummyList(size: Int){
        for (i in 1 until size+1) {
            val personitem = Person(
                Integer.parseInt("$i")+100,
                "Не курю $i",
                "12.12.2020 $i",
                "18:12 $i"
            )
             db.addPerson(personitem)
        }


    }




    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }
    private fun initFunc(){
        setSupportActionBar(mToolbar)
            mAppDrawer.create()
            supportFragmentManager.beginTransaction()
                .replace(R.id.dataContainer,
                    ChatsFragment()
                ).commit()

    }

    override fun onBackPressed() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer,
                ChatsFragment()
            ).commit()
    }

    private fun initFields(){
        mToolbar = mBinding.mainToolbar
        mAppDrawer=AppDrawer(this,mToolbar)
    }

}
