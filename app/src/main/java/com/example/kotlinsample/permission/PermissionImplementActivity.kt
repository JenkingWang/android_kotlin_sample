package com.example.kotlinsample.permission


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.example.kotlinsample.R
import com.example.kotlinsample.camera_preview.CameraPreviewActivity
import com.example.kotlinsample.util.checkSelfPermissionCompat
import com.example.kotlinsample.util.requestPermissionsCompat
import com.example.kotlinsample.util.shouldShowRequestPermissionRationaleCompat
import com.example.kotlinsample.util.showSnackbar
import com.google.android.material.snackbar.Snackbar

const val PERMISSION_REQUEST_CAMERA = 0

/**
 * @author Jenking Wang
 * date: 2021/11/15
 */

class PermissionImplementActivity :
    AppCompatActivity(),
    View.OnClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback
{

    private lateinit var layout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_implement)
        layout = findViewById(R.id.main_layout)

        initClickEvent()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layout.showSnackbar(R.string.camera_permission_granted, Snackbar.LENGTH_SHORT)
                startCamera()
            } else {
                layout.showSnackbar(R.string.camera_permission_denied, Snackbar.LENGTH_SHORT)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_permission_implement_start_camera -> showCameraPreview()
        }
    }

    private fun initClickEvent() {
        findViewById<Button>(R.id.btn_permission_implement_start_camera).setOnClickListener(this)
    }

    private fun showCameraPreview() {
        if (checkSelfPermissionCompat(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {
            layout.showSnackbar(R.string.camera_permission_available, Snackbar.LENGTH_SHORT)
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.CAMERA)) {
            layout.showSnackbar(R.string.camera_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok) {
                requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CAMERA)
            }

        } else {
            layout.showSnackbar(R.string.camera_permission_not_available, Snackbar.LENGTH_SHORT)
            requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraPreviewActivity::class.java)
        startActivity(intent)
    }

}