package uz.itmade.agrodatacollector.ui.notifications

import android.annotation.SuppressLint
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.launch
import uz.itmade.agrodatacollector.R
import uz.itmade.agrodatacollector.adapters.ImageAdapter
import uz.itmade.agrodatacollector.adapters.ProductAdapter
import uz.itmade.agrodatacollector.databinding.FragmentHomeBinding
import uz.itmade.agrodatacollector.databinding.FragmentNotificationsBinding
import uz.itmade.agrodatacollector.model.Repository
import uz.itmade.agrodatacollector.model.RepositorySignIn
import uz.itmade.agrodatacollector.ui.home.HomeViewModel
import uz.itmade.agrodatacollector.utils.withIcon

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
    private lateinit var progressBar: ProgressBar
    private val navController : NavController by lazy { findNavController() }

    private  val adapter: ProductAdapter by lazy { ProductAdapter() }
    private val viewModel: NotificationsViewModel by viewModels()
    private var _binding: FragmentNotificationsBinding? = null

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
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        progressBar =binding.progressBar
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter =adapter
        viewModel.productsLiveData.observe(viewLifecycleOwner){
            Log.d("|T",it.toString())
            adapter.submitItems(it)
        }
        lifecycleScope.launch {
            Repository.getUserRepo.get(RepositorySignIn.getInstance().getPhone()).observe(requireActivity()){
                if(it.isSuccess){
                    val item = it.getOrNull()
                    binding.textView.text= "Sizning AgroCoinlaringiz \uD83D\uDCB0${item!!.agroCoin}"
                    binding.textView2.text= "Xush kelibsiz,\n${item.name}"

                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}