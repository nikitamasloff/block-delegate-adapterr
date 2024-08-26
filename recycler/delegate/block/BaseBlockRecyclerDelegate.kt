import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseBlockRecyclerDelegate<S, E, BVM : BlockViewModel<S, E>, VB : ViewBinding>(
    itemType: Class<BVM>
) : AbstractRecyclerDelegate<BVM, BlockViewHolder<VB>>(itemType) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        layoutResId: Int,
        adapter: DelegateAdapter<*, *>
    ): BlockViewHolder<VB> {
        val binding = inflateViewBinding(inflater, parent)
        return BlockViewHolder(binding)
    }

    protected abstract fun inflateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): VB

    override fun onBindViewHolder(
        item: BVM,
        holder: BlockViewHolder<VB>,
        position: Int,
        adapter: DelegateAdapter<*, *>
    ) {
        holder.onBind()
        holder.collectState(blockViewModel = item) { blockViewModel, state ->
            applyState(blockViewModel, state)
        }
        holder.collectEffect(blockViewModel = item) { blockViewModel, effect ->
            applyEffect(blockViewModel, effect)
        }
    }

    override fun onBindViewHolder(
        item: BVM,
        holder: BlockViewHolder<VB>,
        payloads: List<Any>,
        position: Int,
        adapter: DelegateAdapter<*, *>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(item, holder, position, adapter)
        } else {
            val state = item.state.value
            holder.binding.applyState(blockViewModel = item, state, payloads)
        }
    }

    override fun onViewRecycled(
        holder: BlockViewHolder<VB>,
        adapter: DelegateAdapter<*, *>
    ) {
        holder.onViewRecycled()
    }

    override val itemDiffCallback = BlockDiffCallback()

    protected open fun VB.applyState(
        blockViewModel: BVM,
        state: S
    ) {
    }

    protected open fun VB.applyState(
        blockViewModel: BVM,
        state: S,
        payloads: List<Any>,
    ) {
        applyState(blockViewModel, state)
    }

    protected open fun VB.applyEffect(
        blockViewModel: BVM,
        effect: E
    ) {
    }

    protected open fun BlockViewHolder<VB>.collectState(
        blockViewModel: BVM,
        applyState: VB.(BVM, S) -> Unit
    ) {
        itemView.applyIfTagNotSet(tag = TAG_STATE_COLLECTION) {
            blockViewModel.state
                .onEach { binding.applyState(blockViewModel, it) }
                .launchIn(itemViewScope)
        }
    }

    protected open fun BlockViewHolder<VB>.collectEffect(
        blockViewModel: BVM,
        applyEffect: VB.(BVM, E) -> Unit
    ) {
        itemView.applyIfTagNotSet(tag = TAG_EFFECT_COLLECTION) {
            blockViewModel.effect
                .onEach { binding.applyEffect(blockViewModel, it) }
                .launchIn(itemViewScope)
        }
    }

    companion object {
        protected const val TAG_STATE_COLLECTION = "state_collection"
        protected const val TAG_EFFECT_COLLECTION = "effect_collection"
    }
}
