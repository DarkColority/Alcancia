package com.example.alcancia.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.alcancia.extras.Network
import com.example.alcancia.R

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val intent = Intent(this, MainActivity::class.java)
        val handler = Handler()
        handler.postDelayed({
            if (Network.netCheck(this@Splash)) {
                Toast.makeText(applicationContext, "You're connected, Welcome back!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Please check your internet connection", Toast.LENGTH_LONG).show()
            }
        }, 1000)

    }
}
