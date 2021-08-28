package ru.meseen.dev.developers_life.data.api


//https://developerslife.ru/latest/1?json=true&pageSize=1&types=gif

enum class SectionType(val selection: String) {
    LATEST("latest"), HOT("hot"), TOP("top")
}
