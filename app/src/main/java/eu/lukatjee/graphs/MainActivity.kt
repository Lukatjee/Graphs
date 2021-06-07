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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val berekenButton = findViewById<Button>(R.id.berekenButton)
        berekenButton.setOnClickListener(this)

        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener(this)

    }

    fun graphDraw(a : Double, b : Double, c : Double, x1 : Double, discriminant : Double, x2 : Double) {

        val series = LineGraphSeries<DataPoint>()
        val graph = findViewById<GraphView>(R.id.graph)

        graph.viewport.isScrollable = true
        graph.viewport.isScalable = true
        graph.viewport.setScalableY(true)
        graph.viewport.setScrollableY(true)

        if (discriminant > 0) {

            if (x1 > x2) {
                
                var puntLinks = x2 - 2.1
                val puntBasis = x2

                while (puntLinks < puntBasis) {

                    puntLinks += 0.1
                    val point = (a * puntLinks.pow(2.0)) + (b * puntLinks) + c

                    series.appendData(DataPoint(puntLinks, point), true, 10000)

                }

                var puntRechts = x1 + 2.1
                var nul = x1 - 2.1

                while (nul < puntRechts) {

                    nul += 0.1
                    val point = (a * nul.pow(2.0)) + (b * nul) + c
                    series.appendData(DataPoint(nul, point), true, 10000)

                }

                graph.removeAllSeries()
                graph.addSeries(series)


            } else {

                var puntLinks = x1 - 2.1
                val puntBasis = x1

                while (puntLinks < puntBasis) {

                    puntLinks += 0.1
                    val point = (a * puntLinks.pow(2.0)) + (b * puntLinks) + c

                    series.appendData(DataPoint(puntLinks, point), true, 5000)

                }

                var puntRechts = x2 + 2.1
                var nul = x2 - 2.1

                while (nul < puntRechts) {

                    nul += 0.1
                    val point = (a * nul.pow(2.0)) + (b * nul) + c
                    series.appendData(DataPoint(nul, point), true, 5000)

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

                val aEt = findViewById<EditText>(R.id.aEditText)
                val bEt = findViewById<EditText>(R.id.bEditText)
                val cEt = findViewById<EditText>(R.id.cEditText)

                var a = aEt.text.toString().trim()
                var b = bEt.text.toString().trim()
                var c = cEt.text.toString().trim()

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
                    solutionTv.text = "D = 0 | x = ${x1}"

                    graphDraw(aDouble, bDouble, cDouble, x1, discriminant, 0.0)

                    return

                } else if (discriminant < 0) {

                    val solutionTv = findViewById<TextView>(R.id.solutionTv)
                    solutionTv.text = "niet mogelijk"

                    return

                }

                val x1 = (-bDouble + sqrt(discriminant)) / (2 * aDouble)
                val x2 = (-bDouble - sqrt(discriminant)) / (2 * aDouble)

                val solutionTv = findViewById<TextView>(R.id.solutionTv)
                solutionTv.text = "D = ${discriminant} | x1 = ${x1} | x2 = ${x2}"

                graphDraw(aDouble, bDouble, cDouble, x1, discriminant, x2)

            }

            R.id.clearButton -> {

                val aEt = findViewById<EditText>(R.id.aEditText)
                val bEt = findViewById<EditText>(R.id.bEditText)
                val cEt = findViewById<EditText>(R.id.cEditText)

                aEt.text.clear()
                bEt.text.clear()
                cEt.text.clear()

            }

        }

    }

}
