package com.example.movieapp

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
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
import java.util.concurrent.TimeoutException
import java.util.regex.Matcher


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
        onView(isRoot()).perform(waitForView(R.id.recyclerViewItemLayout, 5000))
        onView(withId(R.id.movie_list_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        assertThat(navController.currentDestination?.id, equalTo(R.id.detailFragment))
    }



    // Helper function to wait for item in recyclerview
    private fun waitForView(viewId: Int, timeout: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View>? {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id $viewId; during $timeout millis."
            }

            override fun perform(uiController: UiController, rootView: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + timeout
                val viewMatcher = withId(viewId)

                do {
                    // Iterate through all views on the screen and see if the view we are looking for is there already
                    for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }
                    // Loops the main thread for a specified period of time.
                    // Control may not return immediately, instead it'll return after the provided delay has passed and the queue is in an idle state again.
                    uiController.loopMainThreadForAtLeast(100)
                } while (System.currentTimeMillis() <endTime) // in case of a timeout we throw an exception -&gt; test fails
                throw PerformException.Builder()
                    .withCause(TimeoutException())
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(rootView))
                    .build()
            }
        }
    }


}