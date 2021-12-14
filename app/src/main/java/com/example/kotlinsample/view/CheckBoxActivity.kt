package com.example.kotlinsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.example.kotlinsample.R

class CheckBoxActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkbox)

        /**
         * 点击事件
         */
        val cbBanana: CheckBox = findViewById(R.id.cb_banana)
        val cbPear: CheckBox = findViewById(R.id.cb_pear)
        val cbGrape: CheckBox = findViewById(R.id.cb_grape)
        cbBanana.setOnClickListener(this)
        cbPear.setOnClickListener(this)
        cbGrape.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v is CheckBox) {
            val checked: Boolean = v.isChecked
            when (v?.id) {
                R.id.cb_banana -> {
                    if (checked) {
                        Toast.makeText(this, "我爱吃香蕉", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "我不要香蕉", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.cb_pear -> {
                    if (checked) {
                        Toast.makeText(this, "我爱吃梨", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "我不要梨", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.cb_grape -> {
                    if (checked) {
                        Toast.makeText(this, "我爱吃葡萄", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "我不要葡萄", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}