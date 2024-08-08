package com.kamel.findcity.domain.ds

interface Trie<Value> {
    suspend fun insert(key: String, value: Value)

    suspend fun search(key: String): List<Value>
}