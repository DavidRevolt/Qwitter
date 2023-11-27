# Qwitter - Twitter Clone [Firebase integrated]
**Qwitter** is a Android app built entirely with Kotlin and Jetpack Compose, It
follows the [official architecture guidance](https://developer.android.com/jetpack/guide) as closely as possible.
**THIS APP IS A WORK IN PROGRESS**

### Features
*   Registered users can post text and images.
*   Users can also like, comment and bookmark posts.
*   Periodically synchronize with server side.

### Highlights
* **MVVM** architecture.
* **Firebase** This app integrated with Firebase Auth, Cloud Firestore, Firebase Storage and Firebase Analytics.
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

## Setup Requirement
To install **Qwitter**, follow these steps:
1. Clone or download the project code from the repository.
2. Create Firebase project [don't download the google-services.json file!].
3. In Firebase console, enable: Authentication[with Google and SHA-1], Cloud Firestore and Firebase Storage.
   [rules and how to add SHA-1](https://github.com/DavidRevolt/Qwitter/blob/master/docs/SetupRequirementGuides.md)
5. Download and place google-services.json file in App module.
6. Build and run the app on an Android emulator or device.
