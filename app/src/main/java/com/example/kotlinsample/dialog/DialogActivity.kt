package com.example.kotlinsample.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.kotlinsample.R

/**
 * @author Jenking Wang
 * date 2021/11/15
 */

class DialogActivity : AppCompatActivity(), DialogNoticeFragment.NoticeDialogListener {

    /**
     * onCreate方法
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        /**
         * 点击普通dialog
         */
        val btnDialogNormal: Button = findViewById(R.id.btn_dialog_normal)
        btnDialogNormal.setOnClickListener {
            DialogNormalFragment().show(supportFragmentManager, "dialog_normal")
        }

        /**
         * 点击列表dialog
         */
        val btnDialogList: Button = findViewById(R.id.btn_permission)
        btnDialogList.setOnClickListener {
            DialogListFragment().show(supportFragmentManager, "dialog_list")
        }

        /**
         * 点击传参dialog
         */
        val btnDialogParam: Button = findViewById(R.id.bt_dialog_param)
        btnDialogParam.setOnClickListener {
            val etParam: EditText = findViewById(R.id.et_param)
            DialogParamFragment(etParam.text.toString()).show(supportFragmentManager, "dialog_param")
        }

        /**
         * 点击复选框列表dialog
         */
        val btnDialogListCheckbox: Button = findViewById(R.id.bt_dialog_list_checkbox)
        btnDialogListCheckbox.setOnClickListener {
            DialogListCheckboxFragment().show(supportFragmentManager, "dialog_list_checkbox")
        }

        /**
         * 点击自定义布局dialog
         */
        val btnDialogCustomLayout: Button = findViewById(R.id.btn_camera)
        btnDialogCustomLayout.setOnClickListener {
            DialogCustomLayoutFragment().show(supportFragmentManager, "dialog_custom_layout")
        }

        /**
         * 点击底部dialog
         */
        val btnDialogBottom: Button = findViewById(R.id.btn_dialog_bottom)
        btnDialogBottom.setOnClickListener {
            DialogBottomLayoutFragment().show(supportFragmentManager, "dialog_bottom")
        }

        val btnDialogNOtice: Button = findViewById(R.id.btn_dialog_notice)
        btnDialogNOtice.setOnClickListener {
            DialogNoticeFragment().show(supportFragmentManager, "dialog_notice")
        }
    }

    /**
     * 底部dialog点击选项1
     */
    fun clickDialogBottomItem1(view: View) {
        Toast.makeText(this, "你点击了底部dialog的拍摄", Toast.LENGTH_SHORT).show()
    }

    fun clickDialogBottomItem2(view: View) {
        Toast.makeText(this, "你点击了底部dialog的相册选择", Toast.LENGTH_SHORT).show()
    }

    fun clickDialogBottomItem3(view: View) {
        Toast.makeText(this, "你点击了底部dialog的取消", Toast.LENGTH_SHORT).show()
    }

    /**
     * 传递宿主dialog里的接口里的方法
     */
    override fun onDialogPositiveClick(dialog: DialogFragment) {
        // User touched the dialog's positive button
        Toast.makeText(this, "点击了确定 ${dialog.tag}", Toast.LENGTH_SHORT).show()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // User touched the dialog's negative button
        Toast.makeText(this, "点击了取消${dialog.tag}", Toast.LENGTH_SHORT).show()
    }
}

/**
 * 内部类，创建普通dialog
 */
class DialogNormalFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("普通标题")
                .setMessage("普通内容")
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(requireContext(), "你点击了确定", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(requireContext(), "你点击了取消", Toast.LENGTH_SHORT).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

/**
 * 内部类，创建列表dialog
 */
class DialogListFragment : DialogFragment() {

    companion object {
        val listDialogArray = arrayOf("Banana", "Pear", "Apple", "Orange")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("列表dialog标题")
                .setItems(DialogListFragment.listDialogArray,
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(requireContext(), "你点击了${DialogListFragment.listDialogArray[which].toString()}", Toast.LENGTH_SHORT).show()
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

/**
 * 内部类，创建传参dialog
 */
class DialogParamFragment(val param: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("传参dialog标题")
                .setMessage("点击确定查看param")
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(requireContext(), "你输入的param是：$param", Toast.LENGTH_SHORT).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

/**
 * 内部类，创建复选框列表dialog
 */
class DialogListCheckboxFragment : DialogFragment() {

    companion object {
        val listArray = arrayOf("Banana", "Pear", "Apple", "Orange")
        val selectedItems = ArrayList<Int>()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            builder.setTitle("复选框列表dialog")
                .setMultiChoiceItems(listArray, null,
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            selectedItems.add(which)
                        } else if (selectedItems.contains(which)) {
                            selectedItems.remove(which)
                        }
                    })
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(requireContext(), "你选择了： ${selectedItems.toString()}", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(requireContext(), "你点击了取消按钮", Toast.LENGTH_SHORT).show()
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}

/**
 * 内部类，创建自定义布局dialog
 */
class DialogCustomLayoutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.dialog_custom_layout, null))
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()!!.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}

/**
 * 创建底部dialog
 */
class DialogBottomLayoutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.dialog_bottom_layout, null))
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()!!.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog ?: return
        val window = dialog.window ?: return
        val params = window.attributes
        val dm = activity?.resources?.displayMetrics

//            params.width = dm!!.widthPixels
        params.gravity = Gravity.BOTTOM
        Log.d("bbbb", dm!!.widthPixels.toString())
        window.attributes = params
    }
}


/**
 * 创建传递宿主dialog
 */
class DialogNoticeFragment : DialogFragment() {
    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Build the dialog and set up the button click handlers
            val builder = AlertDialog.Builder(it)

            builder.setMessage("内容区")
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send the positive button event back to the host activity
                        listener.onDialogPositiveClick(this)
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send the negative button event back to the host activity
                        listener.onDialogNegativeClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }
}
























