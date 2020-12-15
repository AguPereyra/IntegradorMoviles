package com.iua.agustinpereyra.controller
import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions


/**
 * Class used to detect faces in streamed images
 */
class FacesAnalyzer(private val resultHandler: FacesAnalyzerResultHandler) : ImageAnalysis.Analyzer {
    private val TAG = FacesAnalyzer::class.java.name

    // Real-time fast detection
    private val realTimeOps = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .build()

    // Get the corresponding Face Detector
    private val faceDetector = FaceDetection.getClient(realTimeOps)

    override fun analyze(imageProxy: ImageProxy) {

        // Get the frame
        @androidx.camera.core.ExperimentalGetImage // Required to run
        val mediaImage = imageProxy.image
        @androidx.camera.core.ExperimentalGetImage // Required to run
        if (mediaImage != null) {
            // Create Image type supported by ML Kit
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            // Process the image
            val detectionResult = faceDetector.process(image)
                .addOnSuccessListener { faces -> resultHandler.onSuccess(faces) }
                .addOnFailureListener { exception -> resultHandler.onFailure(exception) }
                .addOnCompleteListener {
                    // Close the frame
                    imageProxy.close()
                }
        }
    }

    /**
     * interface used to handle errors and success on face detection
     */
    interface FacesAnalyzerResultHandler {
        fun onSuccess(faces: List<Face>)
        fun onFailure(exception: Exception)
    }

}