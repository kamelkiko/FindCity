package com.kamel.findcity.domain.usecase

import com.kamel.findcity.domain.ds.TrieCity
import javax.inject.Inject

class SearchCitiesByPrefixUseCase @Inject constructor(
    private val trieCity: TrieCity
) {
    operator fun invoke(prefix: String) = trieCity.search(prefix).sortedBy { it.name }
}