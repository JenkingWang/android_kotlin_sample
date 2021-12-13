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
        btnViewButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_view_button -> {
                startActivity(Intent(this, ButtonActivity::class.java))
            }
        }
    }
}