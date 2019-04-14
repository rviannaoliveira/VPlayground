package com.rviannaoliveira.vnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.rviannaoliveira.vnavigation.DestinationFragmentTree.Companion.ID_ARGUMENTS

class DestinationFragmentOne : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_destination_one, container!!, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle().apply {
            putString(ID_ARGUMENTS, " GO to the Four")
        }

        view.findViewById<View>(R.id.button1).setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_destinationFragmentOne_to_destinationFragmentTree,
                bundle
            )
        )

        view.findViewById<View>(R.id.button).setOnClickListener {
            //Note : Action Global
            Navigation.findNavController(it).navigate(R.id.action_global_destinationFragmentTwo)
        }

    }
}