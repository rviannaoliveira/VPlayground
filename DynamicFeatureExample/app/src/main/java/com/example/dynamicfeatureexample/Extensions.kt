package com.example.dynamicfeatureexample

import java.math.BigDecimal
import java.math.RoundingMode

fun Long.byteToMega(): Double {
    val kByte = this / 1024
    val mega = kByte.toDouble() / 1024.toDouble()
    return mega.roundHalfUp(2)
}

fun Double.roundHalfUp(places: Int): Double {
    require(places >= 0)
    var bd = BigDecimal(this)
    bd = bd.setScale(places, RoundingMode.HALF_UP)
    return bd.toDouble()
}
