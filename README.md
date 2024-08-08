# City Finder App

## Overview

The City Finder App is an Android application built with Kotlin and Jetpack Compose. It provides a
responsive and efficient interface to search through a list of cities. The app is designed to
demonstrate problem-solving skills, UX judgement, and code quality.

## Features

- **Efficient City Search**: Utilizes a Trie data structure for optimized search performance.
- **Responsive UI**: Real-time filtering of cities as the user types.
- **Alphabetical Order**: Displays cities in alphabetical order.
- **Google Maps Integration**: Shows city location on Google Maps when a city is tapped.
- **Screen Rotation**: Supports screen rotation without losing the state.
- **Compatibility**: Works with Android 5.+.

Here is a demonstration of the City Finder App:
[![City Finder App Demo](http://img.youtube.com/vi/VmXdaaTZXYs/0.jpg)](http://www.youtube.com/watch?v=VmXdaaTZXYs)

## Technologies Used

- **Kotlin**: Primary language for development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **Hilt**: Dependency injection framework.
- **Kotlin Serialization**: For JSON parsing.

## Project Structure

The project follows a clean architecture pattern, ensuring separation of concerns and making the
codebase more maintainable and testable. The main layers are:

- **Presentation Layer**: Contains UI-related code, including Jetpack Compose composables and
  ViewModels. This layer interacts with the domain layer to display data and handle user
  interactions.
- **Domain Layer**: Contains business logic and use cases. It defines the application's core
  functionality and interacts with the data layer.
- **Data Layer**: Responsible for data management, including fetching data from local storage or
  network sources. In this project, it handles loading city data from a JSON file with repository
  pattern.

## Trie Data Structure

### Why Trie?

A Trie (pronounced as "try") is a tree-like data structure that stores a dynamic set of strings,
where the keys are usually strings. Here are the reasons why Trie was used in this project:

- **Efficiency**: Tries provide an efficient way to search for a string prefix, making the search
  operations faster than linear search.
- **Prefix Matching**: Perfectly suits the requirement of prefix-based searching for city names.
- **Scalability**: Handles large datasets (200k+ entries) efficiently without compromising on
  performance.

### Implementation

The Trie structure was used to preprocess the list of cities. This allows quick retrieval of cities
based on the input prefix, ensuring a responsive UI even with a large dataset.

```kotlin
data class TrieNodeCity(
    val children: MutableMap<Char, TrieNodeCity> = mutableMapOf(),
    val cities: MutableList<City> = mutableListOf()
)

interface Trie<Value> {
    fun insert(key: String, value: Value)

    suspend fun search(key: String): List<Value>
}

class TrieCity @Inject constructor() : Trie<City> {
    private val root = TrieNodeCity()

    /**
     * Inserts a city into the Trie.
     * Each character of the city's name is added as a node in the Trie.
     */
    override fun insert(key: String, value: City) {
        var currentNode = root
        value.name.lowercase().forEach { char ->
            currentNode = currentNode.children.getOrPut(char) { TrieNodeCity() }
            currentNode.cities.add(value)
        }
    }


    /**
     * Searches for cities that match the given prefix.
     * Traverses the Trie based on the prefix and returns the list of matching cities.
     */
    override suspend fun search(key: String): List<City> {
        var currentNode = root
        key.lowercase().forEach { char ->
            currentNode = currentNode.children[char] ?: throw NotFoundException(NO_RESULT_ERROR)
        }
        return currentNode.cities
    }

    companion object {
        private const val NO_RESULT_ERROR = "Oops, no matches found"
    }
}
