package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private var firstNumber = ""
    private var secondNumber = ""
    private var operator = ""
    private var isOperatorClicked = false
    private var result: Double? = null

    // TextViews
    private lateinit var calTextView: TextView
    private lateinit var resTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        calTextView = findViewById(R.id.cal)
        resTextView = findViewById(R.id.res)

        // Link Buttons
        val buttons = listOf(
            findViewById<Button>(R.id.one),
            findViewById<Button>(R.id.two),
            findViewById<Button>(R.id.thr),
            findViewById<Button>(R.id.fou),
            findViewById<Button>(R.id.fiv),
            findViewById<Button>(R.id.six),
            findViewById<Button>(R.id.sev),
            findViewById<Button>(R.id.eig),
            findViewById<Button>(R.id.nin),
            findViewById<Button>(R.id.zer)
        )

        val addButton = findViewById<Button>(R.id.add)
        val subButton = findViewById<Button>(R.id.sub)
        val mulButton = findViewById<Button>(R.id.mul)
        val divButton = findViewById<Button>(R.id.div)
        val equButton = findViewById<Button>(R.id.equ)
        val cancelOneButton = findViewById<ImageView>(R.id.cancelOne)
        val removeAllButton = findViewById<Button>(R.id.removeAll)

        val decButton = findViewById<Button>(R.id.dec)

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

        // Equals Button click listener
        equButton.setOnClickListener { calculate() }

        // Cancel One Button click listener (Backspace)
        cancelOneButton.setOnClickListener {
            if (isOperatorClicked) {
                // Remove last character from secondNumber or reset operator if secondNumber is empty
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
                // Remove last character from firstNumber
                if (firstNumber.isNotEmpty()) {
                    firstNumber = ""
                    calTextView.text = ""
                    resTextView.text = "0"
                }
            }
        }

        // Remove All Button click listener (Clear All)
        removeAllButton.setOnClickListener {
            resetAll()
        }
    }

    // Handle Operator Logic
    private fun handleOperator(op: String) {
        if (firstNumber.isNotEmpty()) {
            operator = op
            isOperatorClicked = true
            calTextView.text = "$firstNumber $operator"
            resTextView.text = ""
        }
    }

    // Perform Calculation
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

            // Format the result to two decimal places
            val decimalFormat = DecimalFormat("#.00")
            val formattedResult = decimalFormat.format(result ?: 0.0)

            // Display the result
            resTextView.text = formattedResult
            calTextView.text = "$firstNumber $operator $secondNumber"

            // Reset for the next calculation
            isOperatorClicked = false
            firstNumber = ""
            secondNumber = ""
            operator = ""
        }
    }

    // Reset the input to the initial state
    private fun resetInput() {
        firstNumber = ""
        secondNumber = ""
        resTextView.text = "0"
        calTextView.text = ""
    }

    // Clear everything
    private fun resetAll() {
        resetInput()
        operator = ""
        isOperatorClicked = false
        result = null
    }
}
