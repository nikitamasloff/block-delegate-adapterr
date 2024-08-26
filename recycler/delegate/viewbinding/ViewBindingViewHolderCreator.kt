import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface ViewBindingViewHolderCreator<VB : ViewBinding> :
    DelegateViewHolderCreator<ViewBindingViewHolder<VB>> {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        layoutResId: Int,
        adapter: DelegateAdapter<*, *>
    ): ViewBindingViewHolder<VB> {
        val binding = inflateViewBinding(inflater, parent)
        return ViewBindingViewHolder(binding)
    }

    fun inflateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): VB
}

abstract class AbstractViewBindingViewHolderCreator<VB : ViewBinding> :
    ViewBindingViewHolderCreator<VB>

inline fun <reified VB : ViewBinding> viewBindingViewHolderCreator(
    crossinline inflate: (inflater: LayoutInflater, parent: ViewGroup) -> VB
): ViewBindingViewHolderCreator<VB> =
    object : AbstractViewBindingViewHolderCreator<VB>() {
        override fun inflateViewBinding(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): VB = inflate(inflater, parent)
    }
