package com.example.hobbyappgame.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbyappgame.databinding.GameListNewsBinding
import com.example.hobbyappgame.model.Game
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class GameListAdapter(val gameList:ArrayList<Game>):RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {
    class GameViewHolder(var binding:GameListNewsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        with(holder.binding) {

            val picasso = Picasso.Builder(holder.itemView.context)
            picasso.listener { picasso, uri, exception ->
                exception.printStackTrace()
            }
            picasso.build().load(gameList[position].photourl).into(holder.binding.imageGame, object:
                Callback {
                override fun onSuccess() {
//                    holder.binding.progressImage.visibility= View.INVISIBLE
                    holder.binding.imageGame.visibility= View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error",e.toString())
                }

            })
            txtMaker.text = gameList[position].name
            txtTitle.text = gameList[position].titleName
            txtDeskripsi.text=gameList[position].deskripsi

            btnRead.setOnClickListener{
                val action = GameListFragmentDirections.actionitemHomegameDetailFragment(gameList[position].id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    fun updateGameList(newGameList:ArrayList<Game>) {
        gameList.clear()
        gameList.addAll(newGameList)
//        notifyDataSetChanged()
    }
}