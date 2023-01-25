package moziotest2.repository

import moziotest2.domain.Pizza

interface ShopRepository {
    suspend fun loadPizza(): List<Pizza>
}