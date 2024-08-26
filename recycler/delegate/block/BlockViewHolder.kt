import androidx.viewbinding.ViewBinding
import com.guavapay.crypto.common.base.recycler.delegate.coroutine.CoroutineViewHolder
import com.guavapay.crypto.common.base.recycler.delegate.coroutine.CoroutineViewHolderDelegate
import com.guavapay.crypto.common.base.recycler.delegate.viewbinding.ViewBindingViewHolder
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class BlockViewHolder<VB : ViewBinding>(
    binding: VB,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : ViewBindingViewHolder<VB>(binding),
    CoroutineViewHolder by CoroutineViewHolderDelegate(coroutineContext)
