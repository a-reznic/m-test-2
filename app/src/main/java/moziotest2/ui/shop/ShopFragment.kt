package moziotest2.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import moziotest2.domain.ShopData
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    val shopAdapter by lazy { ShopAdapter<ShopAdapterObject>() }
    private val viewModel by activityViewModel<ShopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

            recyclerView.adapter = shopAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        collect()
        viewModel.loadPizza()
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

    private fun convertToAdapter(it: ShopData) {
        val list = it.pizzaList.map { pizza ->
            ShopAdapterObject(
                name = pizza.name,
                price = pizza.price.toString(),
                priceHalf = pizza.priceHalf.toString(),
                imageRes = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.pizza
                )
            )
        }
        shopAdapter.submitList(list)
        shopAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}