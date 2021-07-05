package utopia.ikbal.simplemovieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.MovieFragmentType
import utopia.ikbal.simplemovieapplication.extensions.navigateTo
import utopia.ikbal.simplemovieapplication.ui.adapter.MovieFragmentAdapter
import utopia.ikbal.simplemovieapplication.ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
        initClickListener()
        initDrawer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearInstances()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search -> this.navigateTo(R.id.action_homeFragment_to_searchMovieFragment)
            R.id.img_three_dots -> Toast.makeText(requireContext(),
                getString(R.string.three_dots_clicked),
                Toast.LENGTH_SHORT).show()
            R.id.tv_navigation_rated -> this.navigateTo(R.id.action_homeFragment_to_ratedMoviesFragment)
            R.id.tv_navigation_logout -> this.navigateTo(R.id.action_homeFragment_to_loginActivity)
        }
    }

    private fun clearInstances() {
        tv_navigation_rated.setOnClickListener(null)
        tv_navigation_logout.setOnClickListener(null)
        img_search.setOnClickListener(null)
        img_three_dots.setOnClickListener(null)
        view_pager.adapter = null
    }

    private fun initClickListener() {
        tv_navigation_logout.setOnClickListener(this)
        tv_navigation_rated.setOnClickListener(this)
        img_search.setOnClickListener(this)
        img_three_dots.setOnClickListener(this)
    }

    private fun initTabLayout() {
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = MovieFragmentType.values()[position].name
        }.attach()
    }

    private fun initViewPager() {
        view_pager.adapter = MovieFragmentAdapter(childFragmentManager, lifecycle)
    }

    private fun initDrawer() {
        drawerToggle = setUpDrawerToggle()
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
        drawer_layout.addDrawerListener(drawerToggle)
    }

    private fun setUpDrawerToggle(): ActionBarDrawerToggle =
        ActionBarDrawerToggle(requireActivity(),
            drawer_layout,
            toolbar_movie,
            R.string.drawer_open,
            R.string.drawer_close)
}