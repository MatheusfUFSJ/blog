package br.com.sigga.blog.activities

import android.os.Bundle
import br.com.sigga.blog.R
import org.jetbrains.anko.intentFor

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isTaskRoot) {
            setContentView(R.layout.activity_splash)
            startActivity(intentFor<MainActivity>())
        }

        finish()
    }
}
