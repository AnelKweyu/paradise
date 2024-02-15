package co.ke.kweyu.paradise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val image: ImageView = findViewById(R.id.imageView)
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        image.startAnimation(topAnim)

        Handler().postDelayed({
            startActivity(Intent(this@MainActivity, IntroActivity::class.java))
            finish()
        }, 5000)
    }
}