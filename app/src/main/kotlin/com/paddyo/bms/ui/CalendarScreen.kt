package com.paddyo.bms.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.paddyo.bms.data.entities.Task
import com.paddyo.bms.viewmodels.TaskViewModel
import kotlinx.coroutines.launch
import java.util.*
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen(navController: NavController, viewModel: TaskViewModel = hiltViewModel()) {
    val calendar = Calendar.getInstance()
    val pagerState = rememberPagerState(initialPage = 500)
    val coroutineScope = rememberCoroutineScope()
    var selectedDate by rememberSaveable { mutableStateOf(calendar.timeInMillis) }
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    var monthTasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    LaunchedEffect(selectedDate) {
        val cal = Calendar.getInstance().apply { timeInMillis = selectedDate }
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val startOfDay = cal.timeInMillis
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        val endOfDay = cal.timeInMillis
        tasks = viewModel.getTasksForDateRange(startOfDay, endOfDay)
    }
    LaunchedEffect(pagerState.currentPage) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 500 + pagerState.currentPage)
            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val startOfMonth = cal.timeInMillis
        cal.add(Calendar.MONTH, 1)
        cal.add(Calendar.DAY_OF_MONTH, -1)
        val endOfMonth = cal.timeInMillis
        monthTasks = viewModel.getTasksForDateRange(startOfMonth, endOfMonth)
    }
    Column(Modifier.padding(16.dp)) {
        Text("Calendar", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        HorizontalPager(count = 1000, state = pagerState) { page ->
            val cal = Calendar.getInstance().apply {
                set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 500 + page)
                set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, 1)
            }
            MonthView(
                year = cal.get(Calendar.YEAR),
                month = cal.get(Calendar.MONTH),
                selectedDate = selectedDate,
                monthTasks = monthTasks,
                onDateSelected = { selectedDate = it }
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } }) {
                Text("Previous")
            }
            Button(onClick = {
                selectedDate = System.currentTimeMillis()
                coroutineScope.launch { pagerState.animateScrollToPage(500) }
            }) {
                Text("Today")
            }
            Button(onClick = { coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } }) {
                Text("Next")
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Tasks for ${java.text.SimpleDateFormat("MMM dd, yyyy").format(java.util.Date(selectedDate))}", style = MaterialTheme.typography.titleLarge)
        if (tasks.isEmpty()) {
            Text("No tasks for this date", Modifier.padding(16.dp))
        } else {
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(task, onClick = { navController.navigate("job/${task.jobId}") })
                }
            }
        }
    }
}
@Composable
fun MonthView(year: Int, month: Int, selectedDate: Long, monthTasks: List<Task>, onDateSelected: (Long) -> Unit) {
    val calendar = Calendar.getInstance().apply {
        set(year, month, 1)
    }
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
    val weeks = (daysInMonth + firstDayOfWeek + 6) / 7
    Column {
        Text(
            java.text.SimpleDateFormat("MMMM yyyy").format(calendar.time),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Row(Modifier.fillMaxWidth()) {
            listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa").forEach { day ->
                Text(
                    day,
                    modifier = Modifier.weight(1f).padding(4.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        for (week in 0 until weeks) {
            Row(Modifier.fillMaxWidth()) {
                for (dayOfWeek in 0 until 7) {
                    val day = week * 7 + dayOfWeek - firstDayOfWeek + 1
                    if (day in 1..daysInMonth) {
                        val cal = Calendar.getInstance().apply {
                            set(year, month, day, 0, 0, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        val isSelected = cal.timeInMillis == selectedDate
                        val hasTask = monthTasks.any { it.requiredDate in cal.timeInMillis until (cal.timeInMillis + 86_400_000) }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else if (hasTask) MaterialTheme.colorScheme.secondary
                                    else MaterialTheme.colorScheme.surface
                                )
                                .clickable { onDateSelected(cal.timeInMillis) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                day.toString(),
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                else if (hasTask) MaterialTheme.colorScheme.onSecondary
                                else MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
