import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class DelegateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    protected open val delegateDispatcher: RecyclerDelegateDispatcher,
    diffCallback: DiffUtil.ItemCallback<T> = DelegateItemDiffCallback(
        delegateDispatcher = delegateDispatcher,
        defaultItemDiffCallback = DefaultItemDiffCallback()
    )
) : ListAdapter<T, VH>(diffCallback) {

    @LayoutRes
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return getDelegateViewTypeForItem(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val inflater = LayoutInflater.from(parent.context)
        val delegate = findDelegateByViewType(viewType)
        val viewHolder = delegate.onCreateViewHolder(inflater, parent, layoutResId = viewType, adapter = this)
        return try {
            @Suppress("UNCHECKED_CAST")
            viewHolder as VH
        } catch (e: ClassCastException) {
            val errMsg = "Wrong ViewHolder type: [${viewHolder::class}]. " +
                "The delegate [$delegate] with the layout resource identifier [$viewType] " +
                "creates ViewHolders of the type different from the type of ViewHolders " +
                "the current adapter works with. " +
                "Please, provide a suitable ViewHolder " +
                "or fix the delegate match condition (see [RecyclerDelegate.isDelegateFor]) " +
                "to use correct ViewHolder " +
                "or identify why this DelegatesDispatcher [$delegateDispatcher] contains such a wrong delegate."
            throw IllegalStateException(errMsg, e)
        }
    }

    override fun onBindViewHolder(
        viewHolder: VH,
        position: Int
    ) {
        val item = getItem(position)
        @Suppress("UNCHECKED_CAST")
        val delegate = findDelegateForItem(item) as RecyclerDelegate<@UnsafeVariance T, @UnsafeVariance VH>
        try {
            delegate.onBindViewHolder(item, viewHolder, position, adapter = this)
        } catch (e: ClassCastException) {
            val errMsg = "Wrong ViewHolder type [${viewHolder::class}] or wrong data type [${item::class}] " +
                "The delegate [$delegate] that processes data of type [${item::class} " +
                "binds ViewHolders of the type different from the type of ViewHolders the current adapter works with " +
                "or binds data of the type different from the type of data the current adapter works with. " +
                "Please, provide a suitable ViewHolder " +
                "or fix the delegate match condition (see [RecyclerDelegate.isDelegateFor]) " +
                "to use correct ViewHolder type or data type " +
                "or identify why this DelegatesDispatcher [$delegateDispatcher] contains such a wrong delegate."
            throw IllegalStateException(errMsg, e)
        }
    }

    override fun onBindViewHolder(
        viewHolder: VH,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(viewHolder, position)
        } else {
            val item = getItem(position)
            @Suppress("UNCHECKED_CAST")
            val delegate = findDelegateForItem(item) as RecyclerDelegate<@UnsafeVariance T, @UnsafeVariance VH>
            try {
                delegate.onBindViewHolder(item, viewHolder, payloads, position, adapter = this)
            } catch (e: ClassCastException) {
                val errMsg = "Wrong ViewHolder type [${viewHolder::class}] or wrong data type [${item::class}] " +
                    "The delegate [$delegate] that processes data of type [${item::class} " +
                    "binds ViewHolders of the type different from the type of ViewHolders " +
                    "the current adapter works with " +
                    "or binds data of the type different from the type of data the current adapter works with. " +
                    "Please, provide a suitable ViewHolder " +
                    "or fix the delegate match condition (see [RecyclerDelegate.isDelegateFor]) " +
                    "to use correct ViewHolder type or data type " +
                    "or identify why this DelegatesDispatcher [$delegateDispatcher] contains such a wrong delegate."
                throw IllegalStateException(errMsg, e)
            }
        }
    }

    override fun onViewRecycled(viewHolder: VH) {
        @LayoutRes val layoutResId = viewHolder.itemViewType

        @Suppress("UNCHECKED_CAST")
        val delegate = findDelegateByViewType(layoutResId) as RecyclerDelegate<@UnsafeVariance T, @UnsafeVariance VH>
        try {
            delegate.onViewRecycled(viewHolder, adapter = this)
        } catch (e: ClassCastException) {
            val errMsg = "Wrong ViewHolder type: [${viewHolder::class}]. " +
                "The delegate [$delegate] with the layout resource identifier [$layoutResId] " +
                "recycles ViewHolders of the type different from the type of ViewHolders " +
                "the current adapter works with. " +
                "Please, provide a suitable ViewHolder " +
                "or fix the delegate match condition (see [RecyclerDelegate.isDelegateFor]) " +
                "to use correct ViewHolder type " +
                "or identify why this DelegatesDispatcher [$delegateDispatcher] contains such a wrong delegate."
            throw IllegalStateException(errMsg, e)
        }
    }

    protected open fun getDelegateViewTypeForItem(item: T): Int =
        delegateDispatcher.getDelegateViewTypeForItem(item)
            ?: error("Delegate for item [$item] was not found. Register one via [${RecyclerDelegateDispatcher::class}]")

    protected open fun findDelegateByViewType(viewType: Int): RecyclerDelegate<*, *> =
        delegateDispatcher.findDelegateByViewType(viewType)
            ?: error("Internal error. Delegate was not found by viewType [$viewType]")

    protected open fun findDelegateForItem(
        item: T
    ): RecyclerDelegate<*, *> =
        delegateDispatcher.findDelegateForItem(item)
            ?: error("Delegate for item [$item] was not found. Register one via [${RecyclerDelegateDispatcher::class}]")
}
