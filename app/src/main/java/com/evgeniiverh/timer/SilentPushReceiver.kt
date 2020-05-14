package com.evgeniiverh.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.push.YandexMetricaPush


class SilentPushReceiver : BroadcastReceiver() {
    /**
     * Silent push message can contain user defined payload. It can be extracted from intent
     * as `String` with [YandexMetricaPush.EXTRA_PAYLOAD] constant.
     */
    override fun onReceive(context: Context, intent: Intent) {
        val payload = intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD)
        val sb = StringBuilder("Silent push received.")
        if (!TextUtils.isEmpty(payload)) {
            sb.append("\nPayload: ").append(payload)
        }
        YandexMetrica.reportEvent("Silent push")
        Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show()
    }
}