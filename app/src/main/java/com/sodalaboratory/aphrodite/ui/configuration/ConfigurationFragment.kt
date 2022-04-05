package com.sodalaboratory.aphrodite.ui.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sodalaboratory.aphrodite.R

class ConfigurationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
       = inflater.inflate(R.layout.fragment_configuration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView =
            view.findViewById(R.id.configuration_textview) as TextView
        textView.text = StringBuilder()
                        .append(resources.getString(R.string.app_name))
                        .append(" ")
                        .append(resources.getString(R.string.app_version))




    }
}