fun interface BlockViewModelProvider<C : BlockContract, K : BlockKey<C>> {

    fun C.get(blockKey: K): BlockViewModel<*, *>
}
