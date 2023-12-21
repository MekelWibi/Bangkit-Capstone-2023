package com.dicoding.calofruit.ui.story

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.calofruit.R
import com.dicoding.calofruit.databinding.ActivityUploadStoryBinding
import com.dicoding.calofruit.response.FruitResponse
import com.dicoding.calofruit.retrofit.ApiService
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
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date


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

    private val predictionService = retrofit.create(ApiService::class.java)

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            try {
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                inputStream?.let {
                    val file = File(cacheDir, "temp_file_name")
                    val outputStream = FileOutputStream(file)
                    inputStream.copyTo(outputStream)
                    outputStream.close()

                    // Verifikasi file sebelum dikirim ke server
                    if (file.exists() && file.length() > 0) {
                        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                        val body = MultipartBody.Part.createFormData("image", file.name+".jpg", requestFile)

                        predictionService.predictImage(body).enqueue(object : Callback<FruitResponse> {
                            override fun onResponse(
                                call: Call<FruitResponse>,
                                response: Response<FruitResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val prediction = response.body()?.data
                                    prediction?.let {
                                        val message =
                                            "Result Prediction :\n\n Kelas Diprediksi: ${it.predictedClass}\nKalori Diprediksi: ${it.predictedCalorie}"
                                        showPredictionResult(message)
                                    }
                                } else {
                                    Log.e("UploadStoryActivity", "Respons gagal: ${response.errorBody()?.string()}")
                                    showToast("Silahkan berikan format file jpg")
                                }
                            }

                            override fun onFailure(call: Call<FruitResponse>, t: Throwable) {
                                showToast("Permintaan gagal: ${t.message}")
                                Log.e("UploadStoryActivity", "Permintaan gagal", t)
                            }
                        })
                    } else {
                        showToast("Gagal memuat file atau file kosong")
                        Log.e("UploadStoryActivity", "Gagal memuat file atau file kosong")
                    }
                } ?: showToast("Gagal membaca file")
            } catch (e: Exception) {
                showToast("Terjadi kesalahan: ${e.message}")
                Log.e("UploadStoryActivity", "Terjadi kesalahan", e)
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }




    private fun showPredictionResult(message: String) {
        tvPredictionResult.text = message
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

