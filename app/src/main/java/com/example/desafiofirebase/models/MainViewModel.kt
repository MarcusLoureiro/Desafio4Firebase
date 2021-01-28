package com.example.desafiofirebase.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiofirebase.entities.Game
import com.example.desafiofirebase.entities.Repository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel(val Service: Repository):ViewModel() {
    val reslistGames = MutableLiveData<ArrayList<Game>>()
    val resSend = MutableLiveData<Boolean>()
    val cr = Service.cr

    fun setValueLiveDataGame(listGame: ArrayList<Game>){
        reslistGames.value = listGame
    }

//     fun defaultListGame() = arrayListOf(
//            Game("Star Wars Jedi Fallen Order","2019", "Erga a nova Ordem Jedi", ),
//        )
     fun sendGame(game: Game){
         cr.document(game.id).set(game).addOnSuccessListener {
             resSend.value = true
         }.addOnCanceledListener {
             resSend.value = false
         }
     }

    fun getGame(){
        cr.get().addOnSuccessListener { documents ->
            var listGames = arrayListOf<Game>()
            for (document in documents){
                val game: Game = document.toObject(Game::class.java)
                listGames.add(game)
                Log.i("Store", document.toString())
            }
            reslistGames.value = listGames
        }
    }

    fun delGames(idGame: String){
        cr.document(idGame).delete().addOnSuccessListener {

        }.addOnCanceledListener {

        }
    }

    fun UpdGames(game: Game){
        cr.document(game.id).set(game).addOnSuccessListener {
            resSend.value = true
        }.addOnCanceledListener {
            resSend.value = false

        }
    }

}