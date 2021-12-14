package com.example.kotlinsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import com.example.kotlinsample.R

class SwitchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch)

        /**
         * 点击事件
         */
        val btnSwitchStatus: Button = findViewById(R.id.btn_switch_status)
        val swDevelop: Switch = findViewById(R.id.sw_develop)
        val tglTransfer: ToggleButton = findViewById(R.id.tgl_transfer)
        val swcVoice: SwitchCompat = findViewById(R.id.swc_voice)
        btnSwitchStatus.setOnClickListener(this)
        swDevelop.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "你开启了调试", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "你关闭了调试", Toast.LENGTH_SHORT).show()
            }
        }
        tglTransfer.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "你开启了传输", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "你关闭了传输", Toast.LENGTH_SHORT).show()
            }
        }
        swcVoice.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "你开启了语音", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "你关闭了语音", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        if (v is Button) {
            when (v.id) {
                R.id.btn_switch_status -> {
                    val swDevelop: Switch = findViewById(R.id.sw_develop)
                    val tglTransfer: ToggleButton = findViewById(R.id.tgl_transfer)
                    val swcVoice: SwitchCompat = findViewById(R.id.swc_voice)
                    var strDevelopStatus: String = if (swDevelop.isChecked) {
                        "开启"
                    } else {
                        "关闭"
                    }
                    var strTransferStatus: String = if (tglTransfer.isChecked) {
                        "开启"
                    } else {
                        "关闭"
                    }
                    var strVoiceStatus: String = if (swcVoice.isChecked) {
                        "开启"
                    } else {
                        "关闭"
                    }
                    Toast.makeText(this, "调试： $strDevelopStatus, 传输：$strTransferStatus, 语音：$strVoiceStatus", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}