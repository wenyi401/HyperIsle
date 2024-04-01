package art.luaj.hyperisle.ui.fragment.start

import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import art.luaj.hyperisle.R
import art.luaj.hyperisle.databinding.FragmentStartBinding
import art.luaj.hyperisle.ext.Tools
import art.luaj.hyperisle.ui.base.BaseFragment

class StartFragment : BaseFragment<FragmentStartBinding, ViewModel>(
    FragmentStartBinding::inflate,
    null
) {
    override fun initFragment(binding: FragmentStartBinding, viewModel: ViewModel?) {

        binding.appGrantRoot.setOnClickListener {
            Tools.exec("su", false)

            findNavController().navigate(
                R.id.mainNavFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.startFragment, true).build()
            )

        }
    }
}