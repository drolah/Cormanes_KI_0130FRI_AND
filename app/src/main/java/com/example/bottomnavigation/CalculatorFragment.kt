package com.example.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.DecimalFormat

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private var firstNumber = ""
    private var secondNumber = ""
    private var operator = ""
    private var isOperatorClicked = false
    private var result: Double? = null

    // TextViews
    private lateinit var calTextView: TextView
    private lateinit var resTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calTextView = view.findViewById(R.id.cal)
        resTextView = view.findViewById(R.id.res)

        // Link Buttons
        val buttons = listOf(
            view.findViewById<Button>(R.id.one),
            view.findViewById<Button>(R.id.two),
            view.findViewById<Button>(R.id.thr),
            view.findViewById<Button>(R.id.fou),
            view.findViewById<Button>(R.id.fiv),
            view.findViewById<Button>(R.id.six),
            view.findViewById<Button>(R.id.sev),
            view.findViewById<Button>(R.id.eig),
            view.findViewById<Button>(R.id.nin),
            view.findViewById<Button>(R.id.zer)
        )

        val addButton = view.findViewById<Button>(R.id.add)
        val subButton = view.findViewById<Button>(R.id.sub)
        val mulButton = view.findViewById<Button>(R.id.mul)
        val divButton = view.findViewById<Button>(R.id.div)
        val equButton = view.findViewById<Button>(R.id.equ)
        val cancelOneButton = view.findViewById<ImageView>(R.id.cancelOne)
        val removeAllButton = view.findViewById<Button>(R.id.removeAll)

        val decButton = view.findViewById<Button>(R.id.dec)

        decButton.setOnClickListener {
            if (!isOperatorClicked) {
                // Only add a decimal point if there's no existing one in the firstNumber
                if (!firstNumber.contains(".")) {
                    firstNumber += "."
                    resTextView.text = firstNumber
                }
            } else {
                // Only add a decimal point if there's no existing one in the secondNumber
                if (!secondNumber.contains(".")) {
                    secondNumber += "."
                    resTextView.text = secondNumber
                }
            }
        }

        // Number Buttons click listeners
        buttons.forEach { button ->
            button.setOnClickListener {
                val number = button.text.toString()
                if (!isOperatorClicked) {
                    firstNumber += number
                    resTextView.text = firstNumber
                } else {
                    secondNumber += number
                    resTextView.text = secondNumber
                }
            }
        }

        addButton.setOnClickListener { handleOperator("+") }
        subButton.setOnClickListener { handleOperator("-") }
        mulButton.setOnClickListener { handleOperator("*") }
        divButton.setOnClickListener { handleOperator("/") }

        equButton.setOnClickListener { calculate() }

        cancelOneButton.setOnClickListener {
            if (isOperatorClicked) {
                if (secondNumber.isNotEmpty()) {
                    secondNumber = secondNumber.dropLast(1)
                    resTextView.text = secondNumber
                } else {
                    operator = ""
                    isOperatorClicked = false
                    calTextView.text = ""
                    resTextView.text = firstNumber
                    isOperatorClicked = false
                }
            } else {
                if (firstNumber.isNotEmpty()) {
                    firstNumber = ""
                    calTextView.text = ""
                    resTextView.text = "0"
                }
            }
        }

        removeAllButton.setOnClickListener {
            resetAll()
        }
    }

    private fun handleOperator(op: String) {
        if (firstNumber.isNotEmpty()) {
            operator = op
            isOperatorClicked = true
            calTextView.text = "$firstNumber $operator"
            resTextView.text = ""
        }
    }

    private fun calculate() {
        if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && operator.isNotEmpty()) {
            result = when (operator) {
                "+" -> firstNumber.toDouble() + secondNumber.toDouble()
                "-" -> firstNumber.toDouble() - secondNumber.toDouble()
                "*" -> firstNumber.toDouble() * secondNumber.toDouble()
                "/" -> if (secondNumber != "0") firstNumber.toDouble() / secondNumber.toDouble() else {
                    resTextView.textSize = 30f
                    resTextView.text = "Cannot divide by zero"
                    secondNumber = ""
                    return
                }
                else -> null
            }

            val decimalFormat = DecimalFormat("#.00")
            val formattedResult = decimalFormat.format(result ?: 0.0)

            resTextView.text = formattedResult
            calTextView.text = "$firstNumber $operator $secondNumber"

            isOperatorClicked = false
            firstNumber = ""
            secondNumber = ""
            operator = ""
        }
    }

    private fun resetAll() {
        firstNumber = ""
        secondNumber = ""
        operator = ""
        isOperatorClicked = false
        result = null
        resTextView.text = "0"
        calTextView.text = ""
    }
}
