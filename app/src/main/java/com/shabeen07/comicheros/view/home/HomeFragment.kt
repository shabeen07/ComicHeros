package com.shabeen07.comicheros.view.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shabeen07.comicheros.R
import com.shabeen07.comicheros.adapters.CharacterItemAdapter
import com.shabeen07.comicheros.api.Resource
import com.shabeen07.comicheros.databinding.FragmentHomeBinding
import com.shabeen07.comicheros.models.CharacterItem
import com.shabeen07.comicheros.models.CharacterResponse

class HomeFragment : Fragment(), CharacterItemAdapter.ItemClickCallback {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    companion object{
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initUi()

        // observe characters
        viewModel.characters.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "Error: ${it.message}")
                    viewProgressBar(false)
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
                    setCharacterAdapter(it.data)
                }
            }
        }

    }

    private fun initUi() {
        binding.rvCharacters.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharacters.itemAnimator = DefaultItemAnimator()

        binding.swipeRefreshLayout.setOnRefreshListener {
            // load products
            Handler(Looper.getMainLooper()).postDelayed({
                // Load characters
                // shopViewModel.getProducts()

                binding.swipeRefreshLayout.isRefreshing = false
            }, 1500)
        }
    }

    private fun viewProgressBar(isShow: Boolean) {
        binding.progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun setCharacterAdapter(response: CharacterResponse) {
        response.results?.let { characterList ->
            val shopAdapter = CharacterItemAdapter(requireContext(), characterList, this)
            binding.rvCharacters.adapter = shopAdapter
        }
    }

    override fun onAddItemClick(characterItem: CharacterItem) {
        val args = Bundle()
        args.putInt("characterId", characterItem.id)

        // navigate to detail
        navController.navigate(R.id.action_homeFragment_to_detailFragment, args = args)
    }

}