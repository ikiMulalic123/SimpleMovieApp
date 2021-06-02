package utopia.ikbal.simplemovieapplication.data

import androidx.annotation.StringRes
import utopia.ikbal.simplemovieapplication.R

enum class MovieFragmentType(@StringRes val fragmentResNameId: Int) {
    LATEST(R.string.latest),
    RECENT(R.string.recent),
    POPULAR(R.string.popular)
}