package com.kamel.findcity.domain.ds

interface Trie<Value> {
    fun insert(key: String, values: Value)

    fun search(key: String): Value
}