package com.iua.agustinpereyra.view.camera

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.CAMERA_PERMISSIONS_CODE
import com.iua.agustinpereyra.controller.CAMERA_REQUIRED_PERMISSIONS
import com.iua.agustinpereyra.databinding.ActivityCameraBinding
import com.iua.agustinpereyra.view.base.BaseActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : BaseActivity() {

    // Keep track of asynchronous camera's tasks
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewBinding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Check for camera permissions and request if needed
        if (allPermissionsGranted()) {
            // TODO: Implement
        } else {
            // Ask for permission
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA_REQUIRED_PERMISSIONS), CAMERA_PERMISSIONS_CODE)
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSIONS_CODE) {
            // Check if permissions were granted
            if (allPermissionsGranted()) {
                // TODO: Implement
            } else {
                // Explain problems of not having camera permissions
                val snackBar = Snackbar.make(viewBinding.root, getString(R.string.no_camera_permission), Snackbar.LENGTH_INDEFINITE)
                snackBar.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    // Function used to check if needed permissions were granted
    private fun allPermissionsGranted() = arrayOf(CAMERA_REQUIRED_PERMISSIONS).all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}