package moziotest2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import mozio.test2.databinding.BaseShopItemBinding
import moziotest2.domain.Flavor
import moziotest2.domain.FlavorType.FULL
import moziotest2.domain.FlavorType.HALF

class ShopAdapter<T : ShopAdapterObject>(
    diff: BaseDiffCallback<T> = BaseDiffCallback(),
) : BaseAdapter<T>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HeaderHolder(BaseShopItemBinding.inflate(inflater, parent, false))
    }
}

class HeaderHolder(b: BaseShopItemBinding) :
    BaseBindHolder<ShopAdapterObject, BaseShopItemBinding>(b) {
    override fun onBind(item: ShopAdapterObject) {
        with(binding) {
            image.background = item.imageRes
            name.text = item.name
            halfPriceBtn.icon = null
            fullPriceBtn.icon = null

            item.flavors.forEach { flavor ->
                when (flavor.type) {
                    FULL -> {
                        if (flavor.selected) {
                            fullPriceBtn.icon = item.iconRes
                        }
//                        initButton(fullPriceBtn, flavor) {
//                            resetSelected(item, flavor)
                        fullPriceBtn.text = flavor.text
                        fullPriceBtn.setOnClickListener { item.onClickListener.invoke(flavor) }
//                            resetAllButtonsIcon(listOf(fullPriceBtn, halfPriceBtn))
//                            fullPriceBtn.icon = item.iconRes
//                        }
                    }
                    HALF -> {
                        if (flavor.selected) {
                            halfPriceBtn.icon = item.iconRes
                        }
                        halfPriceBtn.text = flavor.text
                        halfPriceBtn.setOnClickListener { item.onClickListener.invoke(flavor) }

                    }
                }
            }
        }
    }

    private fun resetAllButtonsIcon(list: List<MaterialButton>) {
        list.forEach { it.icon = null }
    }

    private fun resetSelected(item: ShopAdapterObject, flavor: Flavor) {
        item.flavors.forEach {
            it.selected = false
        }
        flavor.selected = true

    }

    private fun initButton(
        btn: MaterialButton,
        flavor: Flavor,
        onClickListener: (Flavor) -> Unit,
    ) {
        btn.text = flavor.price.toString()
        btn.setOnClickListener { onClickListener.invoke(flavor) }
    }
}
