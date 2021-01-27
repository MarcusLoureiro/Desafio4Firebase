package com.example.desafiofirebase.ui

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiofirebase.R
import com.example.desafiofirebase.adapter.GameAdapter
import com.example.desafiofirebase.entities.Game
import com.example.desafiofirebase.entities.Repository
import com.example.desafiofirebase.models.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var gameAdapter: GameAdapter
    private var service = Repository()
    

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fb_addGame.setOnClickListener {
            callRegisterGame()
        }

        initRecycler()

        viewModel.getGame()

        viewModel.reslistGames.observe(this){
            initAdapter(it)
        }




    }

    fun callRegisterGame(){
        val intent = Intent(this, GameRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun initRecycler(){
        rvGames.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rvGames.setHasFixedSize(true)
    }

    private fun initAdapter(listGames: ArrayList<Game>){
        gameAdapter = GameAdapter(listGames, View.OnClickListener {
            Log.i("TAG", "Clicked !!")
        })
        rvGames.adapter = gameAdapter
    }


}