package co.ke.kweyu.paradise.activities

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.databinding.DialogProgressBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    /**
     * This is a progress dialog instance which we will initialize later on.
     */
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * This function is used to show the progress dialog with the title and message to user.
     */

    fun showProgressDialog(text: String) {
        val binding = DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog = Dialog(this)

        mProgressDialog.setContentView(binding.root)

        binding.tvProgressText.text = text

        mProgressDialog.show()
    }


    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        Handler(mainLooper).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@BaseActivity,
                R.color.snackbar_error_color
            )
        )
        snackBar.show()
    }

//    fun showErrorSnackBar(message: String) {
//        val binding = ActivityBaseBinding.inflate(layoutInflater)
//
//        val snackBar =
//            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
//        val snackBarView = snackBar.view
//
//        snackBarView.setBackgroundColor(
//            ContextCompat.getColor(
//                this@BaseActivity,
//                R.color.snackbar_error_color
//            )
//        )
//
//        snackBar.show()
//    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$"
        return password.matches(passwordRegex.toRegex())
    }

    fun validateFullInput(value: String): Boolean {
        val re = Regex("[A-Za-z]{2,30}")
        val arr = value.split(' ')
        if (arr.size < 2) return false
        val (firstName, lastName) = arr
        return re.matches(firstName) && re.matches(lastName)
    }
     fun hideSoftKeyboard() {
        currentFocus?.let { focus ->
            if (focus is EditText) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focus.windowToken, 0)
            }
        }
    }

}