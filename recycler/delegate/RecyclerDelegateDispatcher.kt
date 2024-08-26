import androidx.recyclerview.widget.DiffUtil

interface RecyclerDelegateDispatcher {

    fun addDelegate(delegate: RecyclerDelegate<*, *>)

    fun <T : Any> getDelegateViewTypeForItem(item: T): Int?

    fun findDelegateByViewType(viewType: Int): RecyclerDelegate<*, *>?

    fun <T : Any> findDelegateForItem(item: T): RecyclerDelegate<T, *>?

    fun <T : Any> findItemDiffCallbackFor(
        oldItem: T,
        newItem: T
    ): DiffUtil.ItemCallback<in T>?
}

operator fun RecyclerDelegateDispatcher.plusAssign(delegate: RecyclerDelegate<*, *>) =
    addDelegate(delegate)

fun recyclerDelegateDispatcher(
    vararg delegates: RecyclerDelegate<*, *>
): RecyclerDelegateDispatcher =
    DefaultRecyclerDelegateDispatcher(delegates.toList())

fun recyclerDelegateDispatcher(
    delegates: Collection<RecyclerDelegate<*, *>> = emptyList()
): RecyclerDelegateDispatcher =
    DefaultRecyclerDelegateDispatcher(delegates)
