interface BlockKeyMatcher<C : BlockContract, K : BlockKey<C>> {

    val blockContractType: Class<C>

    val blockKeyType: Class<K>

    fun matches(key: K): Boolean
}

inline fun <reified C, reified K> blockKeyMatcher(
    crossinline matcher: (key: K) -> Boolean
): BlockKeyMatcher<C, K>
    where C : BlockContract,
          K : BlockKey<C> =
    object : BlockKeyMatcher<C, K> {
        override val blockContractType: Class<C> = C::class.java
        override val blockKeyType: Class<K> = K::class.java
        override fun matches(key: K): Boolean = matcher.invoke(key)
    }
