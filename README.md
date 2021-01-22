# movie-app
Android movie app written in Kotlin with some best practices

This app is using The Movie Database API to show some of the most popular and top rated movies. Furthermore you can search for specific movies. To be able to check out the app quickly, an Api-key is already stored in the project.

The app was built using:

- MVVM (Fragment -> ViewModel((mutable) livedata) -> Repository -> API-Service)
- ViewBinding for accessing views
- Single activity with multiple fragments (NavGraph, NavHostFragment etc.)
- Koin for dependency injection
- Retrofit for API-request + Moshi for parsing
- Glide for loading images
- RecyclerView
- Room in order to store movies locally

- Mockito (e.g. mock repository)
- Navigation Testing (TestNavHostController)
- Espresso combined with Navigation Testing
