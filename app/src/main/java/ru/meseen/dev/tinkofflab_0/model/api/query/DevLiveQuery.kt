package ru.meseen.dev.tinkofflab_0.model.api.query

import ru.meseen.dev.tinkofflab_0.model.api.DevApi.SectionType

class DevLiveQuery(
    private val sectionQuery: SectionType,
    private val pageQuery: Int,
    private val pageSizeQuery: Int,
    private val typesQuery: String
) : Query {
    override fun getSection(): String = sectionQuery.selection

    override fun getPage(): Int = pageQuery

    override fun getPageSize(): Int = pageSizeQuery

    override fun getTypes(): String = typesQuery
}