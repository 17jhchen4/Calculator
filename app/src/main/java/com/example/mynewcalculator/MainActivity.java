package com.example.mynewcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9;
    private Button btn_sin,btn_cos,btn_tan,btn_lg,btn_C,btn_backspace,btn_add,btn_sub,btn_multi,btn_division;
    private Button btn_point,btn_inverse,btn_equal;
    private TextView tv_show,tv_result;

    private Boolean isFinish = false;

    private Stack<String> op_stack,num_stack;

    private ShakeDetector shakeDetector;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        BindBtnWithListener();
        shakeDetector.registerOnShakeListener(new ShakeDetector.OnShakeListener()
        {
            @Override
            public void onShake()
            {
                shakeDetector.stop();
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setMessage("确定要切换成计税模式吗?")
                        .setPositiveButton("是的", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("我再想想", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                shakeDetector.start();
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener()
                        {
                            @Override
                            public void onDismiss(DialogInterface dialog)
                            {
                                shakeDetector.start();
                            }
                        }).create();
                dialog.show();
            }
        });
        shakeDetector.start();
    }

    private void Init()
    {
        btn_0 = findViewById(R.id.num0_btn);
        btn_1 = findViewById(R.id.num1_btn);
        btn_2 = findViewById(R.id.num2_btn);
        btn_3 = findViewById(R.id.num3_btn);
        btn_4 = findViewById(R.id.num4_btn);
        btn_5 = findViewById(R.id.num5_btn);
        btn_6 = findViewById(R.id.num6_btn);
        btn_7 = findViewById(R.id.num7_btn);
        btn_8 = findViewById(R.id.num8_btn);
        btn_9 = findViewById(R.id.num9_btn);

        btn_sin = findViewById(R.id.sin_btn);
        btn_cos = findViewById(R.id.cos_btn);
        btn_tan = findViewById(R.id.tan_btn);
        btn_lg = findViewById(R.id.lg_btn);
        btn_C = findViewById(R.id.clear_btn);
        btn_backspace = findViewById(R.id.backspace_btn);
        btn_add = findViewById(R.id.op_add_btn);
        btn_sub = findViewById(R.id.op_sub_btn);
        btn_multi = findViewById(R.id.op_multi_btn);
        btn_division = findViewById(R.id.op_division_btn);

        btn_point = findViewById(R.id.point_btn);
        btn_inverse = findViewById(R.id.inverse_btn);
        btn_equal = findViewById(R.id.op_equal_btn);

        tv_show = findViewById(R.id.show_tv);
        tv_result = findViewById(R.id.result_tv);

        op_stack = new Stack<>();
        num_stack = new Stack<>();

        shakeDetector = new ShakeDetector(this);
    }

    private void BindBtnWithListener()
    {
        btn_0.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("0");
            }
        });

        btn_1.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("1");
            }
        });

        btn_2.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("2");
            }
        });

        btn_3.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("3");
            }
        });

        btn_4.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("4");
            }
        });

        btn_5.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("5");
            }
        });

        btn_6.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("6");
            }
        });

        btn_7.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("7");
            }
        });

        btn_8.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("8");
            }
        });

        btn_9.setOnClickListener(new MyOnCilckListener()
        {
            @Override
            public void onClick(View v)
            {
                super.onClick(v);
                tv_show.append("9");
            }
        });

        btn_point.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    tv_show.setText("0.");
                    tv_result.setText("");
                    isFinish = false;
                }
                else
                {
                    if (tv_show.getText().toString().isEmpty() || tv_show.getText().toString().equals("0."))
                    {
                        tv_show.setText("0.");
                    }
                    else
                    {
                        tv_show.append(".");
                    }
                }
            }
        });

        btn_inverse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    if (temp.charAt(0) == '-' && temp.charAt(0) == '(')
                    {
                        temp = temp.substring(2, temp.length()-2);
                    }
                    else
                    {
                        temp = "-(" + temp;
                    }
                    tv_show.setText(temp);
                    isFinish = false;
                }
                else
                {
                    String temp = tv_show.getText().toString();
                    if (!temp.isEmpty())
                    {
                        if (temp.charAt(0) == '-' && temp.charAt(0) == '(')
                        {
                            temp = temp.substring(2, temp.length()-2);
                        }
                        else
                        {
                            temp = "-(" + temp;
                        }
                    }
                    tv_show.setText(temp);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText(temp + "+");
                    isFinish = false;
                }
                else
                {
                    tv_show.append("+");
                }
            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText(temp + "-");
                    isFinish = false;
                }
                else
                {
                    tv_show.append("-");
                }
            }
        });

        btn_multi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText(temp + "×");
                    isFinish = false;
                }
                else
                {
                    tv_show.append("×");
                }
            }
        });

        btn_division.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText(temp + "÷");
                    isFinish = false;
                }
                else
                {
                    tv_show.append("÷");
                }
            }
        });

        btn_sin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText("sin(" + temp);
                    isFinish = false;
                }
                else
                {
                    tv_show.append("sin(");
                }
            }
        });

        btn_cos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText("cos(" + temp);
                    isFinish = false;
                }
                else
                {
                    tv_show.append("cos(");
                }
            }
        });

        btn_tan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText("tan(" + temp);
                    isFinish = false;
                }
                else
                {
                    tv_show.append("tan(");
                }
            }
        });

        btn_lg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    String temp = tv_result.getText().toString();
                    tv_result.setText("");
                    tv_show.setText("lg(" + temp);
                    isFinish = false;
                }
                else
                {
                    tv_show.append("lg(");
                }
            }
        });

        btn_C.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv_result.setText("");
                tv_show.setText("");
                isFinish = false;
            }
        });

        btn_backspace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFinish)
                {
                    tv_show.setText("");
                    tv_result.setText("");
                    isFinish = false;
                }
                else
                {
                    String temp = tv_show.getText().toString();
                    if (temp.length() != 1)
                    {
                        if (temp.charAt(temp.length()-1) == '(')
                        {
                            temp = temp.substring(0, temp.length()-3);
                            if (temp.isEmpty())
                            {
                                tv_show.setText("");
                                return;
                            }
                            if (Character.isLetter(temp.charAt(temp.length()-1)))
                            {
                                temp = temp.substring(0, temp.length()-1);
                            }
                        }
                        else
                        {
                            temp = temp.substring(0, temp.length()-1);
                        }
                    }
                    else
                    {
                        temp = "";
                    }
                    tv_show.setText(temp);
                }
            }
        });

        btn_equal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!isFinish)
                {
                    if (tv_show.getText().toString().equals(""))
                    {
                        Toast.makeText(MainActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String temp = tv_show.getText().toString()+"#";
                        String str = "";
                        int index = 0;
                        if (Character.isDigit(temp.charAt(index)))
                            {
                                while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                {
                                    str = str + temp.charAt(index);
                                    index++;
                                }
                            }
                        else if (temp.charAt(index) == '+' || temp.charAt(index) == '-'
                                        || temp.charAt(index) == '×' || temp.charAt(index) == '÷')
                            {
                                str = temp.charAt(index) + "";
                                index++;
                            }
                        else if (temp.charAt(index) == 's' || temp.charAt(index) == 'c'
                                || temp.charAt(index) == 't')
                        {
                            str = temp.substring(index, index+4);
                            index = index + 4;
                        }
                        else if (temp.charAt(index) == 'l')
                        {
                            str = temp.substring(index, index+3);
                            index = index + 3;
                        }
                        else if (temp.charAt(index) == '#')
                        {
                            str = "#";
                        }
                        op_stack.push("#");
                        while(!str.equals("#") || !op_stack.peek().equals("#"))
                        {
                            //利用堆栈实现简单计算
                            if (Character.isDigit(str.charAt(0)))
                            {
                                num_stack.push(str);
                                str = "";
                                if (Character.isDigit(temp.charAt(index)))
                                {
                                    while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                    {
                                        str = str + temp.charAt(index);
                                        index++;
                                    }
                                }
                                else if (temp.charAt(index) == '+' || temp.charAt(index) == '×' || temp.charAt(index) == '÷')
                                {
                                    str = temp.charAt(index) + "";
                                    index++;
                                }
                                else if (temp.charAt(index) == '-')
                                {
                                    if (temp.charAt(index+1) == '-')
                                    {
                                        str = temp.substring(index, index+2);
                                        index = index +2;
                                    }
                                    else
                                    {
                                        str = temp.charAt(index) + "";
                                        index++;
                                    }
                                }
                                else if (temp.charAt(index) == 's' || temp.charAt(index) == 'c'
                                        || temp.charAt(index) == 't')
                                {
                                    str = temp.substring(index, index+4);
                                    index = index + 4;
                                }
                                else if (temp.charAt(index) == 'l')
                                {
                                    str = temp.substring(index, index+3);
                                    index = index + 3;
                                }
                                else if (temp.charAt(index) == '#')
                                {
                                    str = "#";
                                }
                            }
                            else
                            {
                                switch (Precede(op_stack.peek(), str))
                                {
                                    case "<":
                                        op_stack.push(str);
                                        str = "";
                                        if (Character.isDigit(temp.charAt(index)))
                                        {
                                            while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                            {
                                                str = str + temp.charAt(index);
                                                index++;
                                            }
                                        }
                                        else if (temp.charAt(index) == '+' || temp.charAt(index) == '-'
                                                || temp.charAt(index) == '×' || temp.charAt(index) == '÷')
                                        {
                                            str = temp.charAt(index) + "";
                                            index++;
                                        }
                                        else if (temp.charAt(index) == 's' || temp.charAt(index) == 'c'
                                                || temp.charAt(index) == 't')
                                        {
                                            str = temp.substring(index, index+4);
                                            index = index + 4;
                                        }
                                        else if (temp.charAt(index) == 'l')
                                        {
                                            str = temp.substring(index, index+3);
                                            index = index + 3;
                                        }
                                        else if (temp.charAt(index) == '#')
                                        {
                                            str = "#";
                                        }
                                        break;

                                    case "=":
                                        op_stack.pop();
                                        str = "";
                                        if (Character.isDigit(temp.charAt(index)))
                                        {
                                            while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                            {
                                                str = str + temp.charAt(index);
                                                index++;
                                            }
                                        }
                                        else if (temp.charAt(index) == '+' || temp.charAt(index) == '-'
                                                || temp.charAt(index) == '×' || temp.charAt(index) == '÷')
                                        {
                                            str = temp.charAt(index) + "";
                                            index++;
                                        }
                                        else if (temp.charAt(index) == 's' || temp.charAt(index) == 'c'
                                                || temp.charAt(index) == 't')
                                        {
                                            str = temp.substring(index, index+4);
                                            index = index + 4;
                                        }
                                        else if (temp.charAt(index) == 'l')
                                        {
                                            str = temp.substring(index, index+3);
                                            index = index + 3;
                                        }
                                        else if (temp.charAt(index) == '#')
                                        {
                                            str = "#";
                                        }
                                        break;

                                    case ">":
                                        String theta = op_stack.pop();
                                        String b = num_stack.pop();
                                        String a = num_stack.pop();
                                        //进行四则运算
                                        num_stack.push(Operate(a, theta, b));
                                        break;
                                    case "true":
                                        if (str.equals("sin("))
                                        {
                                            str = "";
                                            if (Character.isDigit(temp.charAt(index)))
                                            {
                                                while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                                {
                                                    str = str + temp.charAt(index);
                                                    index++;
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(MainActivity.this, "缺少操作数！", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            double d = Double.valueOf(str);
                                            d = Math.sin(d);
                                            num_stack.push(d + "");
                                        }
                                        else if (str.equals("cos("))
                                        {
                                            str = "";
                                            if (Character.isDigit(temp.charAt(index)))
                                            {
                                                while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                                {
                                                    str = str + temp.charAt(index);
                                                    index++;
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(MainActivity.this, "缺少操作数！", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            double d = Double.valueOf(str);
                                            num_stack.push(Math.cos(d)+"");
                                        }
                                        else if (str.equals("tan("))
                                        {
                                            str = "";
                                            if (Character.isDigit(temp.charAt(index)))
                                            {
                                                while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                                {
                                                    str = str + temp.charAt(index);
                                                    index++;
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(MainActivity.this, "缺少操作数！", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            double d = Double.valueOf(str);
                                            num_stack.push(Math.tan(d)+"");
                                        }
                                        else
                                        {
                                            str = "";
                                            if (Character.isDigit(temp.charAt(index)))
                                            {
                                                while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                                {
                                                    str = str + temp.charAt(index);
                                                    index++;
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(MainActivity.this, "缺少操作数！", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            double d = Double.valueOf(str);
                                            num_stack.push(Math.log10(d)+"");
                                        }

                                        if (Character.isDigit(temp.charAt(index)))
                                        {
                                            while (Character.isDigit(temp.charAt(index)) || temp.charAt(index) == '.')
                                            {
                                                str = str + temp.charAt(index);
                                                index++;
                                            }
                                        }
                                        else if (temp.charAt(index) == '+' || temp.charAt(index) == '-'
                                                || temp.charAt(index) == '×' || temp.charAt(index) == '÷')
                                        {
                                            str = temp.charAt(index) + "";
                                            index++;
                                        }
                                        else if (temp.charAt(index) == 's' || temp.charAt(index) == 'c'
                                                || temp.charAt(index) == 't')
                                        {
                                            str = temp.substring(index, index+4);
                                            index = index + 4;
                                        }
                                        else if (temp.charAt(index) == 'l')
                                        {
                                            str = temp.substring(index, index+3);
                                            index = index + 3;
                                        }
                                        else if (temp.charAt(index) == '#')
                                        {
                                            str = "#";
                                        }

                                        break;
                                }

                            }
                        }
                        tv_result.setText(num_stack.peek());
                        isFinish = true;
                        num_stack.clear();
                        op_stack.clear();
                    }
                }
            }
        });
    }

    private String Operate(String str1, String theta, String str2)
    {
        double a = Double.valueOf(str1);
        double b = Double.valueOf(str2);
        double result = 0;

        switch (theta)
        {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "×":
                result = a * b;
                break;
            case "÷":
                result = a / b;
                break;
        }
        if (result % 1 == 0)
        {
            return (int)result + "";
        }
        else
        {
            return result + "";
        }
    }

    private String Precede(String top, String str)
    {
        if (str.equals("sin(") || str.equals("cos(") || str.equals("tan(") || str.equals("lg(") || str.equals("-("))
        {
            return "true";
        }
        else if (top.equals("+") || top.equals("-"))
        {
            if (str.equals("+") || str.equals("-") || str.equals(")") || str.equals("#"))
            {
                return ">";
            }
            else
            {
                return "<";
            }
        }
        else if (top.equals("×") || top.equals("÷"))
        {
            if (str.equals("("))
            {
                return "<";
            }
            else
            {
                return ">";
            }
        }
        else if (top.equals("("))
        {
            if (str.equals("+") || str.equals("-") || str.equals("×") || str.equals("÷"))
            {
                return "<";
            }
            else if (str.equals(")"))
            {
                return "=";
            }
        }
        else if (top.equals(")"))
        {
            if (!str.equals("("))
            {
                return ">";
            }
        }
        else
        {
            if (!str.equals("#"))
            {
                return "<";
            }
            else
            {
                return "= ";
            }
        }
        return "none";
    }


    class MyOnCilckListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if (isFinish)
            {
                tv_show.setText("");
                tv_result.setText("");
                isFinish = false;
            }
        }
    }
}
