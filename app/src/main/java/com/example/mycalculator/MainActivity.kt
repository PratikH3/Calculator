package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var tvAnswer: TextView
    private lateinit var numberButtons: Array<Button>
    private lateinit var operatorButtons: List<Button>
    private var operator: Operator = Operator.NONE
    private var isOperatorClicked: Boolean = false
    private var operand1: Int = 0


    private var strNumber = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAnswer= findViewById(R.id.tvAnswer)
        initializeComponents()

    }

    private fun initializeComponents() {
        numberButtons = arrayOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        operatorButtons = listOf(buttonAdd, buttonMinus, buttonMul, buttonDiv)
        for (i in numberButtons){
            i.setOnClickListener {
                numberButtonClicked(i)
            }
        }

        for(i in operatorButtons ){
            i.setOnClickListener {
                operatorButtonClick(i)
            }
        }
        buttonEquals.setOnClickListener {
            buttonEqualClick()
        }
        buttonDel.setOnClickListener {
            buttonDelClick()
        }

        buttonPlusMinus.setOnClickListener {
            buttonPlusMinusClick()
        }
        buttonClr.setOnClickListener {
            buttonClrClick()
        }

    }

    private fun buttonClrClick() {
        val result = 0
        tvInput.text = ""
        strNumber.clear()
        strNumber.append(result)
        operand1 = 0
        isOperatorClicked = false
        updateDisplay()
    }

    private fun buttonPlusMinusClick() {
        tvInput.text = ""
        if(strNumber.isNotEmpty()){
            val ans = -strNumber.toString().toInt()
            strNumber.clear()
            strNumber.append(ans.toString())
            updateDisplay()

        }

    }

    private fun buttonDelClick() {
        tvInput.text = ""
        if(strNumber.length>1){
            strNumber.deleteCharAt(strNumber.lastIndex)
            updateDisplay()
        }

    }

    private fun buttonEqualClick() {
        tvInput.text = ""
        var operand2 = strNumber.toString().toInt()
        if(operand2 == 0 && operator == Operator.DIV){
            tvInput.text = getString(R.string.divide_by_zero)
            strNumber.append("0")
            isOperatorClicked = false
            operand1 = 0
            operator = Operator.NONE;
            updateDisplay()
        }else{
            val result = when(operator){
                Operator.ADD -> operand1 + operand2
                Operator.SUB -> operand1 - operand2
                Operator.MUL ->  operand1 * operand2
                Operator.DIV -> operand1 / operand2

                else -> {0}
            }
            strNumber.clear()

            if(result.toString().isNotEmpty()){
                strNumber.append(result.toString())
            }
            updateDisplay()
        }
        isOperatorClicked = true

    }

    private fun updateDisplay() {
        try {
            val textVal = strNumber.toString().toInt()
            //val ans = (textVal * 100.0).roundToInt() / 100.0
            tvAnswer.text = textVal.toString()
        }catch (e: IllegalArgumentException){
            strNumber.clear()
            tvAnswer.text = getString(R.string.error)
        }
    }

    private fun operatorButtonClick(btn: Button) {
        when (btn.text) {
            "+" -> operator = Operator.ADD
            "-" -> operator = Operator.SUB
            "×" -> operator = Operator.MUL
            "÷" -> operator = Operator.DIV
            else -> Operator.NONE
        }
        tvInput.text = btn.text
        isOperatorClicked = true
    }

    private fun numberButtonClicked(btn: Button) {
        if(tvInput.text.length>2){
            tvInput.text = ""
        }
        if(isOperatorClicked){
            operand1 = strNumber.toString().toInt()
            strNumber.clear()
            isOperatorClicked = false
        }
        strNumber.append(btn.text)
        updateDisplay()
    }
}

enum class Operator{ADD, SUB, MUL, DIV, NONE}