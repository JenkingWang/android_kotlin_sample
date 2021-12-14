package com.example.kotlinsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.kotlinsample.R

class SpinnerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    /**
     * 局部变量存储spinner选项值
     */
    lateinit var strFruitSelected: String
    lateinit var strSeasonSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)

        // 创建spinner
        createSpinner()

        /**
         * 点击事件
         */
        val btnSpinnerSubmit: Button = findViewById(R.id.btn_spinner_submit)
        btnSpinnerSubmit.setOnClickListener(this)
    }

    /**
     * 创建spinner
     */
    fun createSpinner() {
        /**
         * 通过代码创建适配器
         */
        val spnFruit: Spinner = findViewById(R.id.spn_fruit)
        spnFruit.onItemSelectedListener = this
        val arrFruit = arrayOf("香蕉", "梨", "葡萄", "桔子")
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrFruit
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnFruit.adapter = adapter
        }

        /**
         * 通过资源创建适配器
         */
        val spnSeason: Spinner = findViewById(R.id.spn_season)
        spnSeason.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.seasons_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnSeason.adapter = adapter
        }
    }

    /**
     * spinner选中方法
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position)
        when (parent?.id) {
            R.id.spn_fruit -> {
                strFruitSelected = selectedItem.toString()
                Toast.makeText(this, "你选择的水果是：${selectedItem.toString()}", Toast.LENGTH_SHORT).show()
            }
            R.id.spn_season -> {
                strSeasonSelected = selectedItem.toString()
                Toast.makeText(this, "你选择的季节是：${selectedItem.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * spinner 什么都没选方法
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "没有选择", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        if (v is Button) {
            when (v.id) {
                R.id.btn_spinner_submit -> {
                    Toast.makeText(this, "你当前选择的是：$strFruitSelected, $strSeasonSelected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}