# Qwitter - Twitter Clone [Firebase integrated]
**Qwitter** is a Android app built entirely with Kotlin and Jetpack Compose, It
follows the [official architecture guidance](https://developer.android.com/jetpack/guide) as closely as possible.

### Features
*   Registered users can post text and images.
*   Users can also like, comment and bookmark posts.
*   Periodically synchronize with server side.

### Highlights
* **MVVM** architecture.
* * **Firebase** This app integrated with Firebase Auth, Firestore Database, Firebase Storage and Firebase Analytics.
* **Hilt** Dependency injection.
* **WorkManger** executes sync job for keeping data up to date and showing status notification.
* **ConnectivityManager** Monitoring network connections. 

  
## Libraries & Dependencies
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack) - Dependency injection library.
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) - Asynchronous programming.
- [Coil](https://coil-kt.github.io/coil/compose/) - Image loading.
- [Room](https://developer.android.com/training/data-storage/room) - Saving data in a local database.
- [Navigation](https://developer.android.com/guide/navigation) - Navigation component.
- [Google Accompanist](https://github.com/google/accompanist) - Android runtime permissions support.
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Schedule sync task.

## Installation
To install **Qwitter**, follow these steps:
1. Clone or download the project code from the repository.
4. Build and run the app on an Android emulator or device.
