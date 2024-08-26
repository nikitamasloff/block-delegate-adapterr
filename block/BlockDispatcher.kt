interface BlockDispatcher :
    BlockContractStore,
    BlockViewModelProviderStore,
    BlockViewModelStore,
    BlockDispatcherScope {

    fun <C : BlockContract, K : BlockKey<C>> getOrCreateBlockViewModel(
        blockKey: K
    ): BlockViewModel<*, *>

    fun <C : BlockContract, K : BlockKey<C>> getOrCreateBlockViewModel(
        blockKey: K,
        blockViewModelProvider: BlockViewModelProvider<C, K>
    ): BlockViewModel<*, *>
}

fun blockDispatcher(
    blockViewModelStore: BlockViewModelStore = blockViewModelStore(),
    init: BlockDispatcher.() -> Unit = {}
): BlockDispatcher =
    DefaultBlockDispatcher(blockViewModelStore).apply(init)
