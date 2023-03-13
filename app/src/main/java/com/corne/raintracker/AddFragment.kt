package com.corne.raintracker

import RainfallEntry
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.corne.raintracker.data.AppDatabase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    //DatePicker code
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a reference to the button for selecting the date
        val btnDatePicker: Button = view.findViewById(R.id.btnDatePicker)

        // Get a calendar instance and initialize the date to today's date
        val myCalendar = Calendar.getInstance()

        // Define a listener for when the user selects a date from the date picker dialog
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        // Initialize the label with today's date
        updateLabel(myCalendar)

        // Set up the date picker dialog to open when the user clicks the button
        btnDatePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )

            // Set the maximum date selectable to today's date
            val datePicker1 = datePickerDialog.datePicker
            datePicker1.maxDate = System.currentTimeMillis()

            // Show the date picker dialog
            datePickerDialog.show()
        }

        //Add Log Button
        // Get the reference to the "btnAddLog" button
        val btnAddLog: Button = view.findViewById(R.id.btnAddLog)

// Set a click listener for the "btnAddLog" button
        btnAddLog.setOnClickListener {

            // Declare variables to hold data from the UI components
            val entry: RainfallEntry
            val txtNotes: TextView = view.findViewById(R.id.txtNotes)
            val btnDatePicker: Button = view.findViewById(R.id.btnDatePicker)
            val txtrain: TextView = view.findViewById(R.id.txtRain)

            // Get the amount of rainfall from the "txtRain" TextView and convert it to a Double
            val amount = txtrain.text.toString().toDouble()

            // Get the notes from the "txtNotes" TextView
            val notes = txtNotes.text.toString()

            // Get the date from the "btnDatePicker" Button and convert it to a LocalDate object using a formatter
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
            val date = LocalDate.parse(btnDatePicker.text.toString(), formatter)

            // Create a new RainfallEntry object with the gathered data
            entry = RainfallEntry(
                date = java.sql.Date.valueOf(date.toString()), //Convert from localdate to Date
                amount = amount,
                note = notes
            )

            // Insert the RainfallEntry into the database using a coroutine and a lifecycleScope
            lifecycleScope.launch {
                AppDatabase.getInstance(requireContext()).rainfallEntryDao().addEntry(entry)
            }
        }

    }

    // Update the label with the selected date
    private fun updateLabel(myCalendar: Calendar) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale("UK"))
        val btnDatePicker: Button = requireView().findViewById(R.id.btnDatePicker)
        btnDatePicker.setText(sdf.format(myCalendar.time))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = AddFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}