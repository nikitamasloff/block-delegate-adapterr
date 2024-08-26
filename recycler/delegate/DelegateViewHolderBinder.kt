import androidx.recyclerview.widget.RecyclerView

interface DelegateViewHolderBinder <T : Any, VH : RecyclerView.ViewHolder> {

    /**
     * Reified data type [T]
     */
    val itemType: Class<T>

    /**
     * Binding of [holder] of type [VH] with data [item]
     *
     * @param item data for binding
     * @param holder holder for binding
     * @param position current binding position
     * @param adapter adapter; can be used to get any additional information,
     *                e.g. all items list: [adapter.currentList]
     */
    fun onBindViewHolder(
        item: T,
        holder: VH,
        position: Int,
        adapter: DelegateAdapter<*, *>
    )

    /**
     * Binding of [holder] of type [VH] with data [item] and additional update information [payloads]
     *
     * @param item data for binding
     * @param holder holder for binding
     * @param payloads additional information for incremental updates
     * @param position current binding position
     * @param adapter adapter; can be used to get any additional information,
     *                e.g. all items list: [adapter.currentList]
     */
    fun onBindViewHolder(
        item: T,
        holder: VH,
        payloads: List<Any>,
        position: Int,
        adapter: DelegateAdapter<*, *>
    ) {
        onBindViewHolder(item, holder, position, adapter)
    }

    /**
     * Cleanup of [holder] (unbinding from [RecyclerView.ViewHolder.itemView];
     * in this method the cleanup/stopping of any actions related to itemView can be done
     *
     * @param holder the [RecyclerView.ViewHolder] on which the unbinding is done
     * @param adapter adapter; can be used to get any additional information,
     *                e.g. all items list: [adapter.currentList]
     */
    fun onViewRecycled(
        holder: VH,
        adapter: DelegateAdapter<*, *>
    ) {
    }
}

abstract class AbstractDelegateViewHolderBinder<T : Any, VH : RecyclerView.ViewHolder>(
    override val itemType: Class<T>
) : DelegateViewHolderBinder<T, VH>
