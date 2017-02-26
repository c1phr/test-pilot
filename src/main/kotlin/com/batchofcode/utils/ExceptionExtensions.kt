package com.batchofcode.utils

fun Array<StackTraceElement>.traceString(): String {
    val sb = StringBuilder()
    for (traceElem in this) {
        sb.append(traceElem.toString())
        sb.append("\n")
    }
    return sb.toString()
}