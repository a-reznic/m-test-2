package moziotest2.repository

import moziotest2.domain.Flavor
import moziotest2.domain.Pizza

interface ShopRepository {
    suspend fun loadPizza(): List<Pizza>

    suspend fun selectFlavor(item: Flavor)
    suspend fun getAllFlavors(): Map<Long, Flavor>
    suspend fun clearFlavors()
}