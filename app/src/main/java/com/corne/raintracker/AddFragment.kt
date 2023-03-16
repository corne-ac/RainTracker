package com.corne.raintracker

import RainfallEntry
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.corne.raintracker.data.DBHelper
import java.time.LocalDate
import java.time.LocalTime
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
class AddFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val myCalendar = Calendar.getInstance()

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

        //Time Picker Button
        val btnTimePicker: Button = view.findViewById(R.id.btnTimePicker)

        val timePicker = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // when user sets the time in the time picker dialog, update the calendar instance
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, minute)
                // update the label text with the selected time
                updateLabelTime(myCalendar)
            },
            // set the initial time in the time picker dialog to the current time
            myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE),
            // set the time picker dialog to display in 24-hour format
            true
        )

        btnTimePicker.setOnClickListener {
            timePicker.show()
            updateLabelTime(myCalendar)
        }




        //Add Log Button
        // Get the reference to the "btnAddLog" button
        val btnAddLog: Button = view.findViewById(R.id.btnAddLog)

// Set a click listener for the "btnAddLog" button
        btnAddLog.setOnClickListener {
            val dbHelper = DBHelper(requireContext())
            // Declare variables to hold data from the UI components
            val entry: RainfallEntry
            val txtNotes: TextView = view.findViewById(R.id.txtNotes)
            val btnDatePicker: Button = view.findViewById(R.id.btnDatePicker)
            val txtrain: TextView = view.findViewById(R.id.txtRain)
            val btnTimePicker: Button = view.findViewById(R.id.btnTimePicker)


            if (txtrain.text == "") {
                Toast.makeText(requireContext(),"Please enter rain in mm",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the amount of rainfall from the "txtRain" TextView and convert it to a Double
            val amount = txtrain.text.toString().toDouble()

            // Get the notes from the "txtNotes" TextView
            val notes = txtNotes.text.toString()

            // Get the date from the "btnDatePicker" Button and convert it to a LocalDate object using a formatter
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
            val date = LocalDate.parse(btnDatePicker.text.toString(), formatter)

            // parse the time string to a Time object using java.sql.Time
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
            val time = LocalTime.parse(btnTimePicker.text.toString(), timeFormatter)



            // Create a new RainfallEntry object with the gathered data
            entry = RainfallEntry(
                date = date.toEpochDay(),
                time = time.toSecondOfDay().toLong(),
                amount = amount,
                note = notes
            )

            // Insert the RainfallEntry into the database using a coroutine and a lifecycleScope
            val id = dbHelper.insertRainfallEntry(entry)

            if (id > 0) {
                // Insert successful
                Toast.makeText(requireContext(), "Record inserted successfully", Toast.LENGTH_SHORT).show()
                //Return to HomeFragment

            } else {
                // Insert failed
                Toast.makeText(requireContext(), "Failed to insert record", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myCalendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
    }

    // Update the label with the selected date
    private fun updateLabel(myCalendar: Calendar) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale("UK"))
        val btnDatePicker: Button = requireView().findViewById(R.id.btnDatePicker)
        btnDatePicker.setText(sdf.format(myCalendar.time))
    }

    private fun updateLabelTime(myCalendar: Calendar) {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeString = sdf.format(Date(myCalendar.timeInMillis))
        // update the button text with the selected time
        val btnTimePicker: Button = requireView().findViewById(R.id.btnTimePicker)
        btnTimePicker.text = timeString
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