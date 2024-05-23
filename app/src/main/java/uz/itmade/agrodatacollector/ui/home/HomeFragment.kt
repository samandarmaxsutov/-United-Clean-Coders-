package uz.itmade.agrodatacollector.ui.home

import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import uz.itmade.agrodatacollector.R
import uz.itmade.agrodatacollector.adapters.ImageAdapter
import uz.itmade.agrodatacollector.databinding.FragmentHomeBinding
import uz.itmade.agrodatacollector.ui.signup.SignUpScreenViewModel
import uz.itmade.agrodatacollector.utils.withIcon

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var progressBar: ProgressBar
    private val navController : NavController by lazy { findNavController() }

    private  val adapter: ImageAdapter by lazy { ImageAdapter() }
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.messageLiveData.observe(this){
            withIcon(it,requireActivity())
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }



    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        progressBar =binding.progressBar
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter =adapter
        viewModel.imagesLiveData.observe(viewLifecycleOwner){
            Log.d("|T",it.toString())
            adapter.submitItems(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}