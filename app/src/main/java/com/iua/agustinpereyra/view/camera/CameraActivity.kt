package com.iua.agustinpereyra.view.camera

import android.os.Bundle
import com.iua.agustinpereyra.databinding.ActivityCameraBinding
import com.iua.agustinpereyra.view.base.BaseActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : BaseActivity() {

    // Keep track of asynchronous camera's tasks
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Check for camera permissions and request if needed
        // TODO: Implement

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}