package moziotest2.adapter

import androidx.viewbinding.ViewBinding

abstract class BaseBindHolder<T, B : ViewBinding>(open val binding: B) : BaseHolder(binding.root) {
    abstract fun onBind(item: T)
}