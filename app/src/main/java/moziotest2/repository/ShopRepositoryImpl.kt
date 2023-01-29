package moziotest2.repository

import moziotest2.domain.Flavor
import moziotest2.domain.Pizza
import moziotest2.domain.mappers.Mapper
import moziotest2.network.API
import moziotest2.network.dto.PizzaDTO
import retrofit2.Response

class ShopRepositoryImpl(private val api: API) : ShopRepository {
    /**
     * can be replaced
     */
    private val inMemoryDbSelectedFlavors = mutableMapOf<Long, Flavor>()
    private var inMemoryDbListOfPizza = mutableListOf<Pizza>()

    override suspend fun loadPizza(): List<Pizza> {
        if (inMemoryDbListOfPizza.isNotEmpty()) return inMemoryDbListOfPizza

        val response: Response<List<PizzaDTO>> = api.listOfPizza()
        if (response.isSuccessful) {
            val body = response.body() ?: return emptyList()
            val pizzaList = Mapper.toPizza(body)
            insertIntoDB(pizzaList)
            return pizzaList
        }
        return emptyList()
    }

    private fun insertIntoDB(pizzaList: List<Pizza>) {
        inMemoryDbListOfPizza.clear()
        inMemoryDbListOfPizza.addAll(pizzaList)
    }

    override suspend fun selectFlavor(item: Flavor) {
        inMemoryDbSelectedFlavors[item.id] = item
    }

    override suspend fun getAllFlavors(): Map<Long, Flavor> {
        return inMemoryDbSelectedFlavors
    }

    override suspend fun clearFlavors() {
        inMemoryDbSelectedFlavors.clear()
    }
}