package com.iua.agustinpereyra.view.camera

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.CAMERA_PERMISSIONS_CODE
import com.iua.agustinpereyra.controller.CAMERA_REQUIRED_PERMISSIONS
import com.iua.agustinpereyra.controller.FacesAnalyzer
import com.iua.agustinpereyra.databinding.ActivityCameraBinding
import com.iua.agustinpereyra.view.base.BaseActivity
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : BaseActivity() {

    private val TAG = CameraActivity::class.java.name

    // Keep track of asynchronous camera's tasks
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewBinding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Check for camera permissions and request if needed
        if (allPermissionsGranted()) {
            startCamera()
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
                startCamera()
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

    /**
     * startCamera starts the camera and binds it to the activity's lifecycle
     */
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener( Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    // Set surface provider on preview
                    it.setSurfaceProvider(viewBinding.cameraPreview.surfaceProvider)
                }

            // Set back camera as default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Set the image analyzer to use
            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, FacesAnalyzer())
                }

            // Check if nothing is bind to the camera provider
            // and bind the camera selector and preview to it
            // TODO: Install, test, and apply boxes over faces
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )
            } catch (e: Exception) {
                Log.e(TAG, "Failed while trying to bind CameraX Preview use case", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }
}