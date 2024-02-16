package co.ke.kweyu.paradise.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.ke.kweyu.paradise.R
import com.google.android.material.snackbar.Snackbar
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
        // Create the dialog instance
        mProgressDialog = Dialog(this)

        // Set the content view of the dialog
        mProgressDialog.setContentView(R.layout.dialog_progress)

        // Find the TextView inside the dialog layout
        val tvProgressText: TextView = mProgressDialog.findViewById(R.id.tv_progress_text)

        // Set the text to the TextView
        tvProgressText.text = text

        // Show the dialog
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

//    fun getCurrentUserID(): String {
//        return FirebaseAuth.getInstance().currentUser!!.uid
//    }

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

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
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

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$"
        return password.matches(passwordRegex.toRegex())
    }

    fun validateInput(value: String): Boolean {
        val re = Regex("[A-Za-z]{3,30}")
        val arr = value.split(' ')
        if (arr.size != 2) return false
        val (firstName, lastName) = arr
        return re.matches(firstName) && re.matches(lastName)
    }

}