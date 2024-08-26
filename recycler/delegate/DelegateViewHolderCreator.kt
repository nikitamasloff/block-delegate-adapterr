import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

interface DelegateViewHolderCreator<VH : RecyclerView.ViewHolder> {

    /**
     * Creation of [RecyclerView.ViewHolder] of type VH
     *
     * @param parent container [ViewGroup] for creation
     * @param layoutResId layout resource identifier
     * @param adapter adapter; can be used to get any additional information,
     *                e.g. all items list: [adapter.currentList]
     */
    fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        @LayoutRes layoutResId: Int,
        adapter: DelegateAdapter<*, *>
    ): VH
}

abstract class AbstractDelegateViewHolderCreator<VH : RecyclerView.ViewHolder> :
    DelegateViewHolderCreator<VH>
