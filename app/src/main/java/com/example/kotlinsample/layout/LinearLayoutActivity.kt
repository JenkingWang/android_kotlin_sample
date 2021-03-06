package com.example.kotlinsample.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlinsample.R

class LinearLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_linear)

        /**
         * 点击垂直线性布局
         */
        val btnVertical: Button = findViewById(R.id.btn_vertical)
        btnVertical.setOnClickListener {
            val intent = Intent(this, LinearLayoutVerticalActivity::class.java)
            startActivity(intent)
        }

        /**
         * 点击水平线性布局
         */
        val btnHorizontal: Button = findViewById(R.id.btn_horizontal)
        btnHorizontal.setOnClickListener {
            val intent = Intent(this, LinearLayoutHorizontalActivity::class.java)
            startActivity(intent)
        }

        /**
         * 点击垂直权重线性布局
         */
        val btnVerticalWeight: Button = findViewById(R.id.btn_vertical_weight)
        btnVerticalWeight.setOnClickListener {
            val intent = Intent(this, LinearLayoutVerticalWeightActivity::class.java)
            startActivity(intent)
        }
    }
}