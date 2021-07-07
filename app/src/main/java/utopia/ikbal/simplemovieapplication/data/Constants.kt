package utopia.ikbal.simplemovieapplication.data

class Constants {
    companion object {
        private const val BASE_URI = "https://api.themoviedb.org/3/"

        const val BASE_IMAGE_URL = "https://www.themoviedb.org/t/p/w440_and_h660_face"
        const val BASE_ORIGINAL_IMAGE_URL = "https://www.themoviedb.org/t/p/original"
        const val BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v="
        const val BASE_URL = "${BASE_URI}discover/"
        const val BASE_SEARCH_URL = "${BASE_URI}search/"
        const val BASE_DETAILS_MOVIE_URL = BASE_URI
        const val BASE_TOKEN_URL = "${BASE_URI}authentication/"
    }
}