package co.ke.kweyu.paradise.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import co.ke.kweyu.paradise.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_intro)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val signInIntroBtn: TextView = findViewById(R.id.link_sign_in_intro)
        val signUpIntroBtn: Button = findViewById(R.id.btn_sign_up_intro)

        signInIntroBtn.setOnClickListener {

            startActivity(Intent(this@IntroActivity, SignInActivity::class.java))
        }
        signUpIntroBtn.setOnClickListener {

            startActivity(Intent(this@IntroActivity, SignUpActivity::class.java))
        }
    }


}