package com.dicoding.calofruit.ui.story

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.calofruit.R
import com.dicoding.calofruit.databinding.ActivityUploadStoryBinding
import com.dicoding.calofruit.retrofit.PredictionService
import com.dicoding.calofruit.utils.PredictionResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class UploadStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadStoryBinding
    private var currentImageUri: Uri? = null
    private lateinit var tvPredictionResult: TextView

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions: Map<String, Boolean> ->
        val cameraPermissionGranted = permissions[Manifest.permission.CAMERA] ?: false
        val readExternalStoragePermissionGranted =
            permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

        when {
            cameraPermissionGranted -> showToast("Izin akses gallery diberikan")
            readExternalStoragePermissionGranted -> showToast("Izin baca penyimpanan eksternal diberikan")
            !cameraPermissionGranted -> showToast("Izin kamera dibutuhkan")
            !readExternalStoragePermissionGranted -> showToast("Izin baca penyimpanan eksternal dibutuhkan")
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.upload_story)

        // Inisialisasi TextView
        tvPredictionResult = findViewById(R.id.tvPredictionResult)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnUpload.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        launcherGallery.launch(intent)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                currentImageUri = uri
                showImage()
            }
        }
    }


    private fun showImage() {
        currentImageUri?.let {
            binding.imageView.setImageURI(it)
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://predict-rwsk5773ya-et.a.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val predictionService = retrofit.create(PredictionService::class.java)

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val file = File(uri.path ?: "")
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            predictionService.uploadImage(body).enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(
                    call: Call<PredictionResponse>,
                    response: Response<PredictionResponse>
                ) {
                    if (response.isSuccessful) {
                        val prediction = response.body()?.data
                        prediction?.let {
                            val message =
                                "Kelas Diprediksi: ${it.predicted_class}\nKalori Diprediksi: ${it.predicted_calorie}"
                            showPredictionResult(message)
                        }
                    } else {
                        showToast("Gagal mendapatkan prediksi")
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    showToast("Permintaan gagal: ${t.message}") // Pesan kesalahan ditampilkan ke pengguna
                    Log.e("UploadStoryActivity", "Permintaan gagal", t) // Pesan kesalahan dicatat di logcat untuk penelusuran lebih lanjut
                }
            })
        } ?: showToast(getString(R.string.empty_image_warning))
    }


    private fun showPredictionResult(message: String) {
        tvPredictionResult.text = message
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

