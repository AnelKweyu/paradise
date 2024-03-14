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
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.databinding.ActivitySignUpBinding
import co.ke.kweyu.paradise.firebase.FirestoreClass
import co.ke.kweyu.paradise.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var toolbar: Toolbar
    private lateinit var linkSignInRegister: TextView
    private lateinit var btnSignUp: Button
    private lateinit var titleAuth: TextView
    private lateinit var textAuth: TextView
    private lateinit var nameField: TextInputLayout
    private lateinit var passwordField: TextInputLayout
    private lateinit var googleSignUpBtn: ConstraintLayout
    private lateinit var orGoogleSignUpLine: ConstraintLayout

    private lateinit var pairs: Array<android.util.Pair<View, String>>
    private lateinit var signInIntent:Intent
    private lateinit var options: ActivityOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeActivityFullscreen()

        setupActionBar()

        btnSignUp = binding.btnSignUp
        linkSignInRegister =  binding.linkSignInRegister
        titleAuth = binding.tvTitle
        textAuth = binding.tvSignText
        nameField = binding.emailTextInputLayout
        passwordField = binding.passwordTextInputLayout
        googleSignUpBtn = binding.signUpWithGoogleBtn
        orGoogleSignUpLine = binding.orLine

        pairs = arrayOf(
            android.util.Pair(titleAuth, "auth_title"),
            android.util.Pair(textAuth, "auth_text"),
            android.util.Pair(nameField, "email_name"),
            android.util.Pair(passwordField, "password_password"),
            android.util.Pair(googleSignUpBtn, "google_sign"),
            android.util.Pair(orGoogleSignUpLine, "or_google"),
            android.util.Pair(btnSignUp, "have_don"),
            android.util.Pair(linkSignInRegister, "sign_in_sign_up"),
        )

        btnSignUp.setOnClickListener { registerUser() }
        linkSignInRegister.setOnClickListener {
            signInIntent = Intent(this@SignUpActivity, SignInActivity::class.java)
            options = ActivityOptions.makeSceneTransitionAnimation(this@SignUpActivity, *pairs)
            startActivity(signInIntent, options.toBundle())
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
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            // Registered Email
                            val registeredEmail = firebaseUser.email!!

                            val user = User(
                                firebaseUser.uid, name, registeredEmail
                            )

                            // call the registerUser function of FirestoreClass to make an entry in the database.
                            FirestoreClass().registerUser(this@SignUpActivity, user)
                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
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

    fun userRegisteredSuccess(user: User) {

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