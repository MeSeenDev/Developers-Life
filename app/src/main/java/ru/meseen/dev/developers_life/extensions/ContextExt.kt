package ru.meseen.dev.developers_life.extensions

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

/**
 * @author Vyacheslav Doroshenko
 */


fun Context.getResourceDrawable(@DrawableRes id: Int, theme: Resources.Theme? = null) =
    ResourcesCompat.getDrawable(this.resources, id, theme)
