package com.example.desafiofirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.R
import com.example.desafiofirebase.entities.Repository
import com.example.desafiofirebase.models.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_details.*

class GameDetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        fb_editGame.setOnClickListener {
            sendGame()
        }

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

    fun sendGame(){
        val name = intent.getSerializableExtra("name") as? String
        val data = intent.getSerializableExtra("lancamento") as? String
        val description = intent.getSerializableExtra("descricao") as? String
        val URL = intent.getSerializableExtra("url") as? String
        val key = intent.getSerializableExtra("key") as? String


        val intent = Intent(this, GameRegisterActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("lancamento", data)
        intent.putExtra("descricao", description)
        intent.putExtra("url", URL)
        intent.putExtra("key", key)
        intent.putExtra("edit", true)
        startActivity(intent)
    }

}