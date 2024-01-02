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

rootProject.name = "Movie"
include(":app")

include(":core")
include(":core:designsystem")
include(":core:utils")
include(":core:widget")
include(":core:common")

include(":feature")
include(":feature:auth-impl")
include(":feature:auth-api")
include(":feature:profile-api")
include(":feature:profile-impl")
include(":feature:detail-impl")
include(":feature:detail-api")
include(":feature:home-api")
include(":feature:home-impl")
include(":feature:favorite-api")
include(":feature:favorite-impl")
