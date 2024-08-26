import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CoroutineViewHolderDelegate(
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : CoroutineViewHolder {

    private val baseCoroutineContext: CoroutineContext = coroutineContext
    private var _itemViewScope: CoroutineScope? = null
    override val itemViewScope: CoroutineScope
        get() = _itemViewScope?.takeIf { it.isActive }
            ?: throw IllegalStateException(
                "[CoroutineScope] has not been initialized, " +
                    "i.e. this [ViewHolder] has not been bounded yet " +
                    "or its [itemView] has already been recycled"
            )

    override fun onBind() {
        val localScope = _itemViewScope
        if (localScope == null || !localScope.isActive) {
            _itemViewScope = createItemViewScope(baseCoroutineContext)
        }
    }

    override fun onViewRecycled() {
        _itemViewScope?.cancel()
        _itemViewScope = null
    }

    private fun createItemViewScope(
        baseCoroutineContext: CoroutineContext
    ): CoroutineScope {
        if (baseCoroutineContext is EmptyCoroutineContext) {
            return MainScope()
        }
        val parentJob = baseCoroutineContext[Job]
        val targetCoroutineContext = baseCoroutineContext.minusKey(Job) + Job(parent = parentJob)
        return CoroutineScope(targetCoroutineContext)
    }
}
