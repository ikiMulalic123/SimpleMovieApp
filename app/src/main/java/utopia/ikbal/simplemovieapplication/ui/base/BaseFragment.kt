package utopia.ikbal.simplemovieapplication.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import utopia.ikbal.simplemovieapplication.util.NetworkResultProcessor

open class BaseFragment : Fragment(), NetworkResultProcessor {
    override fun showGenericError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}