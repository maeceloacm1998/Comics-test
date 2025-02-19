package com.example.comics.data.models

data class ComicsModel(
    val data: DataModel
)

data class DataModel(
    val results: List<ResultModel>
)

data class ResultModel(
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailModel
)

data class ThumbnailModel(
    val path: String,
    val extension: String,
)

val List<ResultModel>.asComicsItemModel: List<ComicsItemModel>
    get() = map {
        ComicsItemModel(
            image = "${it.thumbnail.path}.${it.thumbnail.extension}",
            title = it.title,
            subtitle = it.description ?: "Sem descricao"
        )
    }