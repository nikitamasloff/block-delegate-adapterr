interface BlockViewModelProviderStore : BlockKeyMatcherScope {

    fun <C : BlockContract, K : BlockKey<C>> registerBlockViewModelProvider(
        blockKeyMatcher: BlockKeyMatcher<C, K>,
        blockViewModelProvider: BlockViewModelProvider<C, K>
    )

    fun <C : BlockContract, K : BlockKey<C>> getBlockViewModelProvider(
        blockKey: K,
    ): BlockViewModelProvider<C, K>

    fun clear()
}

inline fun <reified C, reified K> BlockViewModelProviderStore.registerBlockViewModelProvider(
    blockKey: K,
    blockViewModelProvider: BlockViewModelProvider<C, K>
) where C : BlockContract,
        K : BlockKey<C> =
    registerBlockViewModelProvider(
        blockKeyMatcher = equalsTo(blockKey),
        blockViewModelProvider
    )

inline fun <reified C, reified K> BlockViewModelProviderStore.registerBlockViewModelProvider(
    crossinline blockKeyMatcher: (key: K) -> Boolean,
    blockViewModelProvider: BlockViewModelProvider<C, K>
) where C : BlockContract,
        K : BlockKey<C> {
    val matcher = blockKeyMatcher(blockKeyMatcher)
    registerBlockViewModelProvider(matcher, blockViewModelProvider)
}
