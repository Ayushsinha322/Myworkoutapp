package com.dangerx.myworkoutapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerx.myworkoutapp.databinding.ActivityHistoryBinding
import com.dangerxmyworkoutapp.HistoryAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    // create a binding for the layout
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Inflate the layout
            binding = ActivityHistoryBinding.inflate(layoutInflater)
            // Bind the layout to this activity
            setContentView(binding?.root)

            // Setting up the action bar in the History Screen Activity and
            // adding a back arrow button and click event for it.
            setSupportActionBar(binding?.toolbarHistoryActivity)

            val actionbar = supportActionBar
            if (actionbar != null) {
                actionbar.setDisplayHomeAsUpEnabled(true)
                actionbar.title = "HISTORY"
            }

            binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
                onBackPressed()
            }

            val dao = (application as WorkOutApp).db.historyDao()
            getAllCompletedDates(dao)

        } catch (e: Exception) {
            // Log any exception that occurs during layout inflation
            Log.e("HistoryActivity", "Error inflating layout", e)
            // Handle the error as needed (show an error message, etc.)
        }
    }

    /**
     * Function is used to get the list exercise completed dates.
     */
    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchALlDates().collect { allCompletedDatesList ->
                if (allCompletedDatesList.isNotEmpty()) {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.GONE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList) {
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(ArrayList(dates))

                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Reset the binding to null to avoid memory leaks
        binding = null
    }
}
