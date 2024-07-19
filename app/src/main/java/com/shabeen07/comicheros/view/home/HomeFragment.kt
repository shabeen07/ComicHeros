package com.shabeen07.comicheros.view.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.shabeen07.comicheros.R
import com.shabeen07.comicheros.adapters.CharacterItemAdapter
import com.shabeen07.comicheros.api.Resource
import com.shabeen07.comicheros.databinding.FragmentHomeBinding
import com.shabeen07.comicheros.models.CharacterItem
import com.shabeen07.comicheros.models.CharacterResponse
import com.shabeen07.comicheros.utils.PaginationScrollListener

class HomeFragment : Fragment(), CharacterItemAdapter.ItemClickCallback {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var characterItemAdapter: CharacterItemAdapter
    private var characterList = mutableListOf<CharacterItem>()
    private var isLoading = false
    private var isLastPage = false
    private val totalPages = 10

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
        // This callback will only be called when MyFragment is at least Started.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            requireActivity().finish()
        }

        // observe characters
        viewModel.characters.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "Error: ${it.message}")
                    viewProgressBar(false)
                    // show error dialog
                    showAlertDialog(it.message)
                    isLoading = false
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
                    isLoading = false
                }
            }
        }

    }

    private fun showAlertDialog(error: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Failed to Load Characters!")
        builder.setMessage(error)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun initUi() {
        val linearLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharacters.layoutManager = linearLayoutManager
        binding.rvCharacters.itemAnimator = DefaultItemAnimator()
        characterItemAdapter = CharacterItemAdapter(requireContext(),characterList,this)
        binding.rvCharacters.adapter = characterItemAdapter

        // refresh layout
        binding.swipeRefreshLayout.setOnRefreshListener {
            // load products
            Handler(Looper.getMainLooper()).postDelayed({
                // Load characters
                reloadCharacters()

                binding.swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }

        // Add the pagination scroll listener
        binding.rvCharacters.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                val currentPage = viewModel.currentPage
                viewModel.setPage(currentPage+1)
                if (currentPage <= totalPages) {
                    // Simulate network latency for API call
                    Handler(Looper.getMainLooper()).postDelayed({
                       viewModel.getCharacters()
                    }, 2000)
                }
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

    }

    private fun reloadCharacters() {
        // clear list
        characterItemAdapter.clearAll()

        viewModel.setPage(1)
        viewModel.getCharacters()
    }

    private fun viewProgressBar(isShow: Boolean) {
        binding.loadingProgress.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun setCharacterAdapter(response: CharacterResponse) {
        response.results?.let { characterList ->
            characterItemAdapter.addAll(characterList)
        }
    }

    override fun onAddItemClick(characterItem: CharacterItem) {
        val args = Bundle()
        args.putInt("characterId", characterItem.id)

        // navigate to detail
        navController.navigate(R.id.action_homeFragment_to_detailFragment, args = args)
    }

}