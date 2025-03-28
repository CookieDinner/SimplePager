# Simple Pager
[![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![CodeQL](https://github.com/CookieDinner/SimplePager/actions/workflows/codeql.yml/badge.svg)](https://github.com/CookieDinner/SimplePager/actions/workflows/codeql.yml)
[![](https://jitpack.io/v/CookieDinner/SimplePager.svg)](https://jitpack.io/#CookieDinner/SimplePager)
[![Jetpack Compose](https://img.shields.io/badge/Built%20with-Jetpack%20Compose%20%E2%9D%A4%EF%B8%8F-2DA042)](https://developer.android.com/jetpack/compose)

✨ A simple and concise implementation of the dynamic pager for Jetpack Compose with no strings attached ✨

## Gradle setup
Latest release version: [![](https://jitpack.io/v/CookieDinner/SimplePager.svg)](https://jitpack.io/#CookieDinner/SimplePager)

### Version catalog

If you're using Version Catalog, configure the dependency by adding the below lines to your `libs.versions.toml`

```toml
[versions]
simplepager = "<version>"

[libraries]
simplepager = { module = "com.github.CookieDinner:SimplePager", version.ref = "simplepager" }
```

### Gradle

Add the dependency into your **module's** `build.gradle.kts`

```gradle
dependencies {
    implementation("com.github.CookieDinner:SimplePager:$version")

    // Or if you're using the Version Catalog
    implementation(libs.simplepager)
}
```

## Example usage

### 1. Create the Pager instance in your ViewModel and a variable containing the StateFlow returning the paged items

```kotlin
private val pager = Pager(
    config = PagerConfig(
        pageSize = 20,
        prefetchDistance = 20,
        firstPageIndex = 0,
        onlyDistinctItems = true,
        deactivatePagerOnEndReached = true
    ),
    coroutineScope = viewModelScope
) { nextPage, pageSize ->
    // Function returning a Flow<Result<List<T>>>
}

val pagedList = pager.lazyPagingItemsFlow
```

### 2. Create a function that will fetch your paged list

```kotlin
fun example(
    page: Int,
    pageSize: Int
): Flow<Result<List<Item>>> {
    return flow {
        emit(/* Specified page of your list */)
    }.asResult()
}
```

### 3. Observe the paged list and use it in your LazyColumn / LazyRow using the items extension provided by the library

```kotlin
val list by viewModel.pagedList.collectAsStateWithLifecycle()

LazyColumn {
    items(list) {
        //...
    }
}
```

### 4. Begin paging by activating the Pager

```kotlin
pager.resetPager()
```

## Preview Demo

<img height="600px" src="/media/PagerDemo.gif">

## Contributions are very much welcome and appreciated ❤️
