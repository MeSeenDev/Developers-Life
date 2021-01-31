package ru.meseen.dev.tinkofflab_0.model.api.query

interface Query {

    fun getSection(): String
    fun getPage(): Int
    fun getPageSize(): Int
    fun getTypes(): String
}