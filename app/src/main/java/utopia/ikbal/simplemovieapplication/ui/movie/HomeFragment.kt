package utopia.ikbal.simplemovieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.MovieFragmentType
import utopia.ikbal.simplemovieapplication.extensions.navigateTo
import utopia.ikbal.simplemovieapplication.ui.adapter.MovieFragmentAdapter
import utopia.ikbal.simplemovieapplication.ui.base.BaseFragment

class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var adapter: MovieFragmentAdapter

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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_nav_menu -> Toast.makeText(requireContext(),
                "You clicked on navigation",
                Toast.LENGTH_SHORT).show()
            R.id.img_search -> this.navigateTo(R.id.action_homeFragment_to_searchMovieFragment)
            R.id.img_three_dots -> Toast.makeText(requireContext(),
                "You clicked on three dots",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun initClickListener() {
        img_nav_menu.setOnClickListener(this)
        img_search.setOnClickListener(this)
        img_three_dots.setOnClickListener(this)
    }

    private fun initTabLayout() {
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = MovieFragmentType.values()[position].name
        }.attach()
    }

    private fun initViewPager() {
        adapter = MovieFragmentAdapter(childFragmentManager, lifecycle)
        view_pager.adapter = adapter
    }
}