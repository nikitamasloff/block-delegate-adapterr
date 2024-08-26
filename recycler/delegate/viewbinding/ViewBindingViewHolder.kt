import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class ViewBindingViewHolder<VB : ViewBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root)
