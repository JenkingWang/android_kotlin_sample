package com.example.kotlinsample.permission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.kotlinsample.R

class PermissionListActivity :
    AppCompatActivity(),
    View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_list)

        initClickEvent()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_permission_implement -> startActivity(Intent(this, PermissionImplementActivity::class.java))
            R.id.btn_permission_launcher -> startActivity(Intent(this, PermissionLauncherActivity::class.java))
            R.id.btn_permission_multiple -> startActivity(Intent(this, PermissionMultipleActivity::class.java))
        }
    }

    private fun initClickEvent() {
        findViewById<Button>(R.id.btn_permission_implement).setOnClickListener(this)
        findViewById<Button>(R.id.btn_permission_launcher).setOnClickListener(this)
        findViewById<Button>(R.id.btn_permission_multiple).setOnClickListener(this)
    }
}