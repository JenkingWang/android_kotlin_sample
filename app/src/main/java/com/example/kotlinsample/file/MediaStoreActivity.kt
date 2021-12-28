package com.example.kotlinsample.file

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.kotlinsample.R
import com.example.kotlinsample.constant.PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
import com.example.kotlinsample.util.checkSelfPermissionCompat
import com.example.kotlinsample.util.requestPermissionsCompat
import com.example.kotlinsample.util.shouldShowRequestPermissionRationaleCompat
import com.example.kotlinsample.util.showSnackbar
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class MediaStoreActivity :
    AppCompatActivity(),
    View.OnClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback
{

    private lateinit var layout: View

    private lateinit var currentPhotoPath: String
    private lateinit var currentPhotoUri: Uri

    /**
     * 拍照，显示原图的回调Launcher
     */
    private val takePhotoLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    showImagesTakePhoto()
                }
                else -> {
                    Toast.makeText(this, "相机intent调用失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

    /**
     * 拍照，添加到媒体库的回调Launcher
     */
    private val takePhotoMediaLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    showImagesTakePhotoMedia()
                }
                else -> {
                    Toast.makeText(this, "相机intent调用失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_store)
        layout = findViewById(R.id.lyt_media_store)

        initClick()
    }

    /**
     * 请求权限的回调
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layout.showSnackbar(R.string.read_external_storage_permission_granted, Snackbar.LENGTH_SHORT)
                showImages()
            } else {
                layout.showSnackbar(R.string.read_external_storage_permission_denied, Snackbar.LENGTH_SHORT)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_mediastore_show -> showImagesPreview()
            R.id.btn_mediastore_show_origin -> showImagesOriginPreview()
            R.id.btn_mediastore_take_photo_show -> takePhoto()
            R.id.btn_mediastore_take_photo_media -> takePhotoMedia()
        }
    }

    private fun initClick() {
        findViewById<Button>(R.id.btn_mediastore_show).setOnClickListener(this)
        findViewById<Button>(R.id.btn_mediastore_show_origin).setOnClickListener(this)
        findViewById<Button>(R.id.btn_mediastore_take_photo_show).setOnClickListener(this)
        findViewById<Button>(R.id.btn_mediastore_take_photo_media).setOnClickListener(this)
    }

    /**
     * 显示三张缩略图Preview，查看是否有READ_EXTERNAL_STORAGE权限
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImagesPreview() {
        if (checkSelfPermissionCompat(Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
            showImages()
        } else {
            requestReadExternalStoragePermission()
        }
    }

    /**
     * 显示3张原图Preview，先查看是否有READ_EXTERNAL_STORAGE权限
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImagesOriginPreview() {
        if (checkSelfPermissionCompat(Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
            showImagesOrigin()
        } else {
            requestReadExternalStoragePermission()
        }
    }

    /**
     * 请求READ_EXTERNAL_STORAGE权限
     */
    private fun requestReadExternalStoragePermission() {
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            layout.showSnackbar(R.string.read_external_storage_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok) {
                requestPermissionsCompat(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
                )
            }
        } else {
            layout.showSnackbar(R.string.read_external_storage_permission_not_available, Snackbar.LENGTH_SHORT)
            requestPermissionsCompat(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
        }
    }

    /**
     * 查询媒体库，显示3张缩略图
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImages() {
        val videoList = mutableListOf<Video>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )

        val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
        val selectionArgs = arrayOf(
            dateToTimestamp(day = 22, month = 10, year = 2008).toString()
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} ASC"

        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateModifiedColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                // Get values of columns for a given Images.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val dateModified =
                    Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList += Video(id, contentUri, name, dateModified)
            }
        }
        Toast.makeText(this, "图片数：" + videoList.size, Toast.LENGTH_SHORT).show()
        showImagesThumbnail(videoList)
    }

    /**
     * 显示3张缩略图
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImagesThumbnail(videoList: List<Video>) {
        if (videoList.size >= 3) {
            findViewById<ImageView>(R.id.iv_mediastore_show1).setImageBitmap(contentResolver.loadThumbnail(videoList[0].uri, Size(100, 100), null))
            findViewById<ImageView>(R.id.iv_mediastore_show2).setImageBitmap(contentResolver.loadThumbnail(videoList[1].uri, Size(100, 100), null))
            findViewById<ImageView>(R.id.iv_mediastore_show3).setImageBitmap(contentResolver.loadThumbnail(videoList[2].uri, Size(100, 100), null))
        } else {
            Toast.makeText(this, "图片数少于3张，不显示罢了", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 查询媒体库，显示3张原图
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImagesOrigin() {
        val videoList = mutableListOf<Video>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )

        val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
        val selectionArgs = arrayOf(
            dateToTimestamp(day = 22, month = 10, year = 2008).toString()
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} ASC"

        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateModifiedColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                // Get values of columns for a given Images.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val dateModified =
                    Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList += Video(id, contentUri, name, dateModified)
            }
        }
        Toast.makeText(this, "图片数：" + videoList.size, Toast.LENGTH_SHORT).show()
        showImagesOriginPerform(videoList)
    }

    /**
     * 显示3张原图
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImagesOriginPerform(videoList: List<Video>) {
        if (videoList.size >= 3) {
            contentResolver.openFileDescriptor(videoList[0].uri, "r").use { pfd ->
                findViewById<ImageView>(R.id.iv_mediastore_show_origin1).setImageBitmap(BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor))
            }
            contentResolver.openFileDescriptor(videoList[1].uri, "r").use { pfd ->
                findViewById<ImageView>(R.id.iv_mediastore_show_origin2).setImageBitmap(BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor))
            }
            contentResolver.openFileDescriptor(videoList[2].uri, "r").use { pfd ->
                findViewById<ImageView>(R.id.iv_mediastore_show_origin3).setImageBitmap(BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor))
            }
        } else {
            Toast.makeText(this, "图片数少于3张，不显示罢了", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 创建外部专属空间图片，将图片URI传给相机intent，调用相机拍照
     */
    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.kotlinsample.fileprovider",
                        it
                    )
                    currentPhotoUri = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePhotoLauncher.launch(takePictureIntent)
                }
            }
        }
    }

    /**
     * 创建外部专属空间照片
     */
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun takePhotoMedia() {
        val imageContentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "my image1.jpeg")
        }
        val photoURI = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageContentValues)
        currentPhotoUri = photoURI!!
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePhotoMediaLauncher.launch(takePictureIntent)
            }
        }
    }

    /**
     * 将拍照照片显示到view
     */
    private fun showImagesTakePhoto() {
        contentResolver.openFileDescriptor(currentPhotoUri, "r").use { pfd ->
            findViewById<ImageView>(R.id.iv_mediastore_show_photo).setImageBitmap(BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor))
        }
    }

    /**
     * 将媒体库中存入的拍照的照片，显示到view
     */
    private fun showImagesTakePhotoMedia() {
        contentResolver.openFileDescriptor(currentPhotoUri, "r").use { pfd ->
            findViewById<ImageView>(R.id.iv_mediastore_show_photo_media).setImageBitmap(BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor))
        }
    }

    /**
     * 将年月日转换成秒
     */
    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }
}

data class Video(
    val id: Long,
    val uri: Uri,
    val name: String,
    val dateAdded: Date
)