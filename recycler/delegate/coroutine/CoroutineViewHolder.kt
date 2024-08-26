import kotlinx.coroutines.CoroutineScope

interface CoroutineViewHolder : ExtendedViewHolder {

    val itemViewScope: CoroutineScope
}
