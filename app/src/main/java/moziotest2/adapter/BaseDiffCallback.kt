package moziotest2.adapter

import androidx.recyclerview.widget.DiffUtil

open class BaseDiffCallback<T : ShopAdapterObject> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.name == newItem.name && oldItem.flavors == newItem.flavors
    }
}