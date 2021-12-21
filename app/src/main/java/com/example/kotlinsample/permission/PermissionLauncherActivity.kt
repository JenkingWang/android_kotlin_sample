package com.example.kotlinsample.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.kotlinsample.R
import com.example.kotlinsample.camera_preview.CameraPreviewActivity
import com.example.kotlinsample.util.showSnackbar
import com.google.android.material.snackbar.Snackbar

class PermissionLauncherActivity :
    AppCompatActivity(),
        View.OnClickListener
{
    private lateinit var layout: View

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission has been granted. Start camera preview Activity.
                layout.showSnackbar(
                    R.string.camera_permission_granted,
                    Snackbar.LENGTH_INDEFINITE,
                    R.string.ok
                ) {
                    startCamera()
                }
            } else {
                // Permission request was denied.
                layout.showSnackbar(
                    R.string.camera_permission_denied,
                    Snackbar.LENGTH_SHORT)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_launcher)
        layout = findViewById(R.id.lyt_permission_launcher)

        initClickEvent()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_permission_launcher_start_camera -> showCameraPreview()
        }
    }

    private fun initClickEvent() {
        findViewById<Button>(R.id.btn_permission_launcher_start_camera).setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showCameraPreview() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            layout.showSnackbar(
                R.string.camera_permission_available,
                Snackbar.LENGTH_INDEFINITE,
                R.string.ok
            ) {
                startCamera()
            }
        } else requestCameraPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            layout.showSnackbar(
                R.string.camera_access_required,
                Snackbar.LENGTH_INDEFINITE,
                R.string.ok
            ) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        } else {
            // You can directly ask for the permission.
            layout.showSnackbar(
                R.string.camera_permission_not_available,
                Snackbar.LENGTH_LONG,
                R.string.ok
            ) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraPreviewActivity::class.java)
        startActivity(intent)
    }
}