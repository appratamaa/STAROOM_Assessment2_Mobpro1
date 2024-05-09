package org.d3if3156.staroom.navigation

import org.d3if3156.staroom.ui.screen.KEY_ID_STAR

sealed class Screen(val route: String) {
    data object Splash: Screen("splashscreen")
    data object Home: Screen("mainscreen")
    data object FormStar: Screen("DetailScreen")
    data object FormUbah: Screen("DetailScreen/{$KEY_ID_STAR}") {
        fun withId(id: Long)= "DetailScreen/$id"
    }
    data object Nakostar: Screen("nakostarscreen")
    data object About: Screen("aboutscreen")
    data object Notification: Screen("notificationscreen")
    data object Developer: Screen("developerscreen")
}