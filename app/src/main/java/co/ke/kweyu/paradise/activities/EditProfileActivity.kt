package co.ke.kweyu.paradise.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import co.ke.kweyu.paradise.databinding.ActivityEditProfileBinding
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.firebase.FirestoreClass
import co.ke.kweyu.paradise.models.User
import co.ke.kweyu.paradise.utils.Constants
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var toolbar: Toolbar
    private lateinit var editIconOverlay: ImageView
    private lateinit var ivEditUserImage: ImageView

    // Add a global variable for URI of a selected image from phone storage.
    private var paradiseSelectedImageFileUri: Uri? = null

    // A global variable for user details.
    private lateinit var paradiseUserDetails: User

    // A global variable for a user profile image URL
    private var paradiseProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        FirestoreClass().loadUserData(this)

        imageClick()

//        btn_update.setOnClickListener {
//
//            // Here if the image is not selected then update the other details of user.
//            if (paradiseSelectedImageFileUri != null) {
//
//                uploadUserImage()
//            } else {
//
//                showProgressDialog(resources.getString(R.string.please_wait))
//
//                // Call a function to update user details in the database.
//                updateUserProfileData()
//            }
//        }

    }

    private fun setupActionBar() {
        toolbar = binding.toolbarEditProfile.toolbarLayout
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Contact info"
        }

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun imageClick(){
        editIconOverlay = binding.editIconOverlay
        editIconOverlay.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this)
            } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ivEditUserImage = binding.ivProfileUserImageEdit
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            // The uri of selection image from phone storage.
            paradiseSelectedImageFileUri = data.data!!

            try {
                // Load the user image in the ImageView.
                Glide
                    .with(this)
                    .load(Uri.parse(paradiseSelectedImageFileUri.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.ic_user_place_holder) // A default place holder
                    .into(ivEditUserImage) // the view in which the image will be loaded.
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * This function will identify the result of runtime permission after the user allows or deny permission based on the unique code.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * A function to set the existing details in UI.
     */
    fun setUserDataInUI(user: User) {

        // Initialize the user details variable
        paradiseUserDetails = user
        ivEditUserImage = binding.ivProfileUserImageEdit

        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(ivEditUserImage)

        binding.etEditName.setText(user.name)
        binding.etEditBirthdate.setText(user.dob)
//        binding.etEditGender.setText(user.gender.toString())
        binding.etEditEmail.setText(user.email)
        if (user.mobile != 0L) {
            binding.etEditPhone.setText(user.mobile.toString())
        }
        binding.etEditAddress.setText(user.address)

    }


    /**
     * A function to upload the selected user image to firebase cloud storage.
     */
    private fun uploadUserImage() {

        showProgressDialog(resources.getString(R.string.please_wait))

        if (paradiseSelectedImageFileUri != null) {

            //getting the storage reference
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "USER_IMAGE" + System.currentTimeMillis() + "."
                        + Constants.getFileExtension(this, paradiseSelectedImageFileUri)
            )

            //adding the file to reference
            sRef.putFile(paradiseSelectedImageFileUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    // The image upload is success
                    Log.e(
                        "Firebase Image URL",
                        taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    )

                    // Get the downloadable url from the task snapshot
                    taskSnapshot.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { uri ->
                            Log.e("Downloadable Image URL", uri.toString())

                            // assign the image url to the variable.
                            paradiseProfileImageURL = uri.toString()

                            // Call a function to update user details in the database.
                            updateUserProfileData()
                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        exception.message,
                        Toast.LENGTH_LONG
                    ).show()

                    hideProgressDialog()
                }
        }
    }

    /**
     * A function to update the user profile details into the database.
     */
    private fun updateUserProfileData() {
        val userHashMap = HashMap<String, Any>()

        if (paradiseProfileImageURL.isNotEmpty() && paradiseProfileImageURL != paradiseUserDetails.image) {
            userHashMap[Constants.IMAGE] = paradiseProfileImageURL
        }

        if (binding.etEditName.text.toString() != paradiseUserDetails.name) {
            userHashMap[Constants.NAME] = binding.etEditName.text.toString()
        }

        if (binding.etEditBirthdate.text.toString() != paradiseUserDetails.dob) {
            userHashMap[Constants.DOB] = binding.etEditBirthdate.text.toString()
        }

//        if (binding.etEditGender.text.toString() != paradiseUserDetails.gender.toString()) {
//            userHashMap[Constants.GENDER] = binding.etEditGender.text.toString()
//        }

        if (binding.etEditEmail.text.toString() != paradiseUserDetails.email) {
            userHashMap[Constants.EMAIL] = binding.etEditEmail.text.toString()
        }

        if (binding.etEditPhone.text.toString() != paradiseUserDetails.mobile.toString()) {
            userHashMap[Constants.MOBILE] = binding.etEditPhone.text.toString()
        }

        if (binding.etEditAddress.text.toString() != paradiseUserDetails.address) {
            userHashMap[Constants.ADDRESS] = binding.etEditAddress.text.toString()
        }
        // Update the data in the database.
        FirestoreClass().updateUserProfileData(this, userHashMap)
    }

    /**
     * A function to notify the user profile is updated successfully.
     */
    fun profileUpdateSuccess() {

        hideProgressDialog()

        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

        setResult(Activity.RESULT_OK)
        finish()
    }


}
