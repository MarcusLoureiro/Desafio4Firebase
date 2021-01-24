//package com.example.desafiofirebase.ui
//
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.*
//
//
//class RealtimeActivity : AppCompatActivity() {
//    private lateinit var database: FirebaseDatabase
//    private lateinit var reference: DatabaseReference
//
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        bind = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(bind.root)
//        conectDataBase()
//        getData()
//
//        bind.btnCreate.setOnClickListener {
//            var prod = getProd("Carro")
//            saveProduct(prod)
//
//        }
//
//        bind.btnUpdate.setOnClickListener {
//            var prod = getProd("Bicicleta")
//            updateProduct(prod)
//
//        }
//
//        bind.btnDelete.setOnClickListener {
//            deleteProduct("2")
//
//        }
//    }
//
//    fun getProd(name: String): Product{
//        var cat = Category(1, "office")
//        return Product(name, 1, 2.0, cat)
//    }
//
//    //Realiza a conexão com o banco do firebase
//    fun conectDataBase(){
//        database = FirebaseDatabase.getInstance()
//        reference = database.getReference("products")
//    }
//
//    fun saveProduct(product: Product){
//        var ref1 = reference
//            .child("1")
//            .setValue(product)
//
//        var ref2 = reference
//            .child("2")
//            .setValue(product)
//        Log.i("Keys", "key1: $ref1  key2: $ref2")
//
//
//    }
//
//    fun updateProduct(product: Product){
//        var ref1 = reference
//            .child("1")
//            .setValue(product)
//
//    }
//
//    fun deleteProduct(id: String){
//        var ref1 = reference
//            .child(id)
//            .removeValue()
//
//    }
//
//    fun getData(){
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//               dataSnapshot.children.forEach{
//                   Log.i("res", it.toString())
//               }
//            }
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }
//
//    fun showMsg(msg: String){
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
//    }
//}