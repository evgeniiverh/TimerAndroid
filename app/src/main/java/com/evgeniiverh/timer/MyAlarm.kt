package com.evgeniiverh.timer

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock


abstract class MyAlarm(name: String?) : IntentService(name) {
    companion object {
        private const val TAG = "MyAlarm"
        private const val POLL_INTERVAL = 1000 * 10
        fun newIntent(context: Context?): Intent {
            return Intent(context, MyAlarm::class.java)
        }

        fun setServiceAlarm(context: Context, isOn: Boolean) {
            val i = newIntent(context)
            val pi = PendingIntent.getService(context, 0, i, 0)
            val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (isOn) {
                alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),
                    POLL_INTERVAL.toLong(),
                    pi
                )
            } else {
                alarmManager.cancel(pi)
                pi.cancel()
            }
        }
    }
}