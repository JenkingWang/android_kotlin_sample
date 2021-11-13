package com.example.kotlinsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.kotlinsample.dialog.DialogActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btDialog: Button = findViewById(R.id.bt_dialog_normal)
        btDialog.setOnClickListener {
            val intentDialog = Intent(this, DialogActivity::class.java)
            startActivity(intentDialog)
        }
    }
}