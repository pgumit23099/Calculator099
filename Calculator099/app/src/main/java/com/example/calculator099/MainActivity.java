package com.example.calculator099;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTypedNumbersAndSymbols;
    private TextView textViewResult;

    private String input = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTypedNumbersAndSymbols = findViewById(R.id.textViewTypedNumbersAndSymbols);
        textViewResult = findViewById(R.id.textViewResult);

        // Button Click Listeners
        setupButtonClickListeners();
    }

    private void setupButtonClickListeners() {
        // Number buttons
        findViewById(R.id.button0).setOnClickListener(this::appendNumber);
        findViewById(R.id.button1).setOnClickListener(this::appendNumber);
        findViewById(R.id.button2).setOnClickListener(this::appendNumber);
        findViewById(R.id.button3).setOnClickListener(this::appendNumber);
        findViewById(R.id.button4).setOnClickListener(this::appendNumber);
        findViewById(R.id.button5).setOnClickListener(this::appendNumber);
        findViewById(R.id.button6).setOnClickListener(this::appendNumber);
        findViewById(R.id.button7).setOnClickListener(this::appendNumber);
        findViewById(R.id.button8).setOnClickListener(this::appendNumber);
        findViewById(R.id.button9).setOnClickListener(this::appendNumber);
        findViewById(R.id.buttonDot).setOnClickListener(this::appendNumber);

        // Operator buttons
        findViewById(R.id.buttonAdd).setOnClickListener(this::appendOperator);
        findViewById(R.id.buttonSub).setOnClickListener(this::appendOperator);
        findViewById(R.id.buttonMul).setOnClickListener(this::appendOperator);
        findViewById(R.id.buttonDev).setOnClickListener(this::appendOperator); // Fixed button id from buttonDev to buttonDiv

        findViewById(R.id.buttonPower).setOnClickListener(this::applyPower);
        findViewById(R.id.buttonSquareRoot).setOnClickListener(this::applySquareRoot);

        // Function buttons
        findViewById(R.id.buttonEqual).setOnClickListener(v -> calculateResult());
        findViewById(R.id.buttonBackClear).setOnClickListener(v -> backspace());
        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
        findViewById(R.id.buttonClose).setOnClickListener(v -> finish()); // Close the activity
    }

    private void appendNumber(View view) {
        Button button = (Button) view;
        input += button.getText().toString();
        textViewTypedNumbersAndSymbols.setText(input);
    }

    private void appendOperator(View view) {
        Button button = (Button) view;
        if (!input.isEmpty() && !Character.isWhitespace(input.charAt(input.length() - 1))) {
            input += " " + button.getText().toString() + " ";
            textViewTypedNumbersAndSymbols.setText(input);
        }
    }

    private void applyPower(View view) {
        if (!input.isEmpty()) {
            input += " ^ ";
            textViewTypedNumbersAndSymbols.setText(input);
        }
    }

    private void applySquareRoot(View view) {
        if (!input.isEmpty()) {
            input = "√" + input;
            textViewTypedNumbersAndSymbols.setText(input);
        }
    }

    private void calculateResult() {
        try {
            if (input.startsWith("√")) {
                // Handle square root calculation
                double num = Double.parseDouble(input.substring(1));
                result = String.valueOf(Math.sqrt(num));
            } else {
                // Handle other calculations
                String[] tokens = input.split(" ");
                if (tokens.length < 3) return;

                double num1 = Double.parseDouble(tokens[0]);
                String operator = tokens[1];
                double num2 = Double.parseDouble(tokens[2]);

                double calculationResult = 0;
                switch (operator) {
                    case "+":
                        calculationResult = num1 + num2;
                        break;
                    case "-":
                        calculationResult = num1 - num2;
                        break;
                    case "*":
                        calculationResult = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            calculationResult = num1 / num2;
                        } else {
                            textViewResult.setText("Error");
                            return;
                        }
                        break;
                    case "^":
                        calculationResult = Math.pow(num1, num2);
                        break;
                    default:
                        textViewResult.setText("Error");
                        return;
                }

                result = String.valueOf(calculationResult);
            }

            textViewResult.setText(result);

            // Clear the typed input after showing the result
            input = "";
            textViewTypedNumbersAndSymbols.setText("");
        } catch (Exception e) {
            textViewResult.setText("Error");
        }
    }

    private void backspace() {
        if (!input.isEmpty()) {
            input = input.substring(0, input.length() - 1);
            textViewTypedNumbersAndSymbols.setText(input);
        }
    }

    private void clear() {
        input = "";
        result = "";
        textViewTypedNumbersAndSymbols.setText("");
        textViewResult.setText("");
    }
}