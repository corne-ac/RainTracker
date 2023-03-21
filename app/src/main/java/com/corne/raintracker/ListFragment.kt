package com.corne.raintracker

import RainfallEntry
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.corne.raintracker.data.DBHelper
import com.corne.raintracker.data.ListAdapter
import com.corne.raintracker.data.RainfallArrayItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    fun convertToRainfallArrayItem(entries: List<RainfallEntry>): ArrayList<RainfallArrayItem> {
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



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}