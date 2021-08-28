package ru.meseen.dev.developers_life.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.meseen.dev.developers_life.data.api.pojos.DevLifeResponse

interface DevLifeService {


    /**
     * @param section Разделы могут быть
     * latest - последние
     * hot - горячие
     * top - лучшие
     * @param page Номер страницы с элементами
     * @param pageSize Количесто элементов на странице мин 5 макс 50
     * @param types Тип Контента поддерфивается только gif
     */
    @GET("{section}/{page}")
    suspend fun loadData(
        @Path("section") section: String,
        @Path("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = 5,
        @Query("types") types: String = "gif"
    ): DevLifeResponse
}