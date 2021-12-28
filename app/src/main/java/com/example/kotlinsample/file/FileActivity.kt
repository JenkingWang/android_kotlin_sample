package com.example.kotlinsample.file

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.kotlinsample.R

class FileActivity :
    AppCompatActivity(),
    View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        initClickEvent()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_files_dir -> startActivity(Intent(this, FilesDirActivity::class.java))
            R.id.btn_external_files_dir -> startActivity(Intent(this, ExternalFilesDirActivity::class.java))
            R.id.btn_media_store -> startActivity(Intent(this, MediaStoreActivity::class.java))
        }
    }

    private fun initClickEvent() {
        findViewById<Button>(R.id.btn_files_dir).setOnClickListener(this)
        findViewById<Button>(R.id.btn_external_files_dir).setOnClickListener(this)
        findViewById<Button>(R.id.btn_media_store).setOnClickListener(this)
    }
}