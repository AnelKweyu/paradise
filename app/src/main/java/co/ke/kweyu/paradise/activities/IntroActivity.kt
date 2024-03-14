package co.ke.kweyu.paradise.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.TextView
import co.ke.kweyu.paradise.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var signInIntroBtn: TextView
    private lateinit var signUpIntroBtn: Button
    private lateinit var introTitle: TextView
    private lateinit var introText: TextView


    private lateinit var pairs: Array<android.util.Pair<View, String>>

    private lateinit var signInIntent:Intent
    private lateinit var signUpIntent:Intent
    private lateinit var options: ActivityOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeActivityFullscreen()


        signInIntroBtn = binding.linkSignInIntro
        signUpIntroBtn = binding.btnSignUpIntro
        introTitle = binding.introTitleTv
        introText = binding.introTv

        pairs = arrayOf(
            android.util.Pair(introTitle, "auth_title"),
            android.util.Pair(introText, "auth_text"),
            android.util.Pair(signInIntroBtn, "have_don"),
            android.util.Pair(signUpIntroBtn, "sign_in_sign_up"),
        )
        signInIntroBtn.setOnClickListener {

            signInIntent = Intent(this@IntroActivity, SignInActivity::class.java)

            options = ActivityOptions.makeSceneTransitionAnimation(this@IntroActivity, *pairs)
            startActivity(signInIntent, options.toBundle())
        }

        signUpIntroBtn.setOnClickListener {
            signUpIntent = Intent(this@IntroActivity, SignUpActivity::class.java)
            options = ActivityOptions.makeSceneTransitionAnimation(this@IntroActivity, *pairs)
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
}
