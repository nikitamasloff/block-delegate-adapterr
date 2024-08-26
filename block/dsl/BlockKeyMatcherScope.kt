interface BlockKeyMatcherScope

inline fun <reified C, reified K> BlockKeyMatcherScope.equalsTo(
    blockKey: K
): BlockKeyMatcher<C, K>
    where C : BlockContract, K : BlockKey<C> =
    blockKeyMatcher { key -> key == blockKey }

inline fun <reified C, reified K> BlockKeyMatcherScope.anyOfExactType(): BlockKeyMatcher<C, K>
    where C : BlockContract, K : BlockKey<C> =
    blockKeyMatcher { key ->
        key::class == K::class &&
            key.blockContractType == C::class.java
    }

inline fun <reified C, reified K> BlockKeyMatcherScope.anyOfType(): BlockKeyMatcher<C, K>
    where C : BlockContract, K : BlockKey<C> =
    // always returns true, the type compatibility check is performed before the [BlockKeyMatcher.matches] call
    blockKeyMatcher { true }
