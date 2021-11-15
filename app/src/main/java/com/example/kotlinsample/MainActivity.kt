package com.example.kotlinsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlinsample.dialog.DialogActivity
import com.example.kotlinsample.permission.PermissionActivity

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
    }
}