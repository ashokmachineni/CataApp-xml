package com.example.cats.presentation.home


enum class CatCategory(val value: String) {
    ABYSSINIAN("Abyssinian"),
    AEGEAN("Aegean"),
    AMERICANBOBTAIL("American Bobtail"),
    AMERICANCURL("American Curl"),
    AMERICANSHORTHAIR("American Shorthair"),
    PERSIAN("Persian"),
    RAGDOLL("Ragdoll"),
    PIZZA("Pizza"),
    DONUT("Donut"),
}

/**
 * This is all static category which we show on Home page
 */
fun getAllCatsCategories(): List<CatCategory> {
    return listOf(
        CatCategory.ABYSSINIAN,
        CatCategory.AEGEAN,
        CatCategory.AMERICANBOBTAIL,
        CatCategory.AMERICANCURL,
        CatCategory.AMERICANSHORTHAIR,
        CatCategory.PERSIAN,
        CatCategory.RAGDOLL
    )
}