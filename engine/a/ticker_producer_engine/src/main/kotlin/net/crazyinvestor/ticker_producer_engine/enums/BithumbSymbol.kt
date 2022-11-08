package net.crazyinvestor.ticker_producer_engine.enums

enum class BithumbSymbol(val type: String) {
    BTC_KRW("BTC_KRW"),
    ETH_KRW("ETH_KRW")
    ;
}

// TODO
val HARD_CODED_SYMBOLS = listOf(
    "BTC", "ETH", "ETC", "XRP", "BCH",
    "QTUM", "BTG", "EOS", "ICX", "TRX", "ELF", "OMG", "KNC", "GLM", "ZIL", "WAXP", "POWR",
    "LRC", "STEEM", "STRAX", "ZRX", "REP", "SNT", "ADA", "CTXC", "BAT", "THETA", "LOOM",
    "WAVES", "LINK", "ENJ", "VET", "MTL", "IOST", "QKC", "ATOLO", "AMO", "BSV", "ORBS",
    "TFUEL", "VALOR", "CON", "ANKR", "MIX", "CRO", "FX", "CHR", "MBL", "MXC", "FCT2", "TRV",
    "WOM", "SOC", "BOA", "MEV", "SXP", "COS", "EL", "BASIC", "HIVE", "XPR", "VRA", "FIT", "EGG",
    "BORA", "ARPA", "CTC", "APM", "CKB", "AERGO", "CENNZ", "EVZ", "CYCLUB", "SRM", "QTCON", "UNI",
    "YFI", "UMA", "AAVE", "COMP", "REN", "BAL", "RSR", "NMR", "RLC", "UOS", "SAND", "GOM2", "BEL",
    "OBSR", "ORC", "POLA", "AWO", "ADP", "DVI", "GHX", "MVC", "BLY", "WOZX", "ANV", "GRT", "BIOT", "XNO",
    "SNX", "SOFI", "COLA", "OXT", "LINA", "MAP", "AQT", "PLA", "WIKEN", "CTSI", "MANA", "LPT", "MKR", "SUSHI",
    "ASM", "PUNDIX", "CELR", "ARW", "FRONT", "RLY", "OCEAN", "BFC", "ALICE", "OGN", "COTI", "CAKE", "BNT", "XVS",
    "SWAP", "CHZ", "AXS", "DAO", "SIX", "DAI", "MATIC", "WOO", "ACH", "VELO", "XLM", "WICC", "ONT", "META", "KLAY",
    "ONG", "ALGO", "JST", "XTZ", "MLK", "WEMIX", "DOT", "ATOM", "SSX", "TEMCO", "HIBS", "DOGE", "KSM", "CTK", "XYM", "BNB",
    "NFT", "SUN", "XEC", "PCI", "SOL", "EGLD", "GO", "DFA", "C98", "MED", "1INCH", "BOBA", "GALA", "BTT", "EFI", "JASMY",
    "TITAN", "REQ", "CSPR", "AVAX", "TDROP", "SPRT", "NPT", "REI", "T", "MBX", "GMT", "TAVA", "XCN", "GXA", "date",
)