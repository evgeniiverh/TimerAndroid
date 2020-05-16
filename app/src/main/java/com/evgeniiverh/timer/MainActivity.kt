package com.evgeniiverh.timer

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.evgeniiverh.timer.DBHelper.DBHelper
import com.evgeniiverh.timer.asset.Person
import com.evgeniiverh.timer.databinding.ActivityMainBinding
import com.evgeniiverh.timer.ui.fragments.ChatsFragment
import com.evgeniiverh.timer.ui.objects.AppDrawer
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.yandex.metrica.push.YandexMetricaPush


class MainActivity : AppCompatActivity(){


    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar
    internal lateinit var db: DBHelper
    internal var listPerson:List<Person> = ArrayList<Person>()







    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val config = YandexMetricaConfig.newConfigBuilder("37d795de-fc28-4e98-9a8c-1d5665a1f7e2").build()
        // Initializing the AppMetrica SDK.
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(application)

        YandexMetricaPush.init(applicationContext);


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



