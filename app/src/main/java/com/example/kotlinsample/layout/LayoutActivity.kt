package com.example.kotlinsample.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlinsample.R

class LayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        /**
         * 点击线性布局
         */
        val btnLinearLayout: Button = findViewById(R.id.btn_linear_layout)
        btnLinearLayout.setOnClickListener {
            val intent = Intent(this, LinearLayoutActivity::class.java)
            startActivity(intent)
        }

        /**
         * 点击相对布局
         */
        val btnRelativeLayout: Button = findViewById(R.id.btn_relative_layout)
        btnRelativeLayout.setOnClickListener {
            val intent = Intent(this, RelativeLayoutActivity::class.java)
            startActivity(intent)
        }

        /**
         * 点击约束布局
         */
        val btnConstraintLayout: Button = findViewById(R.id.btn_constraint_layout)
        btnConstraintLayout.setOnClickListener {
            val intent = Intent(this, ConstraintLayoutActivity::class.java)
            startActivity(intent)
        }
    }
}