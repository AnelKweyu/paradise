package co.ke.kweyu.paradise.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.models.User

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_sign_in)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        val signInBtn: Button = findViewById(R.id.btn_sign_in)
        val signUpIntroLink: TextView = findViewById(R.id.link_sign_up_login)

        signInBtn.setOnClickListener {
            signInRegisteredUser()
        }
        signUpIntroLink.setOnClickListener {

            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }

    private fun setupActionBar() {
        val toolbarSignInActivity: Toolbar = findViewById(R.id.toolbar_sign_in_activity)

        setSupportActionBar(toolbarSignInActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.setTitle("")
        }

        toolbarSignInActivity.setNavigationOnClickListener { onBackPressed() }
    }
    private fun signInRegisteredUser() {

        val et_email: TextView = findViewById(R.id.et_email)
        val et_password: TextView = findViewById(R.id.et_password)


        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        hideSoftKeyboard()

        if (validateForm(email, password)) {
            val loggedInUser = User(email)
            showProgressDialog(resources.getString(R.string.please_wait))
            signInSuccess(loggedInUser)
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (!isValidEmail(email)){
            showErrorSnackBar("Please enter valid email.")
            false
        }else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        }else if (!isValidPassword(password)) {
            showErrorSnackBar(" At least 8 characters with at least one uppercase letter, one lowercase letter, and one digit")
            false
        } else {
            true
        }
    }


    fun signInSuccess(user: User) {
        Toast.makeText(
            this@SignInActivity,
            "You have successfully Signed In.",
            Toast.LENGTH_SHORT
        ).show()

        hideProgressDialog()

        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        this.finish()
    }
}