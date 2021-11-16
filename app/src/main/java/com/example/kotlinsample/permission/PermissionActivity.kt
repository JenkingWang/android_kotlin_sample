package com.example.kotlinsample.permission


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.kotlinsample.R

const val PERMISSION_REQUEST_MULTIPLE = 0

/**
 * @author Jenking Wang
 * date: 2021/11/15
 */

class PermissionActivity : AppCompatActivity() {


    /**
     * 系统回调，适合单一授权
     */
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "单一授权已授权", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "单一授权已拒绝", Toast.LENGTH_SHORT).show()
            }
        }

    /**
     * 系统回调，多个授权
     */
    val requestPermissionsLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            for ((permissionItemKey, permissionItemValue) in it) {
                if (permissionItemValue) {
                    Toast.makeText(this, "$permissionItemKey 已授权", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "$permissionItemKey 已拒绝", Toast.LENGTH_SHORT).show()
                }
            }
        }

    /**
     * 自定义回调，适合请求多个权限
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_MULTIPLE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "已拒绝", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        /**
         * 点击单个授权，系统回调launcher,获取相机权限
         */
        val btnPermissionApplySingle: Button = findViewById(R.id.btn_permission_apply_single)
        btnPermissionApplySingle.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(this, "单一权限相机权限已授权", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA)
                }
            }
        }

        /**
         * 点击多个授权，自定义回调,获取相机和存储权限
         */
        val btnPermissionApplyMultiple: Button = findViewById(R.id.btn_permission_apply_multiple)
        btnPermissionApplyMultiple.setOnClickListener {
            val multiplePermissionCode = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            var unPermissionList = mutableListOf<String>()

            for (permissionItem in multiplePermissionCode) {
                when {
                    ContextCompat.checkSelfPermission(
                        this,
                        permissionItem
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        Toast.makeText(this, "多项权限中 $permissionItem 权限已授权", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        unPermissionList.add(permissionItem)
                    }
                }
            }
            if (unPermissionList.size > 0) {
                // 自定义请求，不用launcher
                requestPermissions(
                    unPermissionList.toTypedArray(),
                    PERMISSION_REQUEST_MULTIPLE)
            } else {
                Toast.makeText(this, "多项权限都已授权", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 点击多个授权，系统回调，获取相机和存储权限
         */
        val btnPermissionApplyMultipleSystem: Button = findViewById(R.id.btn_permission_apply_multiple_system)
        btnPermissionApplyMultipleSystem.setOnClickListener {
            val multiplePermissionCode = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            var unPermissionList = mutableListOf<String>()

            for (permissionItem in multiplePermissionCode) {
                when {
                    ContextCompat.checkSelfPermission(
                        this,
                        permissionItem
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        Toast.makeText(this, "多项权限中 $permissionItem 权限已授权", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        unPermissionList.add(permissionItem)
                    }
                }
            }
            if (unPermissionList.size > 0) {
                // 系统回调launcher
                requestPermissionsLauncher.launch(unPermissionList.toTypedArray())
            } else {
                Toast.makeText(this, "多项权限都已授权", Toast.LENGTH_SHORT).show()
            }
        }

    }


}