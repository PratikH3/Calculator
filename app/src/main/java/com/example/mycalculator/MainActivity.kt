package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var tvAnswer: TextView
    private lateinit var numberButtons: Array<Button>
    private lateinit var operatorButtons: List<Button>
    private var operator: Operator = Operator.NONE
    private var isOperatorClicked: Boolean = false
    private var operand1: Int =0

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
            i.setOnClickListener(){
                numberButtonClicked(i)
            }
        }

        for(i in operatorButtons ){
            i.setOnClickListener(){
                operatorButtonClick(i)
            }
        }
        buttonEquals.setOnClickListener(){
            buttonEqualClick()
        }
    }

    private fun buttonEqualClick() {
        val operand2 = strNumber.toString().toInt()
        val result = when(operator){
            Operator.ADD -> operand1 + operand2
            Operator.SUB -> operand1 - operand2
            Operator.MUL ->  operand1 * operand2
            Operator.DIV -> operand1 / operand2
            else -> {0}
        }
        strNumber.clear()
        strNumber.append(result.toString())
        updateDisplay()
        isOperatorClicked = true
    }

    private fun updateDisplay() {
        try {
            val textVal = strNumber.toString().toInt()
            tvAnswer.text = textVal.toString()
        }catch (e: IllegalArgumentException){
            strNumber.clear()
            tvAnswer.text = "ERROR"
        }
    }

    private fun operatorButtonClick(btn: Button) {
        when (btn.text) {
            "+" -> operator = Operator.ADD
            "-" -> operator = Operator.SUB
            "*" -> operator = Operator.MUL
            "/" -> operator = Operator.DIV
            else -> Operator.NONE
        }
        isOperatorClicked = true
    }

    private fun numberButtonClicked(btn: Button) {
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