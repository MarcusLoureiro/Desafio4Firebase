package com.example.desafiofirebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.R
import com.example.desafiofirebase.entities.Game
import com.example.desafiofirebase.entities.Repository
import com.example.desafiofirebase.models.MainViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_game_register.*
import java.util.*

class GameRegisterActivity : AppCompatActivity() {
    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    var URL: String = ""
    private var ImageSet = false
    private val service = Repository()
    private val CODE_IMG = 1000


    private val viewModelGame by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_register)
        btRegisterGame.setOnClickListener {
            var id = getUniqueId()
            if(ImageSet == true){
                viewModelGame.sendGame(Game(id, edNameGame.text.toString(), edDate.text.toString(), edDescriptionGame.text.toString(), URL))
                callHome()
                Log.i("Teste", id)
            }
            val edit = intent.getSerializableExtra("edit") as? Boolean
            val key = intent.getSerializableExtra("key") as? String
            val UrlKey = intent.getSerializableExtra("url") as? String

            Log.i("edit", edit.toString())
            if(edit == true){
                viewModelGame.UpdGames(Game(key.toString(), edNameGame.text.toString(), edDate.text.toString(), edDescriptionGame.text.toString(), UrlKey.toString()))
                callHome()
                Log.i("Teste",key.toString())
            }
        }
        iv_game.setOnClickListener {
            getRec()
        }
        config()
        editGame()
    }


    fun config() {
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        storageReference = viewModelGame.Service.storageReference

    }

    fun getRec() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura Imagem"), CODE_IMG)
    }

    fun getUniqueId() = service.db.collection(service.coll).document().id


    fun callHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun editGame(){
        val name = intent.getSerializableExtra("name") as? String
        val data = intent.getSerializableExtra("lancamento") as? String
        val description = intent.getSerializableExtra("descricao") as? String
        val URL = intent.getSerializableExtra("url") as? String
        val key = intent.getSerializableExtra("key") as? String
        val edit = intent.getSerializableExtra("edit") as? Boolean
        if(edit == true){
            val game = Game(key!!,name!!,data!!,description!!,URL!!)
            setGameInEd(game)
        }
    }

    fun setGameInEd(game: Game){
        edNameGame.setText(game.name)
        edDate.setText(game.data)
        edDescriptionGame.setText(game.description)
        Picasso.get().load(game.URL).into(iv_game)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {
            alertDialog.show()
            val uploadFile = storageReference.putFile(data!!.data!!)
            val task = uploadFile.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Imagem Carrregada com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
                storageReference!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    URL = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    Log.i("URL da Imagem", URL)
                    alertDialog.dismiss()
                    Picasso.get().load(URL).into(iv_game)
                    ImageSet = true

                }
            }
        }
    }
}