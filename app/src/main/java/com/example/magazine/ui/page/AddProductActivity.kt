package com.example.magazine.ui.page

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.example.magazine.R
import com.example.magazine.interfaces.BaseView
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddProductActivity : AppCompatActivity(), BaseView {
    private lateinit var productPicture: ImageView
    private lateinit var productTitle: EditText
    private lateinit var productPrice: EditText
    private lateinit var productDescription: EditText
    private lateinit var buttonAddProduct: Button
    private lateinit var category: Spinner
    private lateinit var downloadImageUri: String
    private lateinit var productRandomKey: String
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var storageImageRef: StorageReference
    private lateinit var progressBarAddProduct: ProgressBar

    private var imageURI: Uri? = null
    private val GALLERY_RESULT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        bindViews()

        productPicture.setOnClickListener {
            openGallery()
        }

        buttonAddProduct.setOnClickListener {
            validateProductData()
        }
    }

    private fun validateProductData() {
        when {
            imageURI == null -> {
                Toast.makeText(this, "Add product Picture", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(productTitle.text.toString()) -> {
                editTextIsEmpty(productTitle)
            }
            TextUtils.isEmpty(productDescription.text.toString()) -> {
                editTextIsEmpty(productDescription)
            }
            TextUtils.isEmpty(productPrice.text.toString()) -> {
                editTextIsEmpty(productPrice)
            }
            category.selectedItemPosition == 0 -> Toast.makeText(this, "Select category", Toast.LENGTH_SHORT).show()
            else -> storageProductInformation()
        }
    }

    private fun editTextIsEmpty(editText: EditText){
        editText.error = "The field must be filled"
    }

    @SuppressLint("SimpleDateFormat")
    private fun storageProductInformation() {
        progressBarAddProduct.visibility = ProgressBar.VISIBLE

        try {
            productRandomKey = Random().nextInt().toString()

            val filePath: StorageReference = storageImageRef.child(imageURI!!.lastPathSegment + productRandomKey + ".webm")

            val uploadTask: UploadTask = filePath.putFile(imageURI!!)

            uploadTask.addOnFailureListener(OnFailureListener {
                Log.d("upload_in_storage_error", it.stackTraceToString())
            }).addOnSuccessListener {
                Log.d("successful", "Successful")

                val uriTask = uploadTask.continueWithTask(Continuation {
                    return@Continuation filePath.downloadUrl
                }).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("successful", "Successful")

                        downloadImageUri = it.result.toString()

                        saveProductInfoInDatabase()
                    }
                }
            }
        }catch (e: Exception){
            Log.d("download", e.stackTraceToString())
        }
    }

    private fun saveProductInfoInDatabase() {
        val productMap: HashMap<String, Any> = HashMap()

        productMap["pid"] = productRandomKey
        productMap["category"] = category.selectedItem.toString()
        productMap["productPicture"] = downloadImageUri
        productMap["productTitle"] = productTitle.text.toString()
        productMap["productPrice"] = productPrice.text.toString()
        productMap["productDescription"] = productDescription.text.toString()

        try {
            firebaseReference.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, R.string.successful, Toast.LENGTH_SHORT).show()

                        progressBarAddProduct.visibility = ProgressBar.INVISIBLE

                        finish()
                    }else{
                        Toast.makeText(this, "Беды", Toast.LENGTH_SHORT).show()
                    }
                }
        }catch (e:java.lang.Exception){
            Log.d("download_in_data_error", e.stackTraceToString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GALLERY_RESULT && resultCode == RESULT_OK && data != null){
            imageURI = data.data!!
            productPicture.setImageURI(imageURI)
        }
    }

    private fun openGallery() {
       val openGalleryIntent = Intent()
        openGalleryIntent.action = Intent.ACTION_GET_CONTENT
        openGalleryIntent.type = "image/*"
        startActivityForResult(openGalleryIntent, GALLERY_RESULT)
    }

    override fun bindViews() {
        productPicture = findViewById(R.id.productPicture)
        productDescription = findViewById(R.id.productDescription)
        productTitle = findViewById(R.id.productTitle)
        productPrice = findViewById(R.id.productPrice)
        category = findViewById(R.id.categorySpinner)
        buttonAddProduct = findViewById(R.id.buttonAddProduct)
        progressBarAddProduct = findViewById(R.id.progressBarAddProduct)

        storageImageRef = FirebaseStorage.getInstance().reference.child("ProductsImage")
        firebaseReference = FirebaseDatabase.getInstance().reference.child("Products")
    }

    override fun getContext(): Context {
        return this
    }
}