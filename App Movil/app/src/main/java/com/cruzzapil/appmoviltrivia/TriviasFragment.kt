package com.cruzzapil.appmoviltrivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import model.Trivia

class TriviasFragment : Fragment(R.layout.fragment_trivias) {

    companion object {
        private const val ARG_TRIVIA_LIST = "trivia_list"

        fun newInstance(triviaList: List<Trivia>): TriviasFragment {
            val fragment = TriviasFragment()
            val args = Bundle()
            args.putSerializable(ARG_TRIVIA_LIST, ArrayList(triviaList))
            fragment.arguments = args
            return fragment
        }
    }

    private var triviaList: List<Trivia>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            triviaList = it.getSerializable(ARG_TRIVIA_LIST) as? List<Trivia>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trivias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.triviaListView)

        // Extraer los nombres de las trivias
        val triviaNames = triviaList?.map { it.NOMBRE } ?: emptyList()

        // Mostrar los nombres en el ListView
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, triviaNames)
        listView.adapter = adapter


        listView.setOnItemClickListener { _, _, position, _ ->
            val triviaSelected = triviaList?.get(position)
            triviaSelected?.let {
                // Aquí rediriges al fragmento de detalles
                val fragment = TriviaDetailFragment.newInstance(it)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null) // Permite volver a la lista de trivias
                    .commit()
            }
        }

    }

    }
