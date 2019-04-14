package com.rviannaoliveira.vnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment.findNavController

class DestinationFragmentTree : Fragment() {
    private val text: String by lazy { arguments!!.getString(ID_ARGUMENTS) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_destination_tree, container!!, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button)

        button.text = text

        button.setOnClickListener {
            val direction = DestinationFragmentTreeDirections.actionDestinationTreeFragmentToDestinationFragmentFour()
                .apply {
                    buttonName = "I passed the End"
                }

            findNavController(this@DestinationFragmentTree)
                .navigate(direction)
        }
    }

    companion object {
        const val ID_ARGUMENTS = "text"
    }
}