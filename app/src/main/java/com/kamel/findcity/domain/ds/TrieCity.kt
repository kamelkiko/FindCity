package com.kamel.findcity.domain.ds

import com.kamel.findcity.domain.entity.City
import javax.inject.Inject

/** Why Trie?
 * Efficiency: Tries provide an efficient way to search for a string prefix, making the search operations faster than linear search.
 * Prefix Matching: Perfectly suits the requirement of prefix-based searching for city names.
 * Scalability: Handles large datasets (200k+ entries) efficiently without compromising on performance.
 */

class TrieCity @Inject constructor() : Trie<List<City>> {
    private val root = TrieNodeCity()

    /**
     * Inserts a city into the Trie.
     * Each character of the city's name is added as a node in the Trie.
     */
    override fun insert(key: String, values: List<City>) {
        key.fold(root) { node, char ->
            node.children[char] ?: TrieNodeCity().also { node.children[char] = it }
        }.cities.addAll(values)
    }


    /**
     * Searches for cities that match the given prefix.
     * Traverses the Trie based on the prefix and returns the list of matching cities.
     */
    override fun search(key: String): List<City> {
        return key.lowercase().fold(root) { node, char ->
            node.children[char] ?: return emptyList()
        }.cities
    }
}