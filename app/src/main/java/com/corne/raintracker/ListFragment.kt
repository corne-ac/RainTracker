package com.corne.raintracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.corne.raintracker.data.DBHelper
import com.corne.raintracker.data.ListAdapter
import com.corne.raintracker.data.RainfallArrayItem
import com.corne.raintracker.data.RainfallEntry

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbHelper = DBHelper(requireContext())

        val entries: List<RainfallEntry> = dbHelper.getRainfallEntries()

        val context = context as MainActivity

        val lv = context.findViewById(R.id.lstEntries) as ListView
        val adapter = ListAdapter(context, convertToRainfallArrayItem(entries))

        lv.adapter = adapter
    }

    private fun convertToRainfallArrayItem(entries: List<RainfallEntry>): ArrayList<RainfallArrayItem> {
        val result = ArrayList<RainfallArrayItem>()
        for (entry in entries) {
            val item = RainfallArrayItem()
            item.id = entry.id
            item.date = entry.date
            item.time = entry.time
            item.amount = entry.amount
            item.note = entry.note
            result.add(item)
        }
        return result
    }
}