package com.example.kotlinsample.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.kotlinsample.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Jenking Wang
 * date: 2021/11/16
 */

class CameraActivity : AppCompatActivity() {

    // 相机调用类型
    var pictureType: Int = 0

    // 请求权限逻辑后的是否授权状态
    var isPermissionCameraGrated: Int = 0

    // 调用相机获取完整图片保存的路径
    lateinit var currentPhotoPath: String

    /**
     * 调用相机权限,launcher回调
     */
    val requestPermissionCameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            for ((permissionItemKey, permissionItemValue) in it) {
                if (permissionItemValue) {

                } else {
                    isPermissionCameraGrated = 2
                }
            }
            if (isPermissionCameraGrated != 2) {
                isPermissionCameraGrated = 1
            }
        }

    /**
     * 打开相机intent获取缩略图launcher回调，有2个ImageView，根据pictureType为1和2区分
     */
    val requestCameraThumbLauncher =
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

    /**
     * 打开相机intent获取完整图片uri回调launcher,有两个ImageView，根据pictureType为3和4区分
     */
    val requestCameraOriginLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    val file: File = File(currentPhotoPath)
                    if (file.exists()) {
                        val ivPhotoShow3: ImageView = findViewById(R.id.iv_photo_show_3)
                        val ivPhotoShow4: ImageView = findViewById(R.id.iv_photo_show_4)
                        val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
                        when(pictureType) {
                            3 -> ivPhotoShow3.setImageBitmap(imageBitmap)
                            4 -> ivPhotoShow4.setImageBitmap(imageBitmap)
                            else -> Toast.makeText(this, "相机调用类型错误", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "图片写入失败", Toast.LENGTH_SHORT).show()
                    }

                }
                else -> {
                    Toast.makeText(this, "相机应用调用失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

    /**
     * 选取照片回调launcher
     */
    val requestPickPictureLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    val uri: Uri? = it.data?.data
                    val ivPhotoShow5: ImageView = findViewById(R.id.iv_photo_show_5)

                    val pfd: ParcelFileDescriptor? = contentResolver.openFileDescriptor(uri!!, "r")
                    if (pfd != null) {
                        val bitmap = BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)
                        ivPhotoShow5.setImageBitmap(bitmap)
                    }
                }
                else -> {
                    Toast.makeText(this, "选取图片调用失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

    /**
     * 检查权限
     */
    private fun checkAndRequestPermissionCamera() {
        val multiplePermissionCode = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var unPermissionList = mutableListOf<String>()

        for (permissionItem in multiplePermissionCode) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    permissionItem
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //
                }
                else -> {
                    unPermissionList.add(permissionItem)
                }
            }
        }
        if (unPermissionList.size > 0) {
            // 系统回调launcher
            requestPermissionCameraLauncher.launch(unPermissionList.toTypedArray())
        } else {
            isPermissionCameraGrated = 1
        }
    }

    /**
     * 打开相机intent，获取缩略图
     */
    private fun startCameraThumb() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                requestCameraThumbLauncher.launch(takePictureIntent)
            }
        }
    }

    /**
     * 创建一个本地文件
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_HXLNW_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    /**
     * 打开相机intent，获取完整尺寸图片uri
     */
    private fun startCameraForOrigin() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(this, "创建文件失败", Toast.LENGTH_SHORT).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.kotlinsample.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    requestCameraOriginLauncher.launch(takePictureIntent)
                }
            }
        }
    }

    /**
     * 打开选取照片intent，获取照片
     */
    private fun startPickPicture() {
        Intent(Intent.ACTION_PICK).also { pickPictureIntent ->
            pickPictureIntent.setType("image/*")
            pickPictureIntent.resolveActivity(packageManager)?.also {
                requestPickPictureLauncher.launch(pickPictureIntent)
            }
        }
    }

    /**
     * onCreate方法
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        /**
         * 先请求权限
         */
        checkAndRequestPermissionCamera()

        /**
         * 点击相机调用1，设置图片1缩略图
         */
        val btnStartCamera1: Button = findViewById(R.id.btn_start_camera_1)
        btnStartCamera1.setOnClickListener {
            if (isPermissionCameraGrated == 1) {
                pictureType = 1
                startCameraThumb()
            } else {
                Toast.makeText(this, "你未授权相机权限，功能无法使用", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 点击相机调用2，设置图片2缩略图
         */
        val btnStartCamera2: Button = findViewById(R.id.btn_start_camera_2)
        btnStartCamera2.setOnClickListener {
            if (isPermissionCameraGrated == 1) {
                pictureType = 2
                startCameraThumb()
            } else {
                Toast.makeText(this, "你未授权相机权限，功能无法使用", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 点击相机调用3，获取原图
         */
        val btnStartCamera3: Button = findViewById(R.id.btn_start_camera_3)
        btnStartCamera3.setOnClickListener {
            if (isPermissionCameraGrated == 1) {
                pictureType = 3
                startCameraForOrigin()
            } else {
                Toast.makeText(this, "你未授权相机权限，功能无法使用", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 点击相机调用4，获取原图
         */
        val btnStartCamera4: Button = findViewById(R.id.btn_start_camera_4)
        btnStartCamera4.setOnClickListener {
            if (isPermissionCameraGrated == 1) {
                pictureType = 4
                startCameraForOrigin()
            } else {
                Toast.makeText(this, "你未授权相机权限，功能无法使用", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 点击选取图片
         */
        val btnPickPicture: Button = findViewById(R.id.btn_pick_picture)
        btnPickPicture.setOnClickListener {
            startPickPicture()
        }
    }
}