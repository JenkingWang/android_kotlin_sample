package com.example.kotlinsample.file

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.kotlinsample.R
import java.io.File
import java.io.IOException

class FilesDirActivity :
    AppCompatActivity(),
    View.OnClickListener
{
    lateinit var curTmpFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files_dir)

        initClickEvent()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_filesdir_showdir -> showdir()
            R.id.btn_filesdir_write -> writeFile()
            R.id.btn_filesdir_read -> readFile()
            R.id.btn_filesdir_list -> listFile()
            R.id.btn_filesdir_create_dir -> createDir()
            R.id.btn_filesdir_create_tmp_file -> createTmpFile()
            R.id.btn_filesdir_delete_tmp_file -> deleteTmpFile()
        }
    }

    private fun initClickEvent() {
        findViewById<Button>(R.id.btn_filesdir_showdir).setOnClickListener(this)
        findViewById<Button>(R.id.btn_filesdir_write).setOnClickListener(this)
        findViewById<Button>(R.id.btn_filesdir_read).setOnClickListener(this)
        findViewById<Button>(R.id.btn_filesdir_list).setOnClickListener(this)
        findViewById<Button>(R.id.btn_filesdir_create_dir).setOnClickListener(this)
        findViewById<Button>(R.id.btn_filesdir_create_tmp_file).setOnClickListener(this)
        findViewById<Button>(R.id.btn_filesdir_delete_tmp_file).setOnClickListener(this)
    }

    private fun showdir() {
        val dirPath: String = this.filesDir.toString()
        Toast.makeText(this, dirPath, Toast.LENGTH_SHORT).show()
    }

    private fun writeFile() {
        val fileName = "my.txt"
        val fileContents = "Hello world!"
        this.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
            Toast.makeText(this, "文件写入成功", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readFile() {
        val fileName = "my.txt"
        val fileContents: String = this.openFileInput(fileName).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some$text\n"
            }
        }
        Toast.makeText(this, fileContents, Toast.LENGTH_SHORT).show()
    }

    private fun listFile() {
        val files: Array<String> = this.fileList()
        for (file in files) {
            Toast.makeText(this, file, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createDir() {
        val subDir = this.getDir("sub_dir1", Context.MODE_PRIVATE)
        Toast.makeText(this, "子目录创建成功" + subDir.path, Toast.LENGTH_SHORT).show()
    }

    private fun createTmpFile() {
        try {
            val fileName = "tmp"
            val tmpFile = File.createTempFile(fileName, ".txt", this.cacheDir)
            curTmpFile = tmpFile
            Toast.makeText(this, tmpFile.path, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "临时文件创建成功", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTmpFile() {
        try {
            if (curTmpFile.exists()) {
                curTmpFile.delete()
                Toast.makeText(this, "缓存文件删除成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "缓存文件不存在", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}