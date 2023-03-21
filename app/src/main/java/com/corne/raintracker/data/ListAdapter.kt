package com.corne.raintracker.data

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.corne.raintracker.R
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter (private val context : Activity, private val entries: ArrayList<RainfallArrayItem>)
    : ArrayAdapter<RainfallArrayItem>(context, R.layout.list_item, entries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)

        val txtDate : TextView = view.findViewById(R.id.txtDate)
        val txtTime : TextView = view.findViewById(R.id.txtTime)
        val txtRained : TextView = view.findViewById(R.id.txtRainedmm)
        val txtNotes : TextView = view.findViewById(R.id.txtNotes)

        txtDate.text = SimpleDateFormat("dd MMM yyyy", Locale.UK).format(entries[position].date)
        txtTime.text = SimpleDateFormat("HH:mm", Locale.UK).format(entries[position].time)
        txtRained.text = entries[position].amount.toString()
        txtNotes.text = entries[position].note


        return view
    }
}