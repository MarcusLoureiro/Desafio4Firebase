package com.example.desafiofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btRegister.setOnClickListener {
            getDataFields()
        }
    }

    //pegar os dados digitados nos campos
    fun getDataFields(){
        val email = edEmailRegister.text.toString()
        val password = edPasswordRegister.text.toString()
        val emailEpt = email.isNotBlank()
        val passwordEpt = password.isNotBlank()

        if(emailEpt && passwordEpt)
            sendDataFirebase(email, password)
        else
            showMsg("Preencha todas as informações.")

    }
    // envia dados para o firebase
    fun sendDataFirebase(email: String, password: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    showMsg("Usuário cadastrado com suscesso")
                    val idUser = firebaseUser.uid
                    val emailUser = firebaseUser.email.toString()
                    CallMain(idUser, emailUser)
                } else {
                    showMsg(task.exception?.message.toString())
                }
            }


    }

    //Exibi informações
    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun CallLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun CallMain(idUser: String, emailUser: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id", idUser)
        intent.putExtra("email", emailUser)
        startActivity(intent)
    }

    fun EmptyText(){
        edEmailRegister.text?.clear()
        edPasswordRegister.text?.clear()
    }




}