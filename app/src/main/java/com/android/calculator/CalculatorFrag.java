package com.android.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorFrag extends Fragment {

    private TextView input;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnClear;
    private Button btnDel;
    private Button btnPercent;
    private Button btnDiv;
    private Button btnMul;
    private Button btnSub;
    private Button btnAdd;
    private Button btnResult;
    private Button btnDot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculator_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initCalcButtons();
        btnListeners();
    }

    public void initCalcButtons() {
        input = getView().findViewById(R.id.inputTV);
        btn0 = getView().findViewById(R.id.btn0);
        btn1 = getView().findViewById(R.id.btn1);
        btn2 = getView().findViewById(R.id.btn2);
        btn3 = getView().findViewById(R.id.btn3);
        btn4 = getView().findViewById(R.id.btn4);
        btn5 = getView().findViewById(R.id.btn5);
        btn6 = getView().findViewById(R.id.btn6);
        btn7 = getView().findViewById(R.id.btn7);
        btn8 = getView().findViewById(R.id.btn8);
        btn9 = getView().findViewById(R.id.btn9);
        btnClear = getView().findViewById(R.id.btnC);
        btnDel = getView().findViewById(R.id.btnDel);
        btnPercent = getView().findViewById(R.id.btnPercent);
        btnDiv = getView().findViewById(R.id.btnDiv);
        btnMul = getView().findViewById(R.id.btnMul);
        btnSub = getView().findViewById(R.id.btnSub);
        btnAdd = getView().findViewById(R.id.btnAdd);
        btnResult = getView().findViewById(R.id.btnRes);
        btnDot = getView().findViewById(R.id.btnDot);
    }

    public void btnListeners() {
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().equals("0") && input.getText().toString().length() >= 1) {
                    input.setText(input.getText().toString() + "0");
                } else if (input.getText().toString().length() == 0) {
                    input.setText("0");
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                str = hasZeroAfterOp(str);
                input.setText(str + "9");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = input.getText().toString();
                if (str.length() > 1) {
                    str = str.substring(0, str.length() - 1);
                    input.setText(str);
                } else {
                    input.setText("");
                }
            }
        });

        btnPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = input.getText().toString();

                if (str.length() == 1) {     //if input == "+" etc
                    if (!Character.isDigit(str.charAt(0))) {
                        input.setText("");
                        return;
                    }
                }

                if (str.contains("+") || str.contains("×") || str.contains("−") || str.contains("÷")) {
                    return;
                }

                if (str.length() > 0) {
                    char finalChar = str.charAt(str.length() - 1);

                    if (finalChar == '+' || finalChar == '×' || finalChar == '−' || finalChar == '÷') {
                        str = str.substring(0, str.length() - 1);
                    }

                    if (!str.equals("")) {
                        double num = Double.parseDouble(str) / 100.0f;
                        String temp = String.valueOf(num);
                        input.setText(temp);
                    }
                }
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation("÷");
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation("×");
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation("−");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation("+");
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = input.getText().toString();
                if (in.length() != 0) {
                    in = in.replace("÷", "/");
                    in = in.replace("×", "*");
                    in = in.replace("−", "-");

                    if (in.length() == 1) {     //if input == "+" etc
                        if (!Character.isDigit(in.charAt(0))) {
                            input.setText("");
                            return;
                        }
                    }

                    if (!Character.isDigit(in.charAt(in.length()-1))) {
                        in = in.substring(0, in.length()-1);
                    }

                    Expression exp = new ExpressionBuilder(in).build();
                    try {
                        double result = exp.evaluate();
                        String out = String.valueOf(result);
                        if (result % 1 == 0) {
                            out = String.valueOf((int) result);
                        }
                        input.setText(out);
                    } catch (ArithmeticException e) {
                        input.setText("");
                        Toast.makeText(getActivity(), "Division by zero", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = input.getText().toString();

                if (!in.equals("")) {     //if in != "."
                    if (!Character.isDigit(in.charAt(in.length() - 1))) {     //if previous char is operator, then dot won't be added
                        return;
                    }
                }

                boolean dotFound = false;
                for (int i = in.length()-1; i >= 0; i--) {
                    if (in.charAt(i) ==  '+' || in.charAt(i) == '×' || in.charAt(i) == '−' || in.charAt(i) == '÷') {
                        break;
                    }
                    if (in.charAt(i) == '.') {
                        dotFound = true;
                        break;
                    }
                }

                if (!dotFound) {
                    if (input.getText().toString().equals("")) {
                        input.setText("0.");
                    } else {
                        input.setText(input.getText().toString() + ".");
                    }
                }
            }
        });
    }

    public String startsWithZero() {        //if input is 0, pressing 1, 0 will be replaced by 1
        String str = input.getText().toString();

        if (str.length() == 1) {
            if (str.charAt(0) == '0') {
                str = "";
            }
        }
        return str;
    }

    public String hasZeroAfterOp(String in) {   //avoid input like 9/003

        if (in.length() > 1) {
            char op = in.charAt(in.length() - 2);
            if (op == '+' || op == '×' || op == '−' || op == '÷') {
                if (in.charAt(in.length()-1) == '0') {
                    in = in.substring(0, in.length() - 1);
                }
            }
        }
        return in;
    }

    public void operation(String op) {
        String str = input.getText().toString();

        if (str.equals("") && op.equals("−")) {
            input.setText("−");
            return;
        }

        if (str.length() > 0) {
            char finalChar = str.charAt(str.length() - 1);

            if (finalChar == '+' || finalChar == '×' || finalChar == '−' || finalChar == '÷') {
                str = str.substring(0, str.length() - 1) + op;
                input.setText(str);
            } else {
                input.setText(str + op);
            }
        }
    }
}