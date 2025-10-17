package com.manishjajoriya.moctale.presentation.contentScreen.components

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.domain.model.content.Genre

@Composable
fun VibeChart(modifier: Modifier = Modifier, genres: List<Genre>) {
  AndroidView(
      modifier = modifier,
      factory = { context ->
        PieChart(context).apply {
          setUsePercentValues(false)
          description.isEnabled = false
          isDrawHoleEnabled = true
          holeRadius = 70f
          setHoleColor("#FF080808".toColorInt())
          setDrawEntryLabels(false)
          legend.isEnabled = false
          setExtraOffsets(0f, 0f, 0f, 0f)
          isRotationEnabled = false
          rotationAngle = 0f
          setDrawCenterText(true)
          setCenterTextSize(16f)
          setCenterTextColor(Color.WHITE)
          setCenterTextTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold))
        }
      },
      update = { chart ->
        val entries = genres.map { PieEntry(it.percentage.toFloat(), it.name) }
        val colors = genres.map { it.color.toColorInt() }

        val dataSet =
            PieDataSet(entries, "").apply {
              this.colors = colors
              setDrawValues(false)
              selectionShift = 5f
              sliceSpace = 4f
            }

        chart.data = PieData(dataSet)
        chart.invalidate()

        chart.setOnChartValueSelectedListener(
            object : OnChartValueSelectedListener {
              override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e is PieEntry) {
                  val name = e.label
                  val percentage = e.value
                  chart.centerText = "$name\n${percentage.toInt()}%"
                }
              }

              override fun onNothingSelected() {
                chart.centerText = ""
              }
            }
        )
      },
  )
}
