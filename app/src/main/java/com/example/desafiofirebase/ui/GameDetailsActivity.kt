package com.example.desafiofirebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiofirebase.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_details.*

class GameDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        setGame()
    }

    fun setGame(){
        val name = intent.getSerializableExtra("name") as? String
        val data = intent.getSerializableExtra("lancamento") as? String
        val description = intent.getSerializableExtra("descricao") as? String
        val URL = intent.getSerializableExtra("url") as? String
        val key = intent.getSerializableExtra("key") as? String

        Picasso.get().load(URL).into(img_fundo)
        tv_nameGameImgFundoDetails.text = name
        tvNameGameDetails.text = name
        tvDataGame.text = "Lançamento: " + data
        tvDescription.text = description

    }
}