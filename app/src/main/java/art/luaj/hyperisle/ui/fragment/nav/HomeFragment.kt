package art.luaj.hyperisle.ui.fragment.nav

import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import art.luaj.hyperisle.R
import art.luaj.hyperisle.databinding.FragmentHomeBinding
import art.luaj.hyperisle.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, ViewModel>(
    FragmentHomeBinding::inflate,
    null
) {
    override fun initFragment(binding: FragmentHomeBinding, viewModel: ViewModel?) {
        /*
        binding.button.setOnClickListener {
            requireActivity().findNavController(R.id.mainView)
                .navigate(R.id.action_mainNavFragment_to_startFragment)
        }

         */
    }
}