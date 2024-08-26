interface BlockViewModelStore {

    fun putBlockViewModel(
        blockKey: BlockKey<*>,
        blockViewModel: BlockViewModel<*, *>
    )

    fun getBlockViewModel(
        blockKey: BlockKey<*>
    ): BlockViewModel<*, *>?

    fun clear()
}

fun blockViewModelStore(): BlockViewModelStore =
    DefaultBlockViewModelStore()
