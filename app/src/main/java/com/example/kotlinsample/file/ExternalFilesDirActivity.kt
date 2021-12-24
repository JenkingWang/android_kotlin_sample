package com.example.kotlinsample.file

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.kotlinsample.R
import java.io.File
import java.io.IOException
import java.util.*

class ExternalFilesDirActivity :
    AppCompatActivity(),
    View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_files_dir)

        initClick()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_externalfilesdir_state -> showState()
            R.id.btn_externalfilesdir_showdir -> showDir()
            R.id.btn_externalfilesdir_create -> createFile()
            R.id.btn_externalfilesdir_read -> readFile()
            R.id.btn_externalfilesdir_create_cache_file -> createCacheFile()
            R.id.btn_externalfilesdir_delete_cache_file -> deleteCacheFile()
        }
    }

    private fun initClick() {
        findViewById<Button>(R.id.btn_externalfilesdir_state).setOnClickListener(this)
        findViewById<Button>(R.id.btn_externalfilesdir_showdir).setOnClickListener(this)
        findViewById<Button>(R.id.btn_externalfilesdir_create).setOnClickListener(this)
        findViewById<Button>(R.id.btn_externalfilesdir_read).setOnClickListener(this)
        findViewById<Button>(R.id.btn_externalfilesdir_create_cache_file).setOnClickListener(this)
        findViewById<Button>(R.id.btn_externalfilesdir_delete_cache_file).setOnClickListener(this)
    }

    private fun showState() {
        if (isExternalStorageReadable()) {
            Toast.makeText(this, "外部空间可读", Toast.LENGTH_SHORT).show()
        }
        if (isExternalStorageWritable()) {
            Toast.makeText(this, "外部空间可写", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    private fun showDir() {
        val externalPath = this.getExternalFilesDir(null)
        Toast.makeText(this, externalPath?.path, Toast.LENGTH_SHORT).show()
    }

    private fun createFile() {
        try {
            val fileName = "aaa.txt"
            val fileContents = "Hello world2!"
            val specificFile = File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
            Toast.makeText(this, "文件创建成功" + specificFile.path, Toast.LENGTH_SHORT).show()
            specificFile.writeText(fileContents)
            Toast.makeText(this, "文件创建成功", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun readFile() {
        try {
            val fileName = "aaa.txt"
            val specificFile = File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
            val fileContents: String = specificFile.readText()
            Toast.makeText(this, fileContents, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "文件读取成功" + specificFile.path, Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createCacheFile() {
        val fileName = "cache.txt"
        val fileContents = "缓存文件创建"
        val externalCacheFile = File(this.externalCacheDir, fileName)
        externalCacheFile.writeText(fileContents)
        Toast.makeText(this, "缓存文件创建成功" + externalCacheFile.path, Toast.LENGTH_SHORT).show()
    }

    private fun deleteCacheFile() {
        val fileName = "cache.txt"
        val externalCacheFile = File(this.externalCacheDir, fileName)
        externalCacheFile.delete()
        Toast.makeText(this, "缓存文件删除成功" + externalCacheFile.path, Toast.LENGTH_SHORT).show()
    }
}