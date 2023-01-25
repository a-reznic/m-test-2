package moziotest2.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : BaseAdapterObject>(diffCallback: BaseDiffCallback<T> = BaseDiffCallback()) :
    ListAdapter<T, BaseHolder>(diffCallback) {
    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.value
    }

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