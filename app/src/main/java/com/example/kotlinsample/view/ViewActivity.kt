package com.example.kotlinsample.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.kotlinsample.R

class ViewActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        /**
         * 设置按钮控件的监听事件
         */
        val btnViewButton: Button = findViewById(R.id.btn_view_button)
        val btnViewCheckbox: Button = findViewById(R.id.btn_view_checkbox)
        val btnViewRadio: Button = findViewById(R.id.btn_view_radio)
        val btnViewSwitch: Button = findViewById(R.id.btn_view_switch)
        val btnViewSpinner: Button = findViewById(R.id.btn_view_spinner)
        val btnViewPicker: Button = findViewById(R.id.btn_view_picker)
        btnViewButton.setOnClickListener(this)
        btnViewCheckbox.setOnClickListener(this)
        btnViewRadio.setOnClickListener(this)
        btnViewSwitch.setOnClickListener(this)
        btnViewSpinner.setOnClickListener(this)
        btnViewPicker.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_view_button -> {
                startActivity(Intent(this, ButtonActivity::class.java))
            }
            R.id.btn_view_checkbox -> {
                startActivity(Intent(this, CheckBoxActivity::class.java))
            }
            R.id.btn_view_radio -> {
                startActivity(Intent(this, RadioActivity::class.java))
            }
            R.id.btn_view_switch -> {
                startActivity(Intent(this, SwitchActivity::class.java))
            }
            R.id.btn_view_spinner -> {
                startActivity(Intent(this, SpinnerActivity::class.java))
            }
            R.id.btn_view_picker -> {
                startActivity(Intent(this, PickerActivity::class.java))
            }
        }
    }
}