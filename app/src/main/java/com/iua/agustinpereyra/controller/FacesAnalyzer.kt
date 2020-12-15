package com.iua.agustinpereyra.controller

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions


/**
 * Class used to detect faces in streamed images
 */
class FacesAnalyzer : ImageAnalysis.Analyzer {
    private val TAG = FacesAnalyzer::class.java.name

    @androidx.camera.core.ExperimentalGetImage // Required to run
    override fun analyze(imageProxy: ImageProxy) {
        // Real-time fast detection
        val realTimeOps = FaceDetectorOptions.Builder()
            .build()

        // Get the corresponding Face Detector
        val faceDetector = FaceDetection.getClient(realTimeOps)

        // Get the frame
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            // Create Image type supported by ML Kit
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            // Process the image
            val detectionResult = faceDetector.process(image)
                .addOnSuccessListener { faces -> onFacesDetected(faces) }
                .addOnFailureListener { exception -> onFailure(exception) }
                .addOnCompleteListener {
                    // Close the frame
                    imageProxy.close()
                }
        }
    }

    private fun onFacesDetected(faces: List<Face>) {
        // TODO: Implement
        Log.i(TAG, "Detectamos una cara!!!")
    }

    private fun onFailure(exception: Exception) {
        // TODO: Implement
        Log.i(TAG, exception.message?: "Algo fallo, sin mensaje")
        exception.printStackTrace()
        throw exception
    }

}