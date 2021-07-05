package utopia.ikbal.simplemovieapplication.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import utopia.ikbal.simplemovieapplication.util.NetworkResultProcessor
import utopia.ikbal.simplemovieapplication.util.ToastUtil

open class BaseFragment : Fragment(), NetworkResultProcessor {
    override fun showGenericError(message: String) {
        ToastUtil.showShortToast(requireContext(),message)
    }
}