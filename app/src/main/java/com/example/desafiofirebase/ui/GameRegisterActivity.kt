package com.example.desafiofirebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
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
    lateinit var URL: String
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
            if(ImageSet == true){
                viewModelGame.sendGame(Game(edNameGame.text.toString(), edDate.text.toString(), edDescriptionGame.text.toString(), URL))
                callHome()
            }else{
                Toast.makeText(this, "Insira uma Imagem Primeiro", Toast.LENGTH_SHORT).show()
            }
        }
        iv_game.setOnClickListener {
            getRec()
        }
        config()
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

    fun callHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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