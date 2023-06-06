package com.tcoding.moviehouse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tcoding.moviehouse.R
import com.tcoding.moviehouse.models.Result

class MovieAdapter(private val isFirstScreen : Boolean = true): RecyclerView.Adapter<MovieAdapter.MyCustomHolder>() {

    var liveData : List<Result>? = null

    fun setList(liveData : List<Result>) {
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MyCustomHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        val txtGenre = itemView.findViewById<TextView>(R.id.txtGenre)
        val posterView = itemView.findViewById<ImageView>(R.id.posterView)

        fun bindData(data : Result) {
            txtTitle.text = data.title
            txtGenre.text = "Deneme Deneme Deneme"
            Glide.with(posterView)
                .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                .into(posterView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bindData(liveData!!.get(position))
    }

    override fun getItemCount(): Int {
        if(liveData == null) {
            return 0
        }else if(isFirstScreen){
            return 4
        }else
            return liveData!!.size


    }

}