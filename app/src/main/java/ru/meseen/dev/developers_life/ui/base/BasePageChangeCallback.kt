package ru.meseen.dev.developers_life.ui.base

import androidx.viewpager2.widget.ViewPager2

/**
 * @author Vyacheslav Doroshenko
 */
abstract class BasePageChangeCallback : ViewPager2.OnPageChangeCallback() {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}