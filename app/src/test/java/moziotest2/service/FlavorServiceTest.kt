package moziotest2.service

import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import moziotest2.domain.Flavor
import moziotest2.domain.FlavorType
import moziotest2.domain.FlavorType.FULL
import moziotest2.domain.FlavorType.HALF
import moziotest2.network.API
import moziotest2.repository.ShopRepositoryImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FlavorServiceTest {
    private val api: API = mockkClass(API::class)
    private val repository = ShopRepositoryImpl(api)
    private val flavorService = FlavorService(repository)

    @Before
    fun init() {
        coEvery { api.listOfPizza() } returns Response.success(emptyList())
    }

    @Test
    fun testAddFlavor() {
        runBlocking {
            val flavor = Flavor(id = 1, FlavorType.FULL, 10.0)

            flavorService.addFlavor(flavor)
            val allFlavors: Map<Long, Flavor> = repository.getAllFlavors()
            assert(allFlavors.size == 1)
            val flavor1 = allFlavors.getValue(1)
            assert(flavor1 == flavor)
            Assert.assertEquals("", 10.0, flavorService.calculateSum(), 0.01)
        }
    }

    @Test
    fun testAddFlavor2() {
        runBlocking {
            val flavor = Flavor(id = 1, HALF, 5.0)
            val flavorHalf2 = Flavor(id = 2, HALF, 6.0)

            flavorService.addFlavor(flavor)
            flavorService.addFlavor(flavorHalf2)

            val allFlavors: Map<Long, Flavor> = repository.getAllFlavors()
            assert(allFlavors.getValue(1) == flavor)
            assert(allFlavors.getValue(2) == flavorHalf2)

            Assert.assertEquals("", 11.0, flavorService.calculateSum(), 0.01)
        }
    }

    @Test
    fun `test bad replace half to full`() {
        runBlocking {
            val flavorFull = Flavor(id = 1, FULL, 10.0)
            val flavorHalf = Flavor(id = 2, HALF, 6.0)

            flavorService.addFlavor(flavorHalf)
            flavorService.addFlavor(flavorFull)
            //ignored
            flavorService.addFlavor(flavorFull)
            //ignored
            flavorService.addFlavor(flavorFull)

            val allFlavors: Map<Long, Flavor> = repository.getAllFlavors()
            assert(allFlavors.size == 1)
            assert(allFlavors.getValue(1) == flavorFull)

            Assert.assertEquals("", 10.0, flavorService.calculateSum(), 0.01)
        }
    }

    @Test
    fun `test isPizzaSelected`() {
        runBlocking {
            val flavorHalf = Flavor(id = 2, HALF, 6.0)
            val flavorHalf2 = Flavor(id = 3, HALF, 4.0)

            flavorService.addFlavor(flavorHalf)
            Assert.assertFalse(flavorService.isPizzaSelected())
            flavorService.addFlavor(flavorHalf2)
            Assert.assertTrue(flavorService.isPizzaSelected())
        }
    }

    @Test
    fun `test isPizzaSelected full`() {
        runBlocking {
            val flavorFull = Flavor(id = 1, FULL, 10.0)

            flavorService.addFlavor(flavorFull)
            Assert.assertTrue(flavorService.isPizzaSelected())
        }
    }
}