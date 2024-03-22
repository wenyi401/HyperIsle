package art.luaj.hyperisle.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import art.luaj.hyperisle.R
import art.luaj.hyperisle.databinding.FragmentMainNavBinding
import art.luaj.hyperisle.ext.Tools
import art.luaj.hyperisle.ui.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainNavFragment : BaseFragment<FragmentMainNavBinding, ViewModel>(
    FragmentMainNavBinding::inflate,
    null
) {
    override fun initFragment(binding: FragmentMainNavBinding, viewModel: ViewModel?) {
        Tools.showAlert(requireContext(), "Hello World", true)
        (childFragmentManager.findFragmentById(R.id.mainNavView) as NavHostFragment).apply {
            binding.mainNavBottomNav.labelVisibilityMode =
                BottomNavigationView.LABEL_VISIBILITY_SELECTED
            binding.mainNavBottomNav.setupWithNavController(navController)
        }
    }
}