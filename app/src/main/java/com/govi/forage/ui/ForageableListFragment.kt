package com.govi.forage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.govi.forage.BaseApplication
import com.govi.forage.R
import com.govi.forage.databinding.FragmentForageableListBinding
import com.govi.forage.ui.adapter.ForageableListAdapter
import com.govi.forage.ui.viewmodel.ForageableViewModel
import com.govi.forage.ui.viewmodel.ForageableViewModelFactory

/**
 * A fragment to view the list of [Forageable]s stored in the database.
 * Clicking on a [Forageable] list item launches the [ForageableDetailFragment].
 * Clicking the [FloatingActionButton] launched the [AddForageableFragment]
 */
class ForageableListFragment : Fragment() {

    // Refactor the creation of the view model to take an instance of
    //  ForageableViewModelFactory. The factory should take an instance of the Database retrieved
    //  from BaseApplication
    private val viewModel: ForageableViewModel by activityViewModels{
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }

    private var _binding: FragmentForageableListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForageableListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ForageableListAdapter { forageable ->
            val action = ForageableListFragmentDirections
                .actionForageableListFragmentToForageableDetailFragment(forageable.id)
            findNavController().navigate(action)
        }

        // observe the list of forageables from the view model and submit it the adapter
        viewModel.forageables.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            recyclerView.adapter = adapter
            addForageableFab.setOnClickListener {
                findNavController().navigate(
                    R.id.action_forageableListFragment_to_addForageableFragment
                )
            }
        }
    }
}
