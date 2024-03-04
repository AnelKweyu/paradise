package co.ke.kweyu.paradise.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.databinding.ActivitySignInBinding
import co.ke.kweyu.paradise.models.User

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var toolbar: Toolbar
    private lateinit var linkSignUpLogin: TextView
    private lateinit var btnSignIn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeActivityFullscreen()

        setupActionBar()

        linkSignUpLogin = binding.linkSignUpLogin
        btnSignIn = binding.btnSignIn
        btnSignIn.setOnClickListener {
            signInRegisteredUser()
        }

        linkSignUpLogin.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }

    @Suppress("DEPRECATION")
    private fun makeActivityFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
        }
    }
    private fun setupActionBar() {
        toolbar = binding.toolbarSignInActivity.toolbarLayout
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = ""
        }

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun signInRegisteredUser() {
        val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

        hideSoftKeyboard()

        if (validateForm(email, password)) {
            val loggedInUser = User(email)
            showProgressDialog(resources.getString(R.string.please_wait))
            signInSuccess(loggedInUser)
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
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
                showErrorSnackBar("At least 8 characters with at least one uppercase letter, one lowercase letter, and one digit.")
                false
            }
            else -> true
        }
    }

    private fun signInSuccess(user: User) {
        Toast.makeText(
            this@SignInActivity,
            "You have successfully Signed In.",
            Toast.LENGTH_SHORT
        ).show()

        hideProgressDialog()

        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finish()
    }
}
