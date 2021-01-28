package com.example.desafiofirebase.ui

import android.content.AbstractThreadedSyncAdapter
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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

class MainActivity : AppCompatActivity(), GameAdapter.OnGameClickListener {
    private lateinit var gameAdapter: GameAdapter
    private var service = Repository()
    var listGame = ArrayList<Game>()
    

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
            listGame = it
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
        gameAdapter = GameAdapter(listGames, this)
        rvGames.adapter = gameAdapter
    }

    override fun gameClick(position: Int) {
        val gameSelect = listGame.get(position)
        creatAlert(gameSelect)
        Log.i("teste de click", "Clickou")
    }

    fun creatAlert(game: Game){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Deseja deletar esse game?")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Sim",{ dialogInterface: DialogInterface, i: Int ->
            viewModel.delGames(game.id)
            updateList()
        })

        alertDialog.setNegativeButton("Não", { dialogInterface: DialogInterface, i: Int ->

        })

        alertDialog.create().show()
    }

    fun updateList(){
        viewModel.getGame()
        viewModel.reslistGames.observe(this){
            initAdapter(it)
        }
    }


}