package utopia.ikbal.simplemovieapplication.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import utopia.ikbal.simplemovieapplication.R

class MovieActorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_cast)
    }



    companion object {
        const val CAST_ID = "cast_id"

        private fun createLaunchIntent(context: Context, castId: Int) =
            Intent(context, MovieActorActivity::class.java).apply {
                putExtra(CAST_ID, castId)
            }

        fun launch(context: Context, castId: Int) =
            context.startActivity(createLaunchIntent(context, castId))
    }
}