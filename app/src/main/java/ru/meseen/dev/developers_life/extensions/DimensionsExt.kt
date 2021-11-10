package ru.meseen.dev.developers_life.extensions

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.ceil

/**
 *  Трансформации относительных значений
 * @author Vyacheslav Doroshenko
 */

val Int.dp: Int
    get() = ceil(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    ).toInt()





val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.sp: Float
    get() = ceil(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    )

val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )



/**
 * [sec] секунды в миллисекунды
 */
val Long.sec: Long
    get() = this.times(1000)

val Int.sec: Long
    get() = (this.times(1000)).toLong()

/**
 * [min] минуты в миллисекунды
 */
val Long.min: Long
    get() = this.times(60000)

val Int.min: Long
    get() = (this.times(60000)).toLong()

/**
 * [hour] часы в миллисекунды
 */
val Long.hour: Long
    get() = this.times(3600000)

val Int.hour: Long
    get() = (this.times(3600000)).toLong()
