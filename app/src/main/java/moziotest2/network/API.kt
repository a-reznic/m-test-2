package moziotest2.network

import moziotest2.network.dto.PizzaDTO
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("/mobile/tests/pizzas.json")
    suspend fun listOfPizza(): Response<List<PizzaDTO>>
}