package com.evgeniiverh.timer.ui.objects

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.ui.fragments.SettingsFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

class AppDrawer(val mainActivity:AppCompatActivity, val toolbar: Toolbar) {

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader

    fun create(){
        createHeader()
        createDrawer()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Создать группу")
                    .withIcon(R.drawable.ic_menu_create_groups)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Создать секретный чат")
                    .withIcon(R.drawable.ic_menu_secret_chat)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Создать канал")
                    .withIcon(R.drawable.ic_menu_create_channel)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Контакты")
                    .withIcon(R.drawable.ic_menu_contacts)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(104)
                    .withIconTintingEnabled(true)
                    .withName("Звонки")
                    .withIcon(R.drawable.ic_menu_phone)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(105)
                    .withIconTintingEnabled(true)
                    .withName("Избранное")
                    .withIcon(R.drawable.ic_menu_favorites)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(106)
                    .withIconTintingEnabled(true)
                    .withName("Настройки")
                    .withIcon(R.drawable.ic_menu_settings)
                    .withSelectable(false),
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(107)
                    .withIconTintingEnabled(true)
                    .withName("Пригласить друзей")
                    .withIcon(R.drawable.ic_menu_invate)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(108)
                    .withIconTintingEnabled(true)
                    .withName("Вопросы о Telegram")
                    .withIcon(R.drawable.ic_menu_help)
                    .withSelectable(false)
            )
            .withOnDrawerItemClickListener(object :Drawer.OnDrawerItemClickListener{
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when(position){
                        7 -> mainActivity.supportFragmentManager.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.dataContainer,
                                SettingsFragment()
                            ).commit()

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
            .addProfiles(
                ProfileDrawerItem().withName("Evgeny Verkhoturov")
                    .withEmail("+79091111111")
            ).build()

    }
}