package com.example.kotlinsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlinsample.camera.CameraActivity
import com.example.kotlinsample.dialog.DialogActivity
import com.example.kotlinsample.layout.LayoutActivity
import com.example.kotlinsample.permission.PermissionActivity
import com.example.kotlinsample.view.ViewActivity

/**
 * @author Jenking Wang
 * date 2021/11/15
 */

class MainActivity : AppCompatActivity() {

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 点击dialog按钮
         */
        val btnDialog: Button = findViewById(R.id.btn_dialog_normal)
        btnDialog.setOnClickListener {
            val intentDialog = Intent(this, DialogActivity::class.java)
            startActivity(intentDialog)
        }

        /**
         * 点击permission按钮
         */
        val btnPermission: Button = findViewById(R.id.btn_permission)
        btnPermission.setOnClickListener {
            val intentPermission = Intent(this, PermissionActivity::class.java)
            startActivity(intentPermission)
        }

        /**
         * 点击相机
         */
        val btnCamera: Button = findViewById(R.id.btn_camera)
        btnCamera.setOnClickListener {
            val intentCamera = Intent(this, CameraActivity::class.java)
            startActivity(intentCamera)
        }

        /**
         * 点击布局
         */
        val btnLayout: Button = findViewById(R.id.btn_layout)
        btnLayout.setOnClickListener {
            val intent = Intent(this, LayoutActivity::class.java)
            startActivity(intent)
        }

        /**
         * 点击控件
         */
        val btnView: Button = findViewById(R.id.btn_view)
        btnView.setOnClickListener {
            val intent = Intent(this, ViewActivity::class.java)
            startActivity(intent)
        }
    }
}