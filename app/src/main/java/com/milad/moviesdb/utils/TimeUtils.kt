package com.milad.moviesdb.utils

import java.util.*

fun isNight(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (currentHour <= 7 || currentHour >= 18)
}
fun isCacheExpired(updateAT:Long): Boolean {
    val updateATDate = Date(updateAT)
    val expirationDate = Calendar.getInstance()
    expirationDate.add(Calendar.HOUR,4)
    return updateATDate.before(expirationDate.time)
}
