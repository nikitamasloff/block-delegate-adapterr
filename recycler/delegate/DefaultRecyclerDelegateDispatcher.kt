import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView

open class DefaultRecyclerDelegateDispatcher @JvmOverloads internal constructor(
    delegates: Collection<RecyclerDelegate<*, *>> = emptyList()
) : RecyclerDelegateDispatcher {

    protected open val delegates = delegates.toMutableList()

    override fun addDelegate(delegate: RecyclerDelegate<*, *>) {
        delegates.add(delegate)
    }

    override fun <T : Any> getDelegateViewTypeForItem(item: T): Int? {
        val delegate = findDelegateForItem(item) ?: return null
        return delegates.indexOf(delegate).takeIf { it != -1 }
    }

    override fun findDelegateByViewType(viewType: Int): RecyclerDelegate<*, *>? =
        delegates.getOrNull(index = viewType)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> findDelegateForItem(item: T): RecyclerDelegate<T, *>? =
        delegates.find { delegate ->
            if (delegate.itemType != item::class.java) {
                return@find false
            }
            delegate as RecyclerDelegate<T, RecyclerView.ViewHolder>
            delegate.isDelegateFor(item)
        } as RecyclerDelegate<T, RecyclerView.ViewHolder>?

    override fun <T : Any> findItemDiffCallbackFor(
        oldItem: T,
        newItem: T
    ): ItemCallback<in T>? {
        val oldItemDelegate = findDelegateForItem(oldItem)
        val newItemDelegate = findDelegateForItem(newItem)
        // if found delegates for [oldItem] and for [newItem] are the same object
        return if (newItemDelegate != null && newItemDelegate === oldItemDelegate) newItemDelegate.itemDiffCallback
        else null
    }
}
