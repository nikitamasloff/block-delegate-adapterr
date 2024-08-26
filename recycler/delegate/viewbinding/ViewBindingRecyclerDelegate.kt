import androidx.viewbinding.ViewBinding

interface ViewBindingRecyclerDelegate<T : Any, VB : ViewBinding> :
    RecyclerDelegate<T, ViewBindingViewHolder<VB>>,
    ViewBindingViewHolderCreator<VB>

abstract class AbstractViewBindingRecyclerDelegate<T : Any, VB : ViewBinding>(
    override val itemType: Class<T>
) : ViewBindingRecyclerDelegate<T, VB>
