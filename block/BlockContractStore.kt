interface BlockContractStore : BlockKeyMatcherScope {

    fun <C : BlockContract, K : BlockKey<C>> registerBlockContract(
        blockKeyMatcher: BlockKeyMatcher<C, K>,
        blockContract: C
    )

    fun <C : BlockContract, K : BlockKey<C>> getBlockContract(
        blockKey: K
    ): C

    fun clear()
}

inline fun <reified C, reified K> BlockContractStore.registerBlockContract(
    blockKey: K,
    blockContract: C
) where C : BlockContract,
        K : BlockKey<C> =
    registerBlockContract(
        blockKeyMatcher = equalsTo(blockKey),
        blockContract
    )

inline fun <reified C, reified K> BlockContractStore.registerBlockContract(
    crossinline blockKeyMatcher: (key: K) -> Boolean,
    blockContract: C
) where C : BlockContract,
        K : BlockKey<C> =
    registerBlockContract(
        blockKeyMatcher = blockKeyMatcher(blockKeyMatcher),
        blockContract
    )
