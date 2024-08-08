package com.kamel.findcity.domain.ds

import com.kamel.findcity.domain.entity.City

data class TrieNodeCity(
    val children: MutableMap<Char, TrieNodeCity> = mutableMapOf(),
    val cities: MutableList<City> = mutableListOf()
)