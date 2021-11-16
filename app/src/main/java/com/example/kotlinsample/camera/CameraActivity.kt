package com.example.kotlinsample.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.kotlinsample.R

/**
 * @author Jenking Wang
 * date: 2021/11/16
 */

class CameraActivity : AppCompatActivity() {

    // 相机调用类型
    var pictureType: Int = 0

    /**
     * 调用相机权限
     */
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // 用户授权后打开相机应用
                requestCameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            } else {
                Toast.makeText(this, "未获得授权，功能无法使用", Toast.LENGTH_SHORT).show()
            }
        }

    /**
     * 打开相机intent回调
     */
    val requestCameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    val imageBitmap = it.data!!.extras!!.get("data") as Bitmap
                    val ivPhotoShow1: ImageView = findViewById(R.id.iv_photo_show_1)
                    val ivPhotoShow2: ImageView = findViewById(R.id.iv_photo_show_2)
                    when(pictureType) {
                        1 -> ivPhotoShow1.setImageBitmap(imageBitmap)
                        2 -> ivPhotoShow2.setImageBitmap(imageBitmap)
                        else -> Toast.makeText(this, "相机调用类型错误", Toast.LENGTH_SHORT).show()
                    }


                }
                else -> {
                    Toast.makeText(this, "相机应用调用失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun checkPermission() {

    }

    /**
     * 打开相机intent
     */
    private fun startCamera() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                requestCameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        /**
         * 点击相机调用1
         */
        val btnStartCamera1: Button = findViewById(R.id.btn_start_camera_1)
        btnStartCamera1.setOnClickListener {
            pictureType = 1
            startCamera()
        }

        /**
         * 点击相机调用2
         */
        val btnStartCamera2: Button = findViewById(R.id.btn_start_camera_2)
        btnStartCamera2.setOnClickListener {
            pictureType = 2
            startCamera()
        }
    }
}