package moziotest2.repository

import moziotest2.domain.Pizza
import moziotest2.domain.mappers.Mapper
import moziotest2.network.API

class ShopRepositoryImpl(private val api: API) : ShopRepository {

    override suspend fun loadPizza(): List<Pizza> {
        val listOfPizza = api.listOfPizza()
        if (listOfPizza.isSuccessful) {
            val body = listOfPizza.body() ?: return emptyList()

            return Mapper.toPizza(body)
        }
        return emptyList()
    }
}