package com.example.desafiofirebase.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiofirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            CallMain(user.uid, user.email.toString())
        }

        btLogin.setOnClickListener {
            getDataFields()
        }

        tvRegister.setOnClickListener {
            CallRegister()
        }

    }

    fun getDataFields(){
        val email = edEmail.text.toString()
        val password = edPassword.text.toString()
        val emailEpt = email.isNotBlank()
        val passwordEpt = password.isNotBlank()

        if(emailEpt && passwordEpt)
            sendDataFirebase(email, password)
        else
            showMsg("Preencha todas as informações.")

    }

    fun sendDataFirebase(email: String, password: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    val idUser = firebaseUser.uid
                    val emailUser = firebaseUser.email.toString()
                    CallMain(idUser, emailUser)
                } else {
                    showMsg(task.exception?.message.toString())
                }
            }


    }
    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
    fun CallMain(idUser: String, emailUser: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id", idUser)
        intent.putExtra("email", emailUser)
        startActivity(intent)
    }

    // Chama a activity de cadastro
    fun CallRegister(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}