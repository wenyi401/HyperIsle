package art.luaj.hyperisle.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import art.luaj.hyperisle.R
import art.luaj.hyperisle.databinding.FragmentMainNavBinding
import art.luaj.hyperisle.ext.Tools
import art.luaj.hyperisle.ui.base.BaseFragment

class MainNavFragment : BaseFragment<FragmentMainNavBinding, ViewModel>(
    FragmentMainNavBinding::inflate,
    null
) {
    override fun initFragment(binding: FragmentMainNavBinding, viewModel: ViewModel?) {
        Tools.showAlert(requireContext(), "Hello World", true)
        (childFragmentManager.findFragmentById(R.id.mainNavView) as NavHostFragment).apply {
            binding.mainNavBottomNav.setupWithNavController(navController)
        }
    }
}