package moziotest2.domain

class ShopState(
    val pizzaList: List<Pizza> = emptyList(),
    val selectedFlavors: Map<Long, Flavor> = emptyMap(),
    val isPizzaSelected: Boolean = false,
    val totalPrice: Double = 0.0,
)