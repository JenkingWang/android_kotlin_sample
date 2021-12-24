package com.example.kotlinsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.kotlinsample.camera.CameraActivity
import com.example.kotlinsample.dialog.DialogActivity
import com.example.kotlinsample.file.FileActivity
import com.example.kotlinsample.layout.LayoutActivity
import com.example.kotlinsample.permission.PermissionListActivity
import com.example.kotlinsample.view.ViewActivity

/**
 * @author Jenking Wang
 * date 2021/11/15
 */

class MainActivity :
    AppCompatActivity(),
    View.OnClickListener
{

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initClickEvent()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_dialog_normal -> startActivity(Intent(this, DialogActivity::class.java))
            R.id.btn_permission -> startActivity(Intent(this, PermissionListActivity::class.java))
            R.id.btn_camera -> startActivity(Intent(this, CameraActivity::class.java))
            R.id.btn_layout -> startActivity(Intent(this, LayoutActivity::class.java))
            R.id.btn_view -> startActivity(Intent(this, ViewActivity::class.java))
            R.id.btn_file -> startActivity(Intent(this, FileActivity::class.java))
        }
    }

    private fun initClickEvent() {
        findViewById<Button>(R.id.btn_dialog_normal).setOnClickListener(this)
        findViewById<Button>(R.id.btn_permission).setOnClickListener(this)
        findViewById<Button>(R.id.btn_camera).setOnClickListener(this)
        findViewById<Button>(R.id.btn_layout).setOnClickListener(this)
        findViewById<Button>(R.id.btn_view).setOnClickListener(this)
        findViewById<Button>(R.id.btn_file).setOnClickListener(this)
    }
}