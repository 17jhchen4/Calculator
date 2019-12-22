package com.example.mynewcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity
{

    private Spinner spinner;
    private List<String> data_list;
    private List<Integer> tax_list;
    private ArrayAdapter<String> arr_adapter;

    private Button count_btn, again_btn;
    private EditText input_et;
    private TextView salary_tv, result_tv;

    private Boolean isFinish = false;
    private int index = 0;

    private ShakeDetector shakeDetector;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        spinner = findViewById(R.id.spinner);
        count_btn = findViewById(R.id.count_tax_btn);
        again_btn = findViewById(R.id.again_btn);
        input_et = findViewById(R.id.before_tax_et);
        salary_tv = findViewById(R.id.after_tax_tv);
        result_tv = findViewById(R.id.show_tv);

        shakeDetector = new ShakeDetector(this);

        tax_list = new ArrayList<>();
        tax_list.add(5000);
        tax_list.add(3500);
        tax_list.add(5000);
        tax_list.add(5000);
        tax_list.add(5000);

        //数据
        data_list = new ArrayList<>();
        data_list.add("北京");
        data_list.add("上海");
        data_list.add("广州");
        data_list.add("深圳");
        data_list.add("汕头");

        //适配器
        arr_adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                Toast.makeText(Main2Activity.this, "您选择的是 ："+data_list.get(arg2), Toast.LENGTH_SHORT).show();
                index = arg2;
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

        again_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isFinish = false;
                result_tv.setText("");
                salary_tv.setText("");
                input_et.setText("");
            }
        });

        count_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String temp = input_et.getText().toString();
                if (temp.isEmpty())
                {
                    Toast.makeText(Main2Activity.this, "请输入您的税前工资!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    double salary = Double.valueOf(temp);
                    if (!isFinish)
                    {
                        isFinish = true;
                        double pension = salary * 0.08;
                        double medical = salary * 0.02;
                        double unemploy = salary * 0.005;
                        double house = salary * 0.07;
                        double person_tax = 0;
                        salary = salary - pension - medical - unemploy - house;
                        if (salary > tax_list.get(index))
                        {
                            if (salary <= 36000)
                            {
                                person_tax = salary * 0.03;
                            }
                            else if (salary <= 144000)
                            {
                                person_tax = salary * 0.1;
                            }
                            else if (salary <= 300000)
                            {
                                person_tax = salary * 0.2;
                            }
                            else if (salary <= 420000)
                            {
                                person_tax = salary * 0.25;
                            }
                            else if (salary <= 660000)
                            {
                                person_tax = salary * 0.3;
                            }
                            else if (salary <= 960000)
                            {
                                person_tax = salary * 0.35;
                            }
                            else
                            {
                                person_tax = salary * 0.45;
                            }
                        }
                        salary = salary - person_tax;
                        salary_tv.setText(salary+"");
                        result_tv.setText("\n养老保险金：  "+pension +"(8%)\n\n");
                        result_tv.append("医疗保险金：  "+medical +"(2%)\n\n");
                        result_tv.append("失业保险金：  "+unemploy +"(0.5%)\n\n");
                        result_tv.append("基本住房公积金：  "+house +"(7%)\n\n");
                        result_tv.append("个人所得税：  "+person_tax +"\n\n");
                    }
                }
            }
        });

        shakeDetector.registerOnShakeListener(new ShakeDetector.OnShakeListener()
        {
            @Override
            public void onShake()
            {
                shakeDetector.stop();
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setMessage("确定要切换成普通模式吗?")
                        .setPositiveButton("是的", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
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
}
