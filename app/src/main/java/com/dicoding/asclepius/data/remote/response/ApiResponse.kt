package com.dicoding.asclepius.data.remote.response

data class ApiResponse(
	val totalResults: Int,
	val articles: List<ArticlesItem>,
	val status: String
)

data class ArticlesItem(
	val publishedAt: String,
	val author: String,
	val urlToImage: String?,
	val description: String?,
	val source: Source,
	val title: String,
	val url: String,
	val content: String?
)

data class Source(
	val name: String,
	val id: String
)

