package com.ardnn.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] btnNumbers = new Button[10];
    private Button btnResult, btnClear, btnDelete, btnDot, btnPlus, btnMinus, btnMultiply, btnDivide;
    private TextView tvResult;

    private String result;
    private List<List<String>> calculateNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        tvResult.setMovementMethod(new ScrollingMovementMethod());

        result = tvResult.getText().toString();

        btnResult = findViewById(R.id.btn_result);
        btnResult.setOnClickListener(this);

        btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);

        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);

        btnDot = findViewById(R.id.btn_dot);
        btnDot.setOnClickListener(this);

        btnPlus = findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(this);

        btnMinus = findViewById(R.id.btn_minus);
        btnMinus.setOnClickListener(this);

        btnMultiply = findViewById(R.id.btn_multiply);
        btnMultiply.setOnClickListener(this);

        btnDivide = findViewById(R.id.btn_divide);
        btnDivide.setOnClickListener(this);

        btnNumbers[0] = findViewById(R.id.btn_zero);
        btnNumbers[1] = findViewById(R.id.btn_one);
        btnNumbers[2] = findViewById(R.id.btn_two);
        btnNumbers[3] = findViewById(R.id.btn_three);
        btnNumbers[4] = findViewById(R.id.btn_four);
        btnNumbers[5] = findViewById(R.id.btn_five);
        btnNumbers[6] = findViewById(R.id.btn_six);
        btnNumbers[7] = findViewById(R.id.btn_seven);
        btnNumbers[8] = findViewById(R.id.btn_eight);
        btnNumbers[9] = findViewById(R.id.btn_nine);
        for (Button btn: btnNumbers) {
            btn.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zero:
                setNumber(btnNumbers[0].getText().toString());
                break;
            case R.id.btn_one:
                setNumber(btnNumbers[1].getText().toString());
                break;
            case R.id.btn_two:
                setNumber(btnNumbers[2].getText().toString());
                break;
            case R.id.btn_three:
                setNumber(btnNumbers[3].getText().toString());
                break;
            case R.id.btn_four:
                setNumber(btnNumbers[4].getText().toString());
                break;
            case R.id.btn_five:
                setNumber(btnNumbers[5].getText().toString());
                break;
            case R.id.btn_six:
                setNumber(btnNumbers[6].getText().toString());
                break;
            case R.id.btn_seven:
                setNumber(btnNumbers[7].getText().toString());
                break;
            case R.id.btn_eight:
                setNumber(btnNumbers[8].getText().toString());
                break;
            case R.id.btn_nine:
                setNumber(btnNumbers[9].getText().toString());
                break;
            case R.id.btn_result:
                showResult();
                break;
            case R.id.btn_clear:
                clearNumber();
                break;
            case R.id.btn_delete:
                deleteNumber();
                break;
            case R.id.btn_dot:
                break;
            case R.id.btn_plus:
                plus();
                break;
            case R.id.btn_minus:
                minus();
                break;
            case R.id.btn_multiply:
                multiply();
                break;
            case R.id.btn_divide:
                divide();
                break;
        }

    }

    private boolean isZero() {
        return result.equals("0");
    }

    private boolean checkLastChar(char c) {
        return result.charAt(result.length()-1) == c;
    }

    private boolean isLastZero() {
        return checkLastChar('0');
    }

    private boolean isLastNewline() {
        return checkLastChar('\n');
    }

    private boolean hasOperator() {
        return (checkLastChar('+')
                || checkLastChar('-')
                || checkLastChar('x')
                || checkLastChar('/'));
    }

    private void setNumber(String number) {
        if (isZero()) {
            result = number;
        } else if (hasOperator()) {
            result += "\n" + number;
        } else {
            result += number;
        }
        setResult(result);
    }

    private void clearNumber() {
        result = "0";
        setResult(result);
        calculateNumbers.clear();
    }

    private void deleteNumber() {
        if (result.length() > 1) {
            int del = hasOperator() ? 2 : 1;
            result = result.substring(0, result.length() - del);
            setResult(result);
        } else {
            clearNumber();
        }
    }

    private void setResult(String s) {
        tvResult.setText(s);
    }

    private void addOperation(char c) {
        if (hasOperator()) {
            result = result.substring(0, result.length() - 1) + c;
        } else if (!isLastNewline()) {
            result += "\n" + c + "\n";
            calculateNumbers.get(calculateNumbers.size() - 1).add(String.valueOf(c));
        }
        setResult(result);
    }

    private boolean isFirstCalculation(char operation) {
        if (calculateNumbers.isEmpty()) {
            String ans = "";
            for (char c: result.toCharArray()) {
                ans += c;
            }
            calculateNumbers.add(new ArrayList<>(Arrays.asList(ans)));
            addOperation(operation);
            return true;
        }
        return false;
    }

    private void addNumbers(char operation) {
        String temp = "";
        int pos = result.length() - 1;
        while (result.charAt(pos) != '\n') {
            temp += result.charAt(pos);
            pos--;
        }
        StringBuilder sb = new StringBuilder(temp);
        sb.reverse();
        calculateNumbers.add(new ArrayList<>(Arrays.asList(String.valueOf(sb))));
        if (operation != '!') addOperation(operation);
    }

    private void plus() {
        char operation = '+';
        if (isFirstCalculation(operation)) return;
        addNumbers(operation);
    }

    private void minus() {
        char operation = '-';
        if (isFirstCalculation(operation)) return;
        addNumbers(operation);
    }

    private void multiply() {
        char operation = 'x';
        if (isFirstCalculation(operation)) return;
        addNumbers(operation);
    }

    private void divide() {
        char operation = '/';
        if (isFirstCalculation(operation)) return;
        addNumbers(operation);
    }

    private void showResult() {
        addNumbers('!');
        String ans = "";
        for (int i = 0; i < calculateNumbers.size(); i++) {
            ans += calculateNumbers.get(i).get(0);
            if (i < calculateNumbers.size() - 1) {
                ans += " --> "
                        + calculateNumbers.get(i).get(1)
                        + "\n";
            }
        }
        result = ans;
        getFinalResult();
        setResult(result);
        calculateNumbers.clear();
    }

    private void getFinalResult() {
        double res = Double.valueOf(calculateNumbers.get(0).get(0));
        for (int i = 1; i < calculateNumbers.size(); i++) {
            double num = Double.valueOf(calculateNumbers.get(i).get(0));
            switch (calculateNumbers.get(i-1).get(1)) {
                case "+":
                    res += num;
                    break;
                case "-":
                    res -= num;
                    break;
                case "x":
                    res *= num;
                    break;
                case "/":
                    if (num == 0) {
                        result = "Error. Can not divide by zero.";
                        return;
                    }
                    res /= num;
                    break;
            }
        }
        result += "\n = " + String.valueOf(res);
    }

    private void sortOperation() {
//        for (int i = 0;)
    }
}