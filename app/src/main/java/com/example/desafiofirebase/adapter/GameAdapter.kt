package com.example.desafiofirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.entities.Game

class GameAdapter(private var listaGame: ArrayList<Game>, val listener: onGamelickListener): RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): GameViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false)
        return GameViewHolder(itemView)

    }

    override fun getItemCount() = listaGame.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        var game = listaGame.get(position)

        holder.tvNomeGame.text = game.name
        //holder.imgGame.setImageResource(game.URL)



    }
    interface onGamelickListener{
        fun gameClick(position: Int)
    }


    inner class GameViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvNomeGame: TextView = itemView.findViewById(R.id.tv_Nome)
        val imgGame: ImageView = itemView.findViewById(R.id.tv_lancamento)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position){
                listener.gameClick(position)
            }
        }
    }


}


