package com.example.kotlinsample.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.kotlinsample.R

class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout)

        /**
         * 点击改变tv1的长度
         */
        val btnChangeTv1Length: Button = findViewById(R.id.btn_change_tv1_length)
        btnChangeTv1Length.setOnClickListener {
            val tv1: TextView = findViewById(R.id.tv_1)
            tv1.text = "是伐啦可接受的菲拉斯大富科技阿萨德咖啡机阿拉丁"
        }
    }
}