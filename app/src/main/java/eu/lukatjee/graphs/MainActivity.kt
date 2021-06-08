package eu.lukatjee.graphs

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var aEt : EditText
    private lateinit var bEt : EditText
    private lateinit var cEt : EditText

    private lateinit var berekenButton : Button
    private lateinit var clearButton : Button

    private lateinit var graph : GraphView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aEt = findViewById(R.id.aEditText)
        bEt = findViewById(R.id.bEditText)
        cEt = findViewById(R.id.cEditText)

        berekenButton = findViewById(R.id.berekenButton)
        berekenButton.setOnClickListener(this)

        clearButton = findViewById(R.id.clearButton)
        clearButton.setOnClickListener(this)

        graph = findViewById(R.id.graph)

    }

    private fun graphDraw(a : Double, b : Double, c : Double, x1 : Double, discriminant : Double, x2 : Double) {

        val series = LineGraphSeries<DataPoint>()

        graph.viewport.isScrollable = true
        graph.viewport.isScalable = true
        graph.viewport.setScalableY(true)
        graph.viewport.setScrollableY(true)

        if (discriminant > 0) {

            if (x1 > x2) {
                
                var puntLinks = x2 - 2.1
                val puntRechts = x1 + 2.1

                while (puntLinks < puntRechts) {

                    puntLinks += 0.1

                    while (puntLinks < puntRechts) {

                        puntLinks += 0.1
                        val point = (a * puntLinks.pow(2.0)) + (b * puntLinks) + c

                        series.appendData(DataPoint(puntLinks, point), true, 5000)

                    }

                }

                graph.removeAllSeries()
                graph.addSeries(series)


            } else {

                var puntLinks = x1 - 2.1
                val puntRechts = x2 + 2.1

                while (puntLinks < puntRechts) {

                    puntLinks += 0.1
                    val point = (a * puntLinks.pow(2.0)) + (b * puntLinks) + c

                    series.appendData(DataPoint(puntLinks, point), true, 5000)

                }

                graph.removeAllSeries()
                graph.addSeries(series)

            }

        } else {

            var puntLinks = x1 - 2.1
            val puntRechts = x1 + 2.1

            while (puntLinks < puntRechts) {

                puntLinks += 0.1
                val point = (a * puntLinks.pow(2.0)) + (b * puntLinks) + c

                series.appendData(DataPoint(puntLinks, point), true, 5000)

            }

            graph.removeAllSeries()
            graph.addSeries(series)

        }

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.berekenButton -> {

                val a = aEt.text.toString().trim()
                val b = bEt.text.toString().trim()
                val c = cEt.text.toString().trim()

                if (a.isEmpty() || b.isEmpty() || c.isEmpty()) {

                    Toast.makeText(this, "Oops, not every field has been filled yet.", Toast.LENGTH_SHORT).show()
                    return

                }

                val aDouble = a.toDouble()
                val bDouble = b.toDouble()
                val cDouble = c.toDouble()

                val discriminant = bDouble.pow(2) - (4 * aDouble * cDouble)

                if (discriminant == 0.0) {

                    val x1 = -bDouble / 2 * aDouble

                    val solutionTv = findViewById<TextView>(R.id.solutionTv)
                    solutionTv.text = getString(R.string.discriminant_nul, discriminant.toFloat(), x1.toFloat())

                    graphDraw(aDouble, bDouble, cDouble, x1, discriminant, 0.0)
                    println(x1)

                    return

                } else if (discriminant < 0) {

                    val solutionTv = findViewById<TextView>(R.id.solutionTv)
                    solutionTv.text = getString(R.string.niet_mogelijk)

                    return

                }

                val x1 = (-bDouble + sqrt(discriminant)) / (2 * aDouble)
                val x2 = (-bDouble - sqrt(discriminant)) / (2 * aDouble)

                val solutionTv = findViewById<TextView>(R.id.solutionTv)
                solutionTv.text = getString(R.string.discriminant_non_nul, discriminant.toFloat(), x1.toFloat(), x2.toFloat())

                graphDraw(aDouble, bDouble, cDouble, x1, discriminant, x2)

            }

            R.id.clearButton -> {

                val aEt = findViewById<EditText>(R.id.aEditText)
                aEt.text.clear()

                val bEt = findViewById<EditText>(R.id.bEditText)
                bEt.text.clear()

                val cEt = findViewById<EditText>(R.id.cEditText)
                cEt.text.clear()

                graph.removeAllSeries()

            }

        }

    }

}
