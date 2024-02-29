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

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        val signUpBtn: Button = findViewById(R.id.btn_sign_up)
        val signInLink: TextView = findViewById(R.id.link_sign_in_register)

        signUpBtn.setOnClickListener {registerUser()}
        signInLink.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        }
    }

    private fun setupActionBar() {
        val toolbarSignUpActivity: Toolbar = findViewById(R.id.toolbar_sign_up_activity)


        setSupportActionBar(toolbarSignUpActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.setTitle("")
        }

        toolbarSignUpActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser() {
        val et_name: TextView = findViewById(R.id.et_name)
        val et_email: TextView = findViewById(R.id.et_email)
        val et_password: TextView = findViewById(R.id.et_password)

        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        hideSoftKeyboard();

        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            userRegisteredSuccess()
        }
    }


    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter Full name.")
                false
            }
            !validateFullInput(name) -> {
                showErrorSnackBar("Please enter at least 2 names")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            !isValidEmail(email) -> {
                showErrorSnackBar("Please enter valid email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            !isValidPassword(password) -> {
                showErrorSnackBar("Password should have  at least 8 characters with at least one uppercase letter, one lowercase letter, and one digit")
                false
            }
            else -> {
                true
            }
        }
    }

    private fun userRegisteredSuccess() {

        Toast.makeText(
            this@SignUpActivity,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()
        hideProgressDialog()
        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
        finish()
    }
}