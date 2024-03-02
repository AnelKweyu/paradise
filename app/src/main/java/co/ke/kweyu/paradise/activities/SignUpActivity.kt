package co.ke.kweyu.paradise.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var toolbar: Toolbar
    private lateinit var linkSignInRegister: TextView
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeActivityFullscreen()



        setupActionBar()

        btnSignUp = binding.btnSignUp
        linkSignInRegister =  binding.linkSignInRegister
        btnSignUp.setOnClickListener { registerUser() }
        linkSignInRegister.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
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
        toolbar = binding.toolbarSignUpActivity.toolbarLayout
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = ""
        }

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }


    private fun registerUser() {
        val name: String = binding.etName.text.toString().trim { it <= ' ' }
        val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

        hideSoftKeyboard()

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