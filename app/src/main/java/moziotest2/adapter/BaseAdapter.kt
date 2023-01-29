package moziotest2.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : ShopAdapterObject>(diffCallback: BaseDiffCallback<T> = BaseDiffCallback()) :
    ListAdapter<T, BaseHolder>(diffCallback) {


    open fun onBindHolder(binding: ViewBinding, position: Int) {}

    open fun onBindHolder(holder: BaseHolder, binding: ViewBinding, position: Int) {
        onBindHolder(binding, position)
    }

    final override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        (holder as? BaseBindHolder<T, *>)?.let {
            it.onBind(getItem(position))
            onBindHolder(holder, it.binding, position)
        }
    }
}