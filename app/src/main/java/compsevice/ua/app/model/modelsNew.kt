package compsevice.ua.app.model

data class CreditInfo(val uuid: String, val service: ServiceType, val counter: String, val credit: Double)

data class CounterInfo(val uucred: String, val factory: String, val value: Long)

data class ContractInfo2(val uuid: String, val number: String, val owner: String, val sector: Int, val counters: List<CounterInfo>, val credits: List<CreditInfo>)


