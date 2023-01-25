package moziotest2.domain.mappers

import moziotest2.adapter.ShopAdapterObject
import moziotest2.domain.Pizza
import moziotest2.network.dto.PizzaDTO
import kotlin.random.Random

object Mapper {
    fun toPizza(body: List<PizzaDTO>): List<Pizza> {
        return body.map {
            fromDtoToPizza(it)
        }
    }

    fun fromDtoToPizza(input: PizzaDTO): Pizza {
        val price = input.price ?: 0.0
        return Pizza(
            id = Random.nextLong(),
            name = input.name.orEmpty(),
            price = price,
            priceHalf = price / 2
        )
    }
}
