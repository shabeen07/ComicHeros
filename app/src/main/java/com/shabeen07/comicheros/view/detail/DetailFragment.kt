package com.shabeen07.comicheros.view.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shabeen07.comicheros.R
import com.shabeen07.comicheros.adapters.EpisodeAdapter
import com.shabeen07.comicheros.api.Resource
import com.shabeen07.comicheros.databinding.FragmentDetailBinding
import com.shabeen07.comicheros.models.CharacterItem

class DetailFragment : Fragment() {

    companion object {
        private const val TAG = "DetailFragment"
    }
    private lateinit var fragmentDetailBinding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        // get character by id
        val characterId = arguments?.getInt("characterId") ?: 0
        viewModel.getCharacter(characterId)
        // Inflate the layout for this fragment
        return fragmentDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This callback will only be called when MyFragment is at least Started.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            findNavController().navigateUp()
        }

        viewModel.character.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Error -> {
                    Log.d(TAG, "Error: ${it.message}")
                    viewProgressBar(false)
                    // show alert dialog
                    showAlertDialog(it.message)
                }

                is Resource.Loading -> {
                    Log.d(TAG, "Loading: ")
                    viewProgressBar(true)
                }

                is Resource.Success -> {
                    Log.d(TAG, "Success: ")
                    // hide progress bar
                    viewProgressBar(false)
                    // set adapter
                    updateUi(it.data)
                }
            }
        }
    }

    private fun showAlertDialog(error: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Failed to Load Details!")
        builder.setMessage(error)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun viewProgressBar(isShow: Boolean) {
        fragmentDetailBinding.progressHorizontal.visibility = if (isShow) View.VISIBLE else View.GONE
        fragmentDetailBinding.lvDetail.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    private fun updateUi(characterItem: CharacterItem) {
        fragmentDetailBinding.tvName.text = characterItem.name
        fragmentDetailBinding.tvSpecies.text = characterItem.species
        fragmentDetailBinding.tvGender.text = characterItem.gender
        fragmentDetailBinding.tvStatus.text = characterItem.status
        fragmentDetailBinding.tvLocation.text = characterItem.location?.name

        // load character image
        characterItem.image?.let {
            Glide.with(requireContext())
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(fragmentDetailBinding.ivCharacter)
        }

        // set episode adapter
        characterItem.episode?.let { episode ->
            fragmentDetailBinding.rvEpisodes.layoutManager = LinearLayoutManager(requireContext())
            fragmentDetailBinding.rvEpisodes.itemAnimator = DefaultItemAnimator()
            val episodeAdapter = EpisodeAdapter(requireContext(), episode)
            fragmentDetailBinding.rvEpisodes.adapter = episodeAdapter
        }
    }


}