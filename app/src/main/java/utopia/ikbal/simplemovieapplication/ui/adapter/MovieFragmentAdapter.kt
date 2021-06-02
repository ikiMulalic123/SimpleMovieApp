package utopia.ikbal.simplemovieapplication.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import utopia.ikbal.simplemovieapplication.data.MovieFragmentType
import utopia.ikbal.simplemovieapplication.ui.movie.MovieFragment

class MovieFragmentAdapter(childFragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(childFragmentManager, lifecycle) {

    override fun getItemCount(): Int = MovieFragmentType.values().size

    override fun createFragment(position: Int): Fragment {
        return MovieFragment.newInstance(MovieFragmentType.values()[position].fragmentResNameId)
    }
}