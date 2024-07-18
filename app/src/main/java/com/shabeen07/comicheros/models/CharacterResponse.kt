package com.shabeen07.comicheros.models

data class CharacterResponse(
	val results: List<CharacterItem>? = null,
	val info: Info? = null
)

data class Info(
	val next: String? = null,
	val pages: Int? = null,
	val prev: Any? = null,
	val count: Int? = null
)

data class Origin(
	val name: String? = null,
	val url: String? = null
)

data class CharacterItem(
	val image: String? = null,
	val gender: String? = null,
	val species: String? = null,
	val created: String? = null,
	val origin: Origin? = null,
	val name: String? = null,
	val location: Location? = null,
	val episode: List<String?>? = null,
	val id: Int,
	val type: String? = null,
	val url: String? = null,
	val status: String? = null
)

data class Location(
	val name: String? = null,
	val url: String? = null
)

