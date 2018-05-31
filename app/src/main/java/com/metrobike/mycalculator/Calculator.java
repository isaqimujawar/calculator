package com.metrobike.mycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {
    private TextView textView;
    private String display = "";
    private String currentOperator = "";
    private String result = "";
    Button btnEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(display);
        btnEqual = (Button) findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runThread();
            }
        });
    }

    private void updateScreen() {
        textView.setText(display);
    }

    public void onClickNumber(View v) {

        if (result != "") {
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display = display + b.getText();
        updateScreen();
    }

    private boolean isOperator(char op) {
        switch (op) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '.':
            case '^':
            case '%':
                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View v) {
        if (display == "") return;

        Button b = (Button) v;

        if (result != "") {
            String _display = result;
            clear();
            display = _display;
        }

        if (currentOperator != "") {
            if (isOperator(display.charAt(display.length() - 1))) {
                display = display.replace(display.charAt(display.length() - 1), b.getText().charAt(0));
                updateScreen();
                return;
            }
            currentOperator = b.getText().toString();
        }
        display = display + b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear() {
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View v) {
        clear();
        updateScreen();
    }


    private boolean getResult() {
        if (currentOperator == "") return false;
        EvaluateString.Result res = EvaluateString.evalString(display);
        result = res.toString();
        return true;
    }

    public void onClickDelete(View v) {
        if (display.length() > 0)
            display = display.substring(0, display.length() - 1);
        updateScreen();
    }

    private void runThread() {
        new Thread() {
            public void run() {
                if (display == "") return;
                if (!getResult()) return;
                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textView.setText(display + "\n" + "=" + String.valueOf(result));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}