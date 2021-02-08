package ru.meseen.dev.tinkofflab_0.model.api.query

class DevLiveQuery(
    private val sectionQuery: String,
    private val pageQuery: Int,
    private val pageSizeQuery: Int,
    private val typesQuery: String
) : Query {
    override fun getSection(): String = sectionQuery

    override fun getPage(): Int = pageQuery

    override fun getPageSize(): Int = pageSizeQuery

    override fun getTypes(): String = typesQuery
}