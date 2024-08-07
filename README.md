# City Finder App

## Overview

The City Finder App is an Android application built with Kotlin and Jetpack Compose. It provides a responsive and efficient interface to search through a list of cities. The app is designed to demonstrate problem-solving skills, UX judgement, and code quality. 

## Features

- **Efficient City Search**: Utilizes a Trie data structure for optimized search performance.
- **Responsive UI**: Real-time filtering of cities as the user types.
- **Alphabetical Order**: Displays cities in alphabetical order.
- **Google Maps Integration**: Shows city location on Google Maps when a city is tapped.
- **Screen Rotation**: Supports screen rotation without losing the state.
- **Compatibility**: Works with Android 5.+.

## Technologies Used

- **Kotlin**: Primary language for development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **Hilt**: Dependency injection framework.
- **Kotlin Serialization**: For JSON parsing.

## Project Structure

The project follows a clean architecture pattern, ensuring separation of concerns and making the codebase more maintainable and testable. The main layers are:

- **Presentation Layer**: Contains UI-related code, including Jetpack Compose composables and ViewModels. This layer interacts with the domain layer to display data and handle user interactions.
- **Domain Layer**: Contains business logic and use cases. It defines the application's core functionality and interacts with the data layer.
- **Data Layer**: Responsible for data management, including fetching data from local storage or network sources. In this project, it handles loading city data from a JSON file.

## Trie Data Structure

### Why Trie?

A Trie (pronounced as "try") is a tree-like data structure that stores a dynamic set of strings, where the keys are usually strings. Here are the reasons why Trie was used in this project:

- **Efficiency**: Tries provide an efficient way to search for a string prefix, making the search operations faster than linear search.
- **Prefix Matching**: Perfectly suits the requirement of prefix-based searching for city names.
- **Scalability**: Handles large datasets (200k+ entries) efficiently without compromising on performance.

### Implementation

The Trie structure was used to preprocess the list of cities. This allows quick retrieval of cities based on the input prefix, ensuring a responsive UI even with a large dataset.

```kotlin
class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
    var city: City? = null
}

class Trie {
    private val root = TrieNode()

    fun insert(city: City) {
        var currentNode = root
        city.name.forEach { char ->
            currentNode = currentNode.children.computeIfAbsent(char) { TrieNode() }
        }
        currentNode.isEndOfWord = true
        currentNode.city = city
    }

    fun search(prefix: String): List<City> {
        var currentNode = root
        prefix.forEach { char ->
            currentNode = currentNode.children[char] ?: return emptyList()
        }
        return collectAllCities(currentNode)
    }

    private fun collectAllCities(node: TrieNode): List<City> {
        val result = mutableListOf<City>()
        if (node.isEndOfWord) {
            node.city?.let { result.add(it) }
        }
        node.children.values.forEach {
            result.addAll(collectAllCities(it))
        }
        return result
    }
}
