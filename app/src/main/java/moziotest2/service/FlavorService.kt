package moziotest2.service

import moziotest2.domain.Flavor
import moziotest2.domain.FlavorType.FULL
import moziotest2.domain.FlavorType.HALF
import moziotest2.repository.ShopRepository

class FlavorService(private val repository: ShopRepository) {

    suspend fun addFlavor(item: Flavor) {
        if (!canAddFlavor()) {
            return
        }
        if (item.type == FULL) {
            repository.clearFlavors()
        } else {
            val flavors = repository.getAllFlavors()
            val anyFull = flavors.any { it.value.type == FULL }
            if (anyFull) {
                repository.clearFlavors()
            }
        }
        repository.selectFlavor(item)
    }

    suspend fun isPizzaSelected(): Boolean {
        val allFlavors = repository.getAllFlavors()

        if (allFlavors.isEmpty()) return false
        if (allFlavors.values.filter { it.type == FULL }.size == 1) return true
        if (allFlavors.values.filter { it.type == HALF }.size == 2) return true

        return false
    }

    suspend fun canAddFlavor(): Boolean {
        val allFlavors = repository.getAllFlavors()

        if (allFlavors.isEmpty()) return true

        if (allFlavors.values.any { it.type == FULL }) return false
        if (allFlavors.values.filter { it.type == HALF }.size >= 2) return false

        return true
    }

    suspend fun calculateSum(): Double {
        val allFlavors = repository.getAllFlavors()
        if (allFlavors.isEmpty()) return 0.0

        return allFlavors.values.sumOf { it.price }
    }
}