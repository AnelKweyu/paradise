package co.ke.kweyu.paradise.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.databinding.ActivitySignInBinding
import co.ke.kweyu.paradise.firebase.FirestoreClass
import co.ke.kweyu.paradise.models.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var toolbar: Toolbar
    private lateinit var linkSignUpLogin: TextView
    private lateinit var titleAuth: TextView
    private lateinit var textAuth: TextView
    private lateinit var emailField: TextInputLayout
    private lateinit var etEmail: AppCompatEditText
    private lateinit var passwordField: TextInputLayout
    private lateinit var etPassword: AppCompatEditText
    private lateinit var btnSignIn: Button
    private lateinit var googleSignInBtn: ConstraintLayout
    private lateinit var orGoogleSignInLine: ConstraintLayout

    private lateinit var pairs: Array<android.util.Pair<View, String>>
    private lateinit var signUpIntent:Intent
    private lateinit var options: ActivityOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeActivityFullscreen()

        setupActionBar()

        linkSignUpLogin = binding.linkSignUpLogin
        btnSignIn = binding.btnSignIn
        titleAuth = binding.tvTitle
        textAuth = binding.tvSignText
        emailField = binding.emailTextInputLayout
        passwordField = binding.passwordTextInputLayout
        googleSignInBtn = binding.signInWithGoogleBtn
        orGoogleSignInLine = binding.orLine

        pairs = arrayOf(
            android.util.Pair(titleAuth, "auth_title"),
            android.util.Pair(textAuth, "auth_text"),
            android.util.Pair(emailField, "email_name"),
            android.util.Pair(passwordField, "password_password"),
            android.util.Pair(googleSignInBtn, "google_sign"),
            android.util.Pair(orGoogleSignInLine, "or_google"),
            android.util.Pair(btnSignIn, "have_don"),
            android.util.Pair(linkSignUpLogin, "sign_in_sign_up")
        )

        btnSignIn.setOnClickListener {
            signInRegisteredUser()
        }

        linkSignUpLogin.setOnClickListener {

            signUpIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
            options = ActivityOptions.makeSceneTransitionAnimation(this@SignInActivity, *pairs)
            startActivity(signUpIntent, options.toBundle())
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
        etEmail = binding.etEmail
        etPassword = binding.etPassword
        val email: String = etEmail.text.toString().trim { it <= ' ' }
        val password: String = etPassword.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Sign-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Calling the FirestoreClass signInUser function to get the data of user from database.
                        FirestoreClass().loadUserData(this@SignInActivity)

                    } else {

                        Toast.makeText(
                            this@SignInActivity,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                        hideProgressDialog()

                    }
                }
        }

    }


    private fun validateForm(email: String, password: String): Boolean {
        etEmail = binding.etEmail
        etPassword = binding.etPassword
        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                emailField.error = "Please enter email."
                false
            }
            !isValidEmail(email) -> {
                showErrorSnackBar("Please enter valid email.")
                emailField.error = "Please enter valid email."
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                passwordField.error = "Please enter password."
                false
            }
            !isValidPassword(password) -> {
                showErrorSnackBar("At least 8 characters with at least one uppercase letter, one lowercase letter, and one digit.")
                false
            }
            else -> true
        }
    }

    fun signInSuccess(user: User) {

        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        hideProgressDialog()
        Toast.makeText(
            this@SignInActivity,
            "You have successfully Signed In.",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}
