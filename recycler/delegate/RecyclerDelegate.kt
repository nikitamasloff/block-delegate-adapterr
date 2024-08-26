import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView

/**
 * Base [RecyclerView] item delegate for data of type [T] and ViewHolder of type [VH]
 *
 * @param T data type of an item
 * @param VH type of [RecyclerView.ViewHolder]
 */
interface RecyclerDelegate<T, VH> :
    DelegateViewHolderCreator<VH>,
    DelegateViewHolderBinder<T, VH>
    where T : Any,
          VH : RecyclerView.ViewHolder {

    /**
     * The compatibility condition of this delegate (this) with data [item];
     * if true, this delegate can process data [item],
     * which matches the condition [isDelegateFor]
     *
     * @param item current item for binding
     */
    fun isDelegateFor(item: T): Boolean = true

    /**
     * Custom [ItemCallback] to compare data of type [T];
     * this [ItemCallback] can provide custom comparison and payloads mechanism
     * for the data of type [T];
     * if none provided, default callback will be used
     */
    val itemDiffCallback: ItemCallback<in T>? get() = null
}

abstract class AbstractRecyclerDelegate<T : Any, VH : RecyclerView.ViewHolder>(
    override val itemType: Class<T>
) : RecyclerDelegate<T, VH>
