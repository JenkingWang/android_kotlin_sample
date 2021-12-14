package com.example.kotlinsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.kotlinsample.R
import com.example.kotlinsample.lib.fragment.DatePickerFragment
import com.example.kotlinsample.lib.fragment.TimePickerFragment

class PickerActivity:
    AppCompatActivity(),
    View.OnClickListener,
    TimePickerFragment.NoticeDialogListener,
    DatePickerFragment.NoticeDialogListener
{

    lateinit var timePickerFragment: TimePickerFragment
    lateinit var datePickerFragment: DatePickerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker)

        /**
         * 点击事件
         */
        val btnTimePicker: Button = findViewById(R.id.btn_time_picker)
        val btnDatePicker: Button = findViewById(R.id.btn_date_picker)
        btnTimePicker.setOnClickListener(this)
        btnDatePicker.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v is Button) {
            when (v.id) {
                R.id.btn_time_picker -> {
                    timePickerFragment = TimePickerFragment()
                    timePickerFragment.show(supportFragmentManager, "timePicker")
                }
                R.id.btn_date_picker -> {
                    datePickerFragment = DatePickerFragment()
                    datePickerFragment.show(supportFragmentManager, "datePicker")
                }
            }
        }
    }

    /**
     * 实现TimePickerFragment.NoticeDialogListener接口
     */
    override fun onTimeSetOK(dialog: DialogFragment, hourOfDay: Int, minute: Int) {
        val tvTimeShow: TextView = findViewById(R.id.tv_time_show)
        tvTimeShow.text = "$hourOfDay:$minute"
    }

    /**
     * 实现DatePickerFragment.NoticeDialogListener接口
     */
    override fun onDateSetOK(dialog: DialogFragment, year: Int, month: Int, day: Int) {
        val tvDateShow: TextView = findViewById(R.id.tv_date_show)
        tvDateShow.text = "$year-$month-$day"
    }
}