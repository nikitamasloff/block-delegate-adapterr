interface BlockKey<C : BlockContract> {

    val blockContractType: Class<C>
}

abstract class BaseBlockKey<C : BlockContract>(
    override val blockContractType: Class<C>
) : BlockKey<C>
