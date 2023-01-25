package moziotest2.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import moziotest2.domain.ShopData
import moziotest2.repository.ShopRepository

class ShopViewModel(val repository: ShopRepository) : ViewModel() {

    private val _data = MutableStateFlow(ShopData())
    val data = _data.asStateFlow()

    fun loadPizza() {
        viewModelScope.launch {
            val pizzaList = repository.loadPizza()
            _data.emit(ShopData(pizzaList = pizzaList))
        }
    }
}