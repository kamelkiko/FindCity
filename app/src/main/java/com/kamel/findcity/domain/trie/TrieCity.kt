package com.kamel.findcity.domain.trie

import android.util.Log
import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.util.NotFoundException
import javax.inject.Inject

/** Why Trie?
 * Efficiency: Tries provide an efficient way to search for a string prefix, making the search operations faster than linear search.
 * Prefix Matching: Perfectly suits the requirement of prefix-based searching for city names.
 * Scalability: Handles large datasets (200k+ entries) efficiently without compromising on performance.
 */
class TrieCity @Inject constructor() : Trie<City> {
    private val root = TrieNodeCity()

    /**
     * Inserts a city into the Trie.
     * Each character of the city's name is added as a node in the Trie.
     */
    override suspend fun insert(key: String, value: City) {
        var currentNode = root
        key.lowercase().forEach { char ->
            currentNode = currentNode.children.getOrPut(char) { TrieNodeCity() }
            currentNode.cities.add(value)
        }
        Log.d("KAMELOO", "root : " + root.cities.toString())
        Log.d("KAMELOO", currentNode.cities.toString())
    }


    /**
     * Searches for cities that match the given prefix.
     * Traverses the Trie based on the prefix and returns the list of matching cities.
     */
    override suspend fun search(key: String): List<City> {
        return key.fold(root) { node, char ->
            node.children.getOrElse(char) { throw NotFoundException(NO_RESULT_ERROR) }
        }.cities
    }

    companion object {
        private const val NO_RESULT_ERROR = "Oops, no matches found"
    }
}