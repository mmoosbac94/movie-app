package com.example.movieapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movieapp.overview.OverviewFragment
import com.example.movieapp.repositories.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class NavigationTest : KoinTest {

    private lateinit var navController: TestNavHostController

    private lateinit var mockMoviesRepository: MoviesRepository

    @Before
    fun initialize(): Unit = runBlocking {

        mockMoviesRepository = Mockito.mock(MoviesRepository::class.java)

        Mockito.`when`(mockMoviesRepository.getPopularMovies())
            .thenReturn(TestDataInstrumental.listMovieProperties)

        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        navController.setGraph(R.navigation.navigation)

        loadKoinModules(module {
            single(override = true) { mockMoviesRepository }
        })

        val overViewScenario = launchFragmentInContainer<OverviewFragment>()

        overViewScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
    }

    @Test
    fun testNavigationFromOverviewToSearchScreen() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().context)
        onView(withText(R.string.search_title)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id, equalTo(R.id.searchFragment))
    }

    @Test
    fun testNavigationFromRecyclerViewItemToDetailScreen() {
        // ANOTHER OPTION TO WAIT FOR ELEMENT IN RECYCLERVIEW?
        Thread.sleep(2000)
        onView(withId(R.id.movie_list_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        assertThat(navController.currentDestination?.id, equalTo(R.id.detailFragment))
    }


}