package dev.himanshu.inshorts.task.moviedatabaseapp.data.utils

interface Mapper<F, T> {
    fun map(from: F): T
}

fun <T, F> Mapper<F, T>.mapAll(list: List<F>): List<T> = list.map { map(it) }
