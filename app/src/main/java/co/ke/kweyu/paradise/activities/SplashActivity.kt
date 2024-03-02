package co.ke.kweyu.paradise.activities

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

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeActivityFullscreen()

        imageView = binding.splashImage
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        imageView.startAnimation(topAnim)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
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
