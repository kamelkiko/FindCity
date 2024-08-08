package com.kamel.findcity.domain.trie

interface Trie<Value> {
    suspend fun insert(key: String, value: Value)

    suspend fun search(key: String): List<Value>
}