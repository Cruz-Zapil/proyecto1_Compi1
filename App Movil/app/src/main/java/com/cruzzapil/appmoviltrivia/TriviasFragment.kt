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

class TriviasFragment : Fragment (R.layout.fragment_trivias){
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trivias, container, false)
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Simular lista de trivias
        val triviaList = listOf("Trivia 1", "Trivia 2", "Trivia 3", "Trivia 4")

        val listView: ListView = view.findViewById(R.id.triviaListView)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, triviaList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val triviaSelected = triviaList[position]
            Toast.makeText(context, "Seleccionaste: $triviaSelected", Toast.LENGTH_SHORT).show()
        }
    }
}