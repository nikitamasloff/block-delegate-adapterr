import androidx.recyclerview.widget.DiffUtil.ItemCallback

class DelegateItemDiffCallback<T : Any>(
    private val delegateDispatcher: RecyclerDelegateDispatcher,
    private val defaultItemDiffCallback: ItemCallback<in T> = DefaultItemDiffCallback()
) : ItemCallback<T>() {

    @Suppress("UNCHECKED_CAST")
    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        val diffItemCallback =
            (delegateDispatcher.findItemDiffCallbackFor(oldItem, newItem)
                ?: defaultItemDiffCallback) as ItemCallback<in T>
        return diffItemCallback.areItemsTheSame(oldItem, newItem)
    }

    @Suppress("UNCHECKED_CAST")
    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        val diffItemCallback =
            (delegateDispatcher.findItemDiffCallbackFor(oldItem, newItem)
                ?: defaultItemDiffCallback) as ItemCallback<in T>
        return diffItemCallback.areContentsTheSame(oldItem, newItem)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getChangePayload(
        oldItem: T,
        newItem: T
    ): Any? {
        val diffItemCallback =
            (delegateDispatcher.findItemDiffCallbackFor(oldItem, newItem)
                ?: defaultItemDiffCallback) as ItemCallback<in T>
        return diffItemCallback.getChangePayload(oldItem, newItem)
    }
}
