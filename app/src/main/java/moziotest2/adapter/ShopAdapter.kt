package moziotest2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import mozio.test2.databinding.BaseShopItemBinding

class ShopAdapter<T : BaseAdapterObject>(
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
            price.text = item.price
            priceHalf.text = item.priceHalf
        }
    }
}
