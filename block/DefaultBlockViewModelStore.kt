import java.util.concurrent.ConcurrentHashMap

open class DefaultBlockViewModelStore internal constructor() : BlockViewModelStore {

    protected open val blockViewModels: MutableMap<BlockKey<*>, BlockViewModel<*, *>> = ConcurrentHashMap()

    override fun putBlockViewModel(
        blockKey: BlockKey<*>,
        blockViewModel: BlockViewModel<*, *>
    ) {
        blockViewModels[blockKey] = blockViewModel
    }

    override fun getBlockViewModel(blockKey: BlockKey<*>): BlockViewModel<*, *>? =
        blockViewModels[blockKey]

    override fun clear() {
        blockViewModels.values.forEach { blockViewModel ->
            blockViewModel.onClearedInternal()
        }
        blockViewModels.clear()
    }
}
