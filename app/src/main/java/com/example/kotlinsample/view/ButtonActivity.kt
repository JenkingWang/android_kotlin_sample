package com.example.kotlinsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.kotlinsample.R

class ButtonActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        /**
         * 匿名类方式实现点击事件
         */
        val btnAnonymousClick: Button = findViewById(R.id.btn_anonymous_class_click)
        btnAnonymousClick.setOnClickListener {
            Toast.makeText(this, "此按钮的点击事件是通过匿名类实现的", Toast.LENGTH_SHORT).show()
        }

        /**
         * 接口方式实现点击事件
         */
        val btnNormal: Button = findViewById(R.id.btn_normal)
        val btnImplements: Button = findViewById(R.id.btn_implements_click)
        val btnImage: ImageButton = findViewById(R.id.btn_image)
        btnImplements.setOnClickListener(this)
        btnNormal.setOnClickListener(this)
        btnImage.setOnClickListener(this)
    }

    /**
     * 继承View.OnClickListener接口，重写onClick方法，实现点击事件
     */
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_normal -> {
                Toast.makeText(this, "你点击了一个普通按钮", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_image -> {
                Toast.makeText(this, "你点击了一个ImageButton", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_implements_click -> {
                Toast.makeText(this, "此按钮的点击事件是通过继承View.OnClickListener接口实现的", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * layout指定方式，onClick事件方法
     */
    fun clickLayoutButton(view: View) {
        Toast.makeText(this, "此按钮的点击事件是通过layout指定方法实现的", Toast.LENGTH_SHORT).show()
    }
}