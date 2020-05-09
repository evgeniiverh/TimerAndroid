package com.evgeniiverh.timer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.evgeniiverh.timer.R
import com.evgeniiverh.timer.databinding.FragmentChatsBinding
import com.evgeniiverh.timer.databinding.FragmentTimerDetailBinding
import com.evgeniiverh.timer.ui.objects.Strong
import kotlinx.android.synthetic.main.fragment_timer_detail.*


class Timer_detail :BaseFragment(R.layout.fragment_timer_detail) {

    lateinit var binding: FragmentTimerDetailBinding
    override fun onResume() {
        super.onResume()
        //dtextde.text = Strong.Name


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}