import java.util.concurrent.CopyOnWriteArrayList

open class DefaultBlockDispatcher internal constructor(
    private val blockViewModelStore: BlockViewModelStore,
) : BlockDispatcher,
    BlockViewModelStore by blockViewModelStore {

    protected open val blockContracts: MutableList<BlockContractEntry<*, *>> = CopyOnWriteArrayList()
    protected open val blockViewModelProviders: MutableList<BlockViewModelProviderEntry<*, *>> = CopyOnWriteArrayList()

    // [BlockDispatcher] region

    override fun <C : BlockContract, K : BlockKey<C>> getOrCreateBlockViewModel(
        blockKey: K,
        blockViewModelProvider: BlockViewModelProvider<C, K>,
    ): BlockViewModel<*, *> {
        val storedViewModel = getBlockViewModel(blockKey)
        if (storedViewModel != null) {
            return storedViewModel
        }
        val contract = getBlockContract(blockKey)
        val viewModel = with(blockViewModelProvider) { contract.get(blockKey) }
        putBlockViewModel(blockKey, viewModel)
        return viewModel
    }

    override fun <C : BlockContract, K : BlockKey<C>> getOrCreateBlockViewModel(
        blockKey: K,
    ): BlockViewModel<*, *> {
        val provider = getBlockViewModelProvider(blockKey)
        return getOrCreateBlockViewModel(blockKey, provider)
    }

    // end of [BlockDispatcher] region

    // [BlockContractStore] region

    override fun <C : BlockContract, K : BlockKey<C>> registerBlockContract(
        blockKeyMatcher: BlockKeyMatcher<C, K>,
        blockContract: C,
    ) {
        val entry = BlockContractEntry(blockKeyMatcher, blockContract)
        blockContracts.add(entry)
    }

    override fun <C : BlockContract, K : BlockKey<C>> getBlockContract(
        blockKey: K,
    ): C {
        @Suppress("UNCHECKED_CAST")
        val entry = blockContracts.find { (matcher, _) ->
            if (matcher.blockKeyType.isAssignableFrom(blockKey::class.java) &&
                matcher.blockContractType.isAssignableFrom(blockKey.blockContractType)
            ) {
                (matcher as BlockKeyMatcher<C, K>).matches(blockKey)
            } else {
                false
            }
        } as BlockContractEntry<C, K>?
            ?: blockContractNotFoundError(blockKey)
        return entry.blockContract
    }

    protected data class BlockContractEntry<C : BlockContract, K : BlockKey<C>>(
        val blockKeyMatcher: BlockKeyMatcher<C, in @UnsafeVariance K>,
        val blockContract: C,
    )

    private fun blockContractNotFoundError(key: BlockKey<*>): Nothing =
        error("A [BlockContract] by the given key [$key] was not found")

    // end of [BlockContractStore] region

    // [BlockViewModelProviderStore] region

    override fun <C : BlockContract, K : BlockKey<C>> registerBlockViewModelProvider(
        blockKeyMatcher: BlockKeyMatcher<C, K>,
        blockViewModelProvider: BlockViewModelProvider<C, K>,
    ) {
        val entry = BlockViewModelProviderEntry(blockKeyMatcher, blockViewModelProvider)
        blockViewModelProviders.add(entry)
    }

    override fun <C : BlockContract, K : BlockKey<C>> getBlockViewModelProvider(
        blockKey: K,
    ): BlockViewModelProvider<C, K> {
        @Suppress("UNCHECKED_CAST")
        val entry = blockViewModelProviders.find { (matcher, _) ->
            if (matcher.blockKeyType.isAssignableFrom(blockKey::class.java) &&
                matcher.blockContractType.isAssignableFrom(blockKey.blockContractType)
            ) {
                (matcher as BlockKeyMatcher<C, K>).matches(blockKey)
            } else {
                false
            }
        } as BlockViewModelProviderEntry<C, K>?
            ?: blockViewModelProviderNotFoundError(blockKey)
        return entry.blockViewModelProvider
    }

    protected data class BlockViewModelProviderEntry<C : BlockContract, K : BlockKey<C>>(
        val blockKeyMatcher: BlockKeyMatcher<C, in @UnsafeVariance K>,
        val blockViewModelProvider: BlockViewModelProvider<C, K>,
    )

    private fun blockViewModelProviderNotFoundError(key: BlockKey<*>): Nothing =
        error("A block ViewModel provider by the given key [$key] was not found")

    // end of [BlockViewModelProviderStore] region

    // COMMON region

    override fun clear() {
        blockViewModelStore.clear() // delegated
        blockContracts.clear()
        blockViewModelProviders.clear()
    }

    // end of COMMON region
}
