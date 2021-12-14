package com.example.kotlinsample.lib.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.kotlinsample.dialog.DialogNoticeFragment
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    internal lateinit var listener: NoticeDialogListener

    /**
     * 传递宿主监听器
     */
    interface NoticeDialogListener {
        fun onTimeSetOK(dialog: DialogFragment, hourOfDay: Int, minute: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // 本地弹出消息
        // Toast.makeText(requireContext(), "你选择的时间是：${hourOfDay}:${minute}", Toast.LENGTH_SHORT).show()
        // 传递给宿主
        listener.onTimeSetOK(this, hourOfDay, minute)
    }

    /**
     * 绑定宿主
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as TimePickerFragment.NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }
}
