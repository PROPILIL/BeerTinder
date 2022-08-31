package com.propil.beertinder.data.remote.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class BeerDto(
    @Serializable(with = BeerListSerializer::class)
    val beerItem: List<BeerItem>
)

@Serializable
data class BeerItem(
    @SerialName("id")
    val id: Int, // 192
    @SerialName("name")
    val name: String?, // Punk IPA 2007 - 2010
    @SerialName("tagline")
    val tagline: String?, // Post Modern Classic. Spiky. Tropical. Hoppy.
    @SerialName("first_brewed")
    val firstBrewed: String?, // 04/2007
    @SerialName("description")
    val description: String?, // Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish.
    @SerialName("image_url")
    val imageUrl: String?, // https://images.punkapi.com/v2/192.png
    @SerialName("abv")
    val abv: Double?, // 6.0
    @SerialName("ibu")
    val ibu: Double?, // 60.0
    @SerialName("target_fg")
    val targetFg: Double?, // 1010.0
    @SerialName("target_og")
    val targetOg: Double?, // 1056.0
    @SerialName("ebc")
    val ebc: Double?, // 17.0
    @SerialName("srm")
    val srm: Double?, // 8.5
    @SerialName("ph")
    val ph: Double?, // 4.4
    @SerialName("attenuation_level")
    val attenuationLevel: Double?, // 82.14
    @SerialName("volume")
    val volume: Volume?,
    @SerialName("boil_volume")
    val boilVolume: BoilVolume?,
    @SerialName("method")
    val method: Method?,
    @SerialName("ingredients")
    val ingredients: Ingredients?,
    @SerialName("food_pairing")
    val foodPairing: List<String>?,
    @SerialName("brewers_tips")
    val brewersTips: String?, // While it may surprise you, this version of Punk IPA isn't dry hopped but still packs a punch! To make the best of the aroma hops make sure they are fully submerged and add them just before knock out for an intense hop hit.
    @SerialName("contributed_by")
    val contributedBy: String? // Sam Mason <samjbmason>
)

@Serializable
data class Volume(
    @SerialName("value")
    val value: Int?, // 20
    @SerialName("unit")
    val unit: String? // liters
)

@Serializable
data class BoilVolume(
    @SerialName("value")
    val value: Int?, // 25
    @SerialName("unit")
    val unit: String? // liters
)

@Serializable
data class Method(
    @SerialName("mash_temp")
    val mashTemp: List<MashTemp>?,
    @SerialName("fermentation")
    val fermentation: Fermentation?,
    @SerialName("twist")
    val twist: String?
)

@Serializable
data class Ingredients(
    @SerialName("malt")
    val malt: List<Malt>?,
    @SerialName("hops")
    val hops: List<Hop>?,
    @SerialName("yeast")
    val yeast: String? // Wyeast 1056 - American Ale™
)

@Serializable
data class MashTemp(
    @SerialName("temp")
    val temp: Temp?,
    @SerialName("duration")
    val duration: Int? // 75
)

@Serializable
data class Fermentation(
    @SerialName("temp")
    val temp: TempX?
)

@Serializable
data class Temp(
    @SerialName("value")
    val value: Int?, // 65
    @SerialName("unit")
    val unit: String? // celsius
)

@Serializable
data class TempX(
    @SerialName("value")
    val value: Int?, // 19
    @SerialName("unit")
    val unit: String? // celsius
)

@Serializable
data class Malt(
    @SerialName("name")
    val name: String?, // Extra Pale
    @SerialName("amount")
    val amount: Amount?
)

@Serializable
data class Hop(
    @SerialName("name")
    val name: String?, // Ahtanum
    @SerialName("amount")
    val amount: AmountX?,
    @SerialName("add")
    val add: String?, // start
    @SerialName("attribute")
    val attribute: String? // bitter
)

@Serializable
data class Amount(
    @SerialName("value")
    val value: Double?, // 5.3
    @SerialName("unit")
    val unit: String? // kilograms
)

@Serializable
data class AmountX(
    @SerialName("value")
    val value: Double?, // 17.5
    @SerialName("unit")
    val unit: String? // grams
)