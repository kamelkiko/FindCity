package com.kamel.findcity.domain.trie

import com.kamel.findcity.domain.entity.City

data class TrieNodeCity(
    val children: MutableMap<Char, TrieNodeCity> = mutableMapOf(),
    val cities: MutableList<City> = mutableListOf()
)