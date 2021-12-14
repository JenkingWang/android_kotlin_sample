package com.example.kotlinsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.kotlinsample.R

class RadioActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio)

        /**
         * 点击事件
         */
        val radMale: RadioButton = findViewById(R.id.rad_male)
        val radFemale: RadioButton = findViewById(R.id.rad_female)
        val radOther: RadioButton = findViewById(R.id.rad_other)
        radMale.setOnClickListener(this)
        radFemale.setOnClickListener(this)
        radOther.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v is RadioButton) {
            val checked: Boolean = v.isChecked
            when (v?.id) {
                R.id.rad_male -> {
                    if (checked) {
                        Toast.makeText(this, "你是男性", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.rad_female -> {
                    if (checked) {
                        Toast.makeText(this, "你是女性", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.rad_other -> {
                    if (checked) {
                        Toast.makeText(this, "你是其他", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}