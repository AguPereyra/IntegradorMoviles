package com.iua.agustinpereyra.view.camera

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import com.google.mlkit.vision.face.Face
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.google.android.material.snackbar.Snackbar
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.CAMERA_PERMISSIONS_CODE
import com.iua.agustinpereyra.controller.CAMERA_REQUIRED_PERMISSIONS
import com.iua.agustinpereyra.controller.CoordinatesTranslator
import com.iua.agustinpereyra.controller.FacesAnalyzer
import com.iua.agustinpereyra.databinding.ActivityCameraBinding
import com.iua.agustinpereyra.view.base.BaseActivity
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : BaseActivity(), FacesAnalyzer.FacesAnalyzerResultHandler {

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
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, FacesAnalyzer(this))
                }

            // Check if nothing is bind to the camera provider
            // and bind the camera selector and preview to it
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

    // Used to draw the faces' bounding boxes
    override fun onSuccess(faces: List<Face>, imageWidth: Int, imageHeight: Int) {
        // Check if Faces were found
        if (faces.isNotEmpty()) {
            Log.d(TAG, "A face was found!")
            //TODO: Implement overlay over camera preview with bounding box
            val firstFace = faces[0] // Only care about this one
            val bounds = firstFace.boundingBox

            // Translate to view coordinates
            CoordinatesTranslator.setScaleX(viewBinding.cameraPreview.width.toFloat(),
                imageWidth.toFloat()
            )
            CoordinatesTranslator.setScaleY(viewBinding.cameraPreview.height.toFloat(),
                imageHeight.toFloat()
            )
            val drawBounds = CoordinatesTranslator.translateRect(bounds)

            viewBinding.boundingBox.setBoundingBox(drawBounds)
        } else {
            Log.d(TAG, "Face not found.")
            viewBinding.boundingBox.clearBoundingBox()
        }
    }

    override fun onFailure(exception: Exception) {
        Log.i(TAG, exception.message?: "Something went wrong while trying to detect faces on image.")
        exception.printStackTrace()
        throw exception
    }
}