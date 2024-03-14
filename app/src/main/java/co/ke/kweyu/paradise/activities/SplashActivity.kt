package co.ke.kweyu.paradise.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.databinding.ActivitySplashBinding
import co.ke.kweyu.paradise.firebase.FirestoreClass

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var imageView: ImageView
    private lateinit var introIntent:Intent
    private lateinit var mainIntent:Intent
    private lateinit var pair: android.util.Pair<View, String>
    private lateinit var options: ActivityOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeActivityFullscreen()

        imageView = binding.splashImage
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        imageView.startAnimation(topAnim)

        Handler(mainLooper).postDelayed({

            val currentUserID = FirestoreClass().getCurrentUserID()
            // Start the Intro Activity

            if (currentUserID.isNotEmpty()) {
                // Start the Main Activity
                mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
            } else {
                // Start the Intro Activity
                introIntent = Intent(this@SplashActivity, IntroActivity::class.java)
                pair = android.util.Pair.create(imageView, "logo_image")

                options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity, pair)
                startActivity(introIntent, options.toBundle())
            }
            finish()
        }, 5000)


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
