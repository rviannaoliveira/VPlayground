package com.rviannaoliveira.vnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class DestinationFragmentFour : Fragment() {
//NOTE: Today 14/05/2019 I believe that is works only with AndroidX
//private val args: DestinationFragmentFourArgs by navAr()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_destination_four, container!!, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.button).text =
            arguments?.let { DestinationFragmentFourArgs.fromBundle(it).buttonName }
    }
}