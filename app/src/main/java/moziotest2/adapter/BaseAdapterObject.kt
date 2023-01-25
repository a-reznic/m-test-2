package moziotest2.adapter

import android.graphics.drawable.Drawable
import moziotest2.adapter.BaseItemType.SIMPLE
import java.util.*

enum class BaseItemType(val value: Int) {
    HEADER(0),
    SIMPLE(1),
    SHOW_ARROW(2),
    INPUT_TEXT(3),
    SWITCH(4),
}

open class BaseAdapterObject(
    open val id: String = UUID.randomUUID().toString(),
    open val type: BaseItemType = SIMPLE,
    open val onClickListener: ((BaseAdapterObject) -> Unit)? = null,
    open val onSwitchChanged: ((BaseAdapterObject, Boolean) -> Unit)? = null,
    open val validateInput: ((GenericAdapterObject) -> Boolean)? = null,
) {
    override fun toString(): String {
        return "BaseItemObject(type=$type)"
    }
}

data class ShopAdapterObject(
    override val type: BaseItemType = SIMPLE,
    val name: String,
    val imageRes: Drawable? = null,
    val price: String,
    val priceHalf: String,
) : BaseAdapterObject()

open class GenericAdapterObject(
    override val id: String = UUID.randomUUID().toString(),
    override val type: BaseItemType = SIMPLE,
    open val title: String,
    open var value: String = "",
    override val onClickListener: ((BaseAdapterObject) -> Unit)? = null,
) : BaseAdapterObject()