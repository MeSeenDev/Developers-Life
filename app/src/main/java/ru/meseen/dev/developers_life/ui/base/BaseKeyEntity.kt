@file:Suppress("PropertyName")
package ru.meseen.dev.developers_life.ui.base

/**
 * @author Vyacheslav Doroshenko
 */
interface BaseKeyEntity {
    val _id: Long?
    val prevPage: Int?
    val nextPage: Int?
}