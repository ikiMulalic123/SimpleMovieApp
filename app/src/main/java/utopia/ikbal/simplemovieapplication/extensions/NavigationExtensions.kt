package utopia.ikbal.simplemovieapplication.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import utopia.ikbal.simplemovieapplication.R

fun Fragment.navigateUp() {
    NavHostFragment.findNavController(this).navigateUp()
}

fun Fragment.navigateTo(@IdRes destinationId: Int) {
    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        .navigate(destinationId,null,null,null)
}
