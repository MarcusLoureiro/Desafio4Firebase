package com.example.desafiofirebase.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.entities.Game
import com.example.desafiofirebase.ui.GameDetailsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_register.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.card_home.view.*

class GameAdapter(
    private val listGames: ArrayList<Game>,
    private val clickListener: View.OnClickListener
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.tvNome.text = listGames[position].name
        holder.tvLancamento.text = listGames[position].data
        Picasso.get().load(listGames[position].URL).into(holder.ivGame)
        holder.ivGame.setOnClickListener {
            val intent = Intent(holder.itemView.context, GameDetailsActivity::class.java)
            intent.putExtra("name", listGames[position].name)
            intent.putExtra("lancamento", listGames[position].data)
            intent.putExtra("descricao", listGames[position].description)
            intent.putExtra("url", listGames[position].URL)
            intent.putExtra("key", listGames[position].id)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount() = listGames.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome = view.tv_Nome!!
        val tvLancamento = view.tv_lancamento!!
        val ivGame = view.img_game!!
    }
}