package com.manishjajoriya.moctale.presentation.contentScreen.components

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.github.mikephil.charting.utils.ColorTemplate
import com.manishjajoriya.moctale.R
import kotlin.math.roundToInt

@Composable
fun MoctaleMeter(
    modifier: Modifier = Modifier,
    reviewCount: List<Int>,
    reviewPercentage: List<Double>,
    totalReviewCount: Int,
) {
  Box(modifier = modifier) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
          PieChart(context).apply {
            setUsePercentValues(false)
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 75f
            setHoleColor("#FF080808".toColorInt())
            setDrawEntryLabels(false)
            legend.isEnabled = false
            setExtraOffsets(0f, 20f, 0f, 0f)
            isRotationEnabled = false
            setDrawCenterText(true)
            setCenterTextSize(16f)
            setCenterTextColor(Color.WHITE)
            setCenterTextTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold))
            rotationAngle = 180f
            maxAngle = 180f
          }
        },
        update = { chart ->
          val entries =
              listOf(
                  PieEntry(
                      reviewPercentage[0].toFloat(),
                      "Negative",
                  ),
                  PieEntry(
                      reviewPercentage[1].toFloat(),
                      "Neutral",
                  ),
                  PieEntry(
                      reviewPercentage[2].toFloat(),
                      "Positive",
                  ),
                  PieEntry(
                      reviewPercentage[3].toFloat(),
                      "Perfect",
                  ),
              )
          val dataSet =
              PieDataSet(entries, "").apply {
                colors =
                    listOf(
                        ColorTemplate.rgb("#FE647E"),
                        ColorTemplate.rgb("#FCB700"),
                        ColorTemplate.rgb("#00D391"),
                        ColorTemplate.rgb("#B048FF"),
                    )
                sliceSpace = 2f
                setDrawValues(false)
              }

          chart.data = PieData(dataSet)
          chart.invalidate()

          chart.setOnChartValueSelectedListener(
              object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                  if (e is PieEntry) {
                    val type = e.label
                    val percentage = e.value.roundToInt()

                    val (color, votes) =
                        when (type) {
                          "Negative" -> ColorTemplate.rgb("#FE647E") to reviewCount[0]
                          "Neutral" -> ColorTemplate.rgb("#FCB700") to reviewCount[1]
                          "Positive" -> ColorTemplate.rgb("#00D391") to reviewCount[2]
                          "Perfect" -> ColorTemplate.rgb("#B048FF") to reviewCount[3]
                          else -> ColorTemplate.rgb("#FE647E") to "0"
                        }
                    // Create the combined text
                    val text = "$percentage%\n$votes / $totalReviewCount Votes"
                    val spannable = SpannableString(text)

                    // Style first line
                    spannable.setSpan(
                        RelativeSizeSpan(2f),
                        0,
                        "$percentage%".length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                    )
                    spannable.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        "$percentage%".length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                    )
                    spannable.setSpan(
                        ForegroundColorSpan(color),
                        0,
                        "$percentage%".length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                    )

                    // Style second line
                    val start = "$percentage%\n".length
                    val end = text.length
                    spannable.setSpan(
                        ForegroundColorSpan(Color.GRAY),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                    )
                    spannable.setSpan(
                        RelativeSizeSpan(1f),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                    )

                    chart.centerText = spannable
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
}
