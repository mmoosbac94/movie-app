package com.example.movieapp

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Test
    fun testNavigationFromOverviewToDetailScreen() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
    }


}