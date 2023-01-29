package moziotest2.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import mozio.test2.R
import mozio.test2.databinding.FragmentShopBinding
import moziotest2.adapter.ShopAdapter
import moziotest2.adapter.ShopAdapterObject
import moziotest2.domain.Flavor
import moziotest2.domain.FlavorType
import moziotest2.domain.FlavorType.FULL
import moziotest2.domain.FlavorType.HALF
import moziotest2.domain.Pizza
import moziotest2.domain.ShopState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val shopAdapter by lazy { ShopAdapter<ShopAdapterObject>() }
    private val viewModel by activityViewModel<ShopViewModel>()
    private var shopState: ShopState = ShopState()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonDetails.setOnClickListener {
                findNavController().navigate(R.id.action_Shop_to_DetailsFragment)
            }
            buttonReset.setOnClickListener {
                viewModel.reset()
            }
            recyclerView.adapter = shopAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        collect()
        viewModel.loadData()
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.data.collect {
                    convertToAdapter(it)
                }
            }
        }
    }

    private fun convertToAdapter(shopState: ShopState) {
        this.shopState = shopState

        val list = shopState.pizzaList.map { pizza ->
            val flavors = createFlowers(pizza, shopState.selectedFlavors)

            ShopAdapterObject(
                name = pizza.name,
                id = pizza.id,
                flavors = flavors,
                imageRes = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.pizza
                ),
                iconRes = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_check
                ),
                onClickListener = { onItem(it) }
            )
        }
        shopAdapter.submitList(list)
//        shopAdapter.notifyDataSetChanged()

        binding.buttonDetails.isEnabled = shopState.isPizzaSelected
    }

    private fun createFlowers(pizza: Pizza, selectedFlavors: Map<Long, Flavor>): List<Flavor> {
        return listOf(
            Flavor(
                id = pizza.id, type = FULL, price = pizza.price,
                selected = isSelectedFlower(FULL, pizza, selectedFlavors),
                text = getString(R.string.flavor_full, pizza.price)
            ),
            Flavor(
                id = pizza.id, type = HALF, price = pizza.priceHalf,
                selected = isSelectedFlower(HALF, pizza, selectedFlavors),
                text = getString(R.string.flavor_half, pizza.priceHalf)
            ),
        )
    }

    private fun isSelectedFlower(type: FlavorType, pizza: Pizza, selectedFlavors: Map<Long, Flavor>): Boolean {
        if (selectedFlavors.isEmpty()) return false
        return selectedFlavors.any {
            val flavor = it.value
            flavor.id == pizza.id && flavor.type == type
        }
    }

    private fun onItem(item: Flavor) {
        if (shopState.isPizzaSelected) {
            Toast.makeText(
                requireContext(),
                R.string.you_select_full_pizza, Toast.LENGTH_SHORT
            ).show()

            return
        }
        viewModel.buy(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}