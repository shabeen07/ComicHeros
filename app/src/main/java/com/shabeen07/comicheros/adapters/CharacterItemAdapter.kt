package com.shabeen07.comicheros.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shabeen07.comicheros.R
import com.shabeen07.comicheros.databinding.CharacterItemRowBinding
import com.shabeen07.comicheros.models.CharacterItem

class CharacterItemAdapter(
    private val context: Context, private var characterList: List<CharacterItem>,
    private val itemClickCallback: ItemClickCallback
) :
    RecyclerView.Adapter<CharacterItemAdapter.MyHolder>() {

    class MyHolder(val characterItemRowBinding: CharacterItemRowBinding) : RecyclerView.ViewHolder(
        characterItemRowBinding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = CharacterItemRowBinding.inflate(layoutInflater, parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val characterItem = characterList[position]
        holder.characterItemRowBinding.tvName.text = characterItem.name

        // load image
        characterItem.image?.let {
            Glide.with(context)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.characterItemRowBinding.ivCharacter)
        }

        holder.characterItemRowBinding.root.setOnClickListener {
            itemClickCallback.onAddItemClick(characterItem)
        }
    }

    // add to cart callback
    interface ItemClickCallback {
        fun onAddItemClick(characterItem: CharacterItem)
    }
}