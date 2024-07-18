package com.shabeen07.comicheros.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shabeen07.comicheros.databinding.EpisodeRowBinding

class EpisodeAdapter(
    private val context: Context, private var episodeList: List<String?>,
) :
    RecyclerView.Adapter<EpisodeAdapter.MyHolder>() {

    companion object{
        private const val TAG = "EpisodeAdapter"
    }

    class MyHolder(val episodeRowBinding: EpisodeRowBinding) : RecyclerView.ViewHolder(
        episodeRowBinding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = EpisodeRowBinding.inflate(layoutInflater, parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val episode = episodeList[position]
        // set episode
        episode?.let {
            val number = "${position+1}."
            holder.episodeRowBinding.tvEpisodeNo.text = number
            holder.episodeRowBinding.tvEpisode.text = it
        }
        holder.episodeRowBinding.root.setOnClickListener{
            Log.d(TAG, "Episode Clicked: ")
        }
    }
}