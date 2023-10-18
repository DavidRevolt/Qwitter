pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Qwitter"
include(":app")
include(":core:designsystem")
include(":feature:login")
include(":feature:home")
include(":core:data")
include(":core:model")
include(":core:network")
