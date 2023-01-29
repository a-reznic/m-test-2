package moziotest2.adapter

import android.graphics.drawable.Drawable
import moziotest2.domain.Flavor

data class ShopAdapterObject(
    val id: Long,
    val name: String,
    val imageRes: Drawable? = null,
    val iconRes: Drawable? = null,
    val flavors: List<Flavor>,
    val onClickListener: ((Flavor) -> Unit),
)
