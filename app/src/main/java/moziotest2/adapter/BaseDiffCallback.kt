package moziotest2.adapter

import androidx.recyclerview.widget.DiffUtil

open class BaseDiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem is BaseAdapterObject && newItem is BaseAdapterObject) {
            return oldItem.id == newItem.id
        }
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem is GenericAdapterObject && newItem is GenericAdapterObject) {
            return oldItem.value == newItem.value
        }

        return oldItem == newItem
    }

}