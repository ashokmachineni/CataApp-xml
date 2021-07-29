package com.example.cats.domain.model

import java.io.Serializable

data class CatDetail(
    var adaptability: Int? = 0,
    var affection_level: Int? = 0,
    var bidability: Int? = 0,
    var cat_friendly: Int? = 0,
    var cfa_url: String? = "",
    var child_friendly: Int? = 0,
    var country_code: String? = "",
    var country_codes: String? = "",
    var description: String? = "",
    var dog_friendly: Int? = 0,
    var energy_level: Int? = 0,
    var experimental: Int? = 0,
    var grooming: Int? = 0,
    var hairless: Int? = 0,
    var health_issues: Int? = 0,
    var hypoallergenic: Int? = 0,
    var id: String? = "",
    var indoor: Int? = 0,
    var intelligence: Int? = 0,
    var lap: Int? = 0,
    var life_span: String? = "",
    var name: String? = "",
    var natural: Int? = 0,
    var origin: String? = "",
    var rare: Int? = 0,
    var reference_image_id: String? = "",
    var rex: Int? = 0,
    var shedding_level: Int? = 0,
    var short_legs: Int? = 0,
    var social_needs: Int? = 0,
    var stranger_friendly: Int? = 0,
    var suppressed_tail: Int? = 0,
    var temperament: String? = "",
    var vcahospitals_url: String? = "",
    var vetstreet_url: String? = "",
    var vocalisation: Int? = 0,
    var weight: Weight? = Weight(),
    var wikipedia_url: String? = ""
) : Serializable {
    data class Weight(
        var imperial: String? = "",
        var metric: String? = ""
    ) : Serializable
}
