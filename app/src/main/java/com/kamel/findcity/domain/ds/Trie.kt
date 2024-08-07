package com.kamel.findcity.domain.ds

interface Trie<Value> {
    fun insert(key: String, value: Value)

    suspend fun search(key: String): List<Value>
}