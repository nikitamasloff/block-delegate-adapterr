import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.guavapay.common.base.BaseViewModel

class BlockDiffCallback : ItemCallback<BaseViewModel<*, *>>() {

    override fun areItemsTheSame(
        oldItem: BaseViewModel<*, *>,
        newItem: BaseViewModel<*, *>
    ): Boolean =
        oldItem.javaClass == newItem.javaClass

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: BaseViewModel<*, *>,
        newItem: BaseViewModel<*, *>
    ): Boolean =
        oldItem.state.value == newItem.state.value
}
