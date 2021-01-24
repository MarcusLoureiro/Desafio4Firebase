package com.example.desafiofirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiofirebase.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fb_addGame.setOnClickListener {
            callRegisterGame()
        }

    }

    fun callRegisterGame(){
        val intent = Intent(this, GameRegisterActivity::class.java)
        startActivity(intent)
    }
}