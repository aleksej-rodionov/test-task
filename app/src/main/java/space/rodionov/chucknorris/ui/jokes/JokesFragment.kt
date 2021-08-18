package space.rodionov.chucknorris.ui.jokes

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import space.rodionov.chucknorris.R
import space.rodionov.chucknorris.databinding.FragmentJokesBinding

@AndroidEntryPoint
class JokesFragment : Fragment(R.layout.fragment_jokes) {

    private val viewModel: JokesViewModel by viewModels()

    private var _binding: FragmentJokesBinding? = null
    private val binding get() = _binding!!

    lateinit var jokesAdapter: JokesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJokesBinding.bind(view)

        jokesAdapter = JokesAdapter()

        binding.apply {
            etCount.setText(viewModel.countValue.toString())

            btnReload.setOnClickListener {
                if (it.toString().isNotBlank()) {
                    viewModel.getJokes(etCount.text.toString().toInt())
                }
            }

            recyclerView.apply {
                adapter = jokesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.jokesFlow.collect {
                    val jokes = it ?: return@collect
                    jokesAdapter.submitList(jokes)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





