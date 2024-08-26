interface Block<K, VM>
    where K : BlockKey,
          VM : BlockViewModel<*, *> {
    val blockKey: K
    val blockViewModel: VM
    val isVisible: Boolean get() = true
}
