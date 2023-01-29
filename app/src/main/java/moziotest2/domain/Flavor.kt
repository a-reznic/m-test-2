package moziotest2.domain

class Flavor(
    val id: Long,
    val type: FlavorType,
    val price: Double,
    var selected: Boolean = false,
    var text: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Flavor

        if (type != other.type) return false
        if (price != other.price) return false
        if (selected != other.selected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }

    override fun toString(): String {
        return "Flavor(id=$id, type=$type, price=$price, selected=$selected)"
    }
}
