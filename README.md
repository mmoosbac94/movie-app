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
- Room in order to store popular and top rated movies locally

- Mockk (e.g. mock repository)
- Navigation Testing (TestNavHostController)
- Espresso combined with Navigation Testing



</br>
</br>

<p float="left">
  <img src="https://user-images.githubusercontent.com/45032075/128868437-7a9e8a6a-9e27-4743-aab1-95037b85d71f.png" width="300" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://user-images.githubusercontent.com/45032075/128868501-2642920b-1d3c-4225-aa5a-634078d47f13.png" width="300" /> 
</p>
