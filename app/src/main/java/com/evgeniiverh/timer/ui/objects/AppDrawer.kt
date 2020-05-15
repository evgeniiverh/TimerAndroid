package com.evgeniiverh.timer.ui.objects

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.evgeniiverh.timer.MainActivity
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.asset.Person
import com.evgeniiverh.timer.ui.fragments.ChatsFragment
import com.evgeniiverh.timer.ui.fragments.SettingsFragment
import com.evgeniiverh.timer.ui.fragments.db
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import java.util.*

class AppDrawer(val mainActivity:AppCompatActivity, val toolbar: Toolbar) {

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    val c =Calendar.getInstance()
    var y=0
    var m=0
    var d=0
    var h=0
    var min=0


    fun create(){
        createHeader()
        createDrawer()

    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)
            .withSliderBackgroundColorRes(R.color.colorPrimaryDark)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(

                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Создать событие")
                    .withTextColorRes(R.color.colorAccent)
                    .withIconColorRes(R.color.colorAccent)
                    .withIcon(R.drawable.ic_menu_create_groups)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withTextColorRes(R.color.colorAccent)
                    .withIconColorRes(R.color.colorAccent)
                    .withName("Версия без рекламы")
                    .withIcon(R.drawable.ic_menu_secret_chat)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withTextColorRes(R.color.colorAccent)
                    .withIconColorRes(R.color.colorAccent)
                    .withName("Телеграмм канал")
                    .withIcon(R.drawable.ic_menu_create_channel)
                    .withSelectable(false)

            )
            .withOnDrawerItemClickListener(object :Drawer.OnDrawerItemClickListener{
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when(position){
                        1 -> {
                            val year = c.get(Calendar.YEAR)
                            val month = c.get(Calendar.MONTH)
                            val day = c.get(Calendar.DAY_OF_MONTH)
                            val hours = c.get(Calendar.HOUR_OF_DAY)
                            val minutes = c.get(Calendar.MINUTE)


                            val name = EditText(mainActivity)

                            val FilterArray = arrayOfNulls<InputFilter>(1)
                            FilterArray[0] = InputFilter.LengthFilter(15)
                            name.setFilters(FilterArray)

                            val alert = AlertDialog.Builder(mainActivity)
                            alert.setTitle("Важно сооющение")
                                .setMessage("Наименование")
                                .setView(name)
                                .setPositiveButton(
                                    "Создать",
                                    DialogInterface.OnClickListener { view, i ->
                                        val dd = if (d < 10) "0" + d.toString()
                                        else d.toString()
                                        val mm = if (m < 10) "0" + m.toString()
                                        else m.toString()

                                        val hh = if (h < 10) "0" + h.toString()
                                        else h.toString()
                                        val mmin = if (min < 10) "0" + min.toString()
                                        else min.toString()


                                        val personitem = Person(
                                            0,
                                            "${name.text}",
                                            "$dd.$mm.$y",
                                            "$hh:$mmin"
                                        )
                                        db.addPerson(personitem)
                                        mainActivity.supportFragmentManager.beginTransaction()
                                            .addToBackStack(null)
                                            .replace(
                                                R.id.dataContainer,
                                                ChatsFragment()
                                            ).commit()

                                    })
                                .setNegativeButton(
                                    "Отмена",
                                    DialogInterface.OnClickListener { view, i ->
                                        view.cancel()
                                    })
                            alert.create()


                            val tpd = TimePickerDialog(
                                mainActivity,
                                android.R.style.Theme_Holo_Dialog,
                                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                    h = hourOfDay
                                    min = minute
                                    alert.show()

                                },
                                hours,
                                minutes,
                                true
                            )

                            val dpd = DatePickerDialog(
                                mainActivity,
                                android.R.style.Theme_Holo_Dialog,
                                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                    y = year
                                    m = monthOfYear + 1
                                    d = dayOfMonth
                                    tpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    tpd.setMessage("Выберите время события")
                                    tpd.show()

                                },
                                year,
                                month,
                                day
                            )
                            dpd.setTitle("Выберите дату события")
                            dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dpd.show()


                        }





                    }
                    return false
                }
            })
            .build()
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(mainActivity)
            .withHeaderBackground(R.drawable.header)
            .build()

    }
}