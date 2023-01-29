package moziotest2.domain.mappers

import moziotest2.domain.Pizza
import moziotest2.network.dto.PizzaDTO

object Mapper {
    fun toPizza(body: List<PizzaDTO>): List<Pizza> {
        return body.mapIndexed { index, pizzaDTO ->
            fromDtoToPizza(pizzaDTO, index)
        }
    }

    fun fromDtoToPizza(input: PizzaDTO, id: Int): Pizza {
        val price = input.price ?: 0.0
        return Pizza(
            id = id.toLong(),
            name = input.name.orEmpty(),
            price = price,
            priceHalf = price / 2
        )
    }
}
