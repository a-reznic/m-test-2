package moziotest2.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moziotest2.domain.Flavor
import moziotest2.domain.ShopState
import moziotest2.repository.ShopRepository
import moziotest2.service.FlavorService

class ShopViewModel(
    private val repository: ShopRepository,
    private val flavorService: FlavorService,
) : ViewModel() {

    private val _data = MutableStateFlow(ShopState())
    val data = _data.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            val pizzaList = repository.loadPizza()
            val allFlavors = repository.getAllFlavors()

            _data.emit(
                ShopState(
                    pizzaList = pizzaList,
                    selectedFlavors = allFlavors,
                    isPizzaSelected = flavorService.isPizzaSelected(),
                    totalPrice = flavorService.calculateSum()
                )
            )
        }
    }

    fun buy(item: Flavor) {
        viewModelScope.launch {
            flavorService.addFlavor(item)
            loadData()
        }
    }

    fun reset() {
        viewModelScope.launch {
            repository.clearFlavors()
            loadData()
        }
    }
}