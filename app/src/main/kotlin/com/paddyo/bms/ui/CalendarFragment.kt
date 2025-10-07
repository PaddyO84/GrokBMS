package com.paddyo.bms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.paddyo.bms.databinding.FragmentCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.dayBinder = object : DayBinder<com.kizitonwose.calendarview.ui.ViewContainer> {
            override fun create(view: View) = com.kizitonwose.calendarview.ui.ViewContainer(view)
            override fun bind(container: com.kizitonwose.calendarview.ui.ViewContainer, day: CalendarDay) {
                // TODO: Bind tasks to calendar days
            }
        }
        val currentMonth = YearMonth.now()
        binding.calendarView.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), java.time.DayOfWeek.MONDAY)
        binding.calendarView.scrollToMonth(currentMonth)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}