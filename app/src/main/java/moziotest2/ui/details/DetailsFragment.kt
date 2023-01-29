package moziotest2.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import mozio.test2.R
import mozio.test2.databinding.FragmentOrderDetailsBinding
import moziotest2.domain.Flavor
import moziotest2.domain.Pizza
import moziotest2.domain.ShopState
import moziotest2.ui.shop.ShopViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModel<ShopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            exitBtn.setOnClickListener { activity?.finish() }
            backBtn.setOnClickListener { findNavController().navigateUp() }
        }
        collect()
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.data.collect {
                    showDetails(it)
                }
            }
        }
    }

    private fun showDetails(shopData: ShopState) {
        with(binding) {
            selectedFlavorTextView.text = prepareDetailsText(shopData.selectedFlavors, shopData.pizzaList)
            totalTextView.text = getString(R.string.details_total, shopData.totalPrice)
        }
    }

    private fun prepareDetailsText(selectedFlavors: Map<Long, Flavor>, pizzaList: List<Pizza>): String {
        return selectedFlavors.map { flavorEntry ->
            val flavor = flavorEntry.value
            val name = pizzaList.find { it.id == flavor.id }?.name
            return@map getString(
                R.string.details_selected_flavor,
                name,
                flavor.type.toString(), flavor.price
            )
        }.joinToString("\n")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}