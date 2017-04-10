package com.example.lenovo.planner.UserHome;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lenovo.planner.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BudgetCalculator extends AppCompatActivity {
    Button selectAll, calculate;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5,checkBox6,
            checkBox7, checkBox8, checkBox9, checkBox10, checkBox11,checkBox12;
    EditText suggestedEditText1, suggestedEditText2, suggestedEditText3, suggestedEditText4, suggestedEditText5,suggestedEditText6,
            suggestedEditText7, suggestedEditText8, suggestedEditText9, suggestedEditText10,suggestedEditText11, suggestedEditText12;
    EditText amountEditText1, amountEditText2, amountEditText3, amountEditText4, amountEditText5,amountEditText6,
            amountEditText7, amountEditText8, amountEditText9,amountEditText10, amountEditText11, amountEditText12;
    EditText edittextBudget;
    EditText[] amount;
    EditText[] suggested;
    CheckBox[] checkBoxes;
    int item;

    TableLayout tableLayout = null;

    List<Integer> totalItem=new ArrayList<>();
    int count;
    int selectedItem=0;
    int[] percent={12,8,4,6,7,11,15,5,14,5,6,7};
    float[] value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_calculator);
        tableLayout = (TableLayout) findViewById(R.id.table_layout);
        int rowCount = tableLayout.getChildCount();
        rowCount = rowCount - 1;
        totalItem.add(rowCount);
      //  Toast.makeText(getApplicationContext(), "total item is" + rowCount, Toast.LENGTH_SHORT).show();

        addListenerOnButtonClick();

        selectAll = (Button) findViewById(R.id.button_select);

        checkBox1 = (CheckBox) findViewById(R.id.check_1);
        checkBox2 = (CheckBox) findViewById(R.id.check_2);
        checkBox3 = (CheckBox) findViewById(R.id.check_3);
        checkBox4 = (CheckBox) findViewById(R.id.check_4);
        checkBox5 = (CheckBox) findViewById(R.id.check_5);
        checkBox6 = (CheckBox) findViewById(R.id.check_6);
        checkBox7 = (CheckBox) findViewById(R.id.check_7);
        checkBox8 = (CheckBox) findViewById(R.id.check_8);
        checkBox9 = (CheckBox) findViewById(R.id.check_9);
        checkBox10 = (CheckBox) findViewById(R.id.check_10);
        checkBox11 = (CheckBox) findViewById(R.id.check_11);
        checkBox12 = (CheckBox) findViewById(R.id.check_12);
//        checkBox6 = (CheckBox) findViewById(R.id.check_6);
//        checkBox7 = (CheckBox) findViewById(R.id.check_7);
//        checkBox8 = (CheckBox) findViewById(R.id.check_8);
//        checkBox9 = (CheckBox) findViewById(R.id.check_9);
        checkBoxes=new CheckBox[]{checkBox1, checkBox2, checkBox3, checkBox4, checkBox5,checkBox6,
                checkBox7, checkBox8, checkBox9, checkBox10 ,checkBox11, checkBox12};

        item=checkBoxes.length;
        value=new float[item];

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checked = ((ToggleButton) v).isChecked();
                if (checked) {
                    for (CheckBox box:checkBoxes) {
                        box.setChecked(true);
                    }

                } else {
                    for (CheckBox box:checkBoxes) {
                        box.setChecked(false);
                    }
                }
            }

        });
        for (CheckBox box:checkBoxes) {
            box.setOnCheckedChangeListener(new CheckedChangeListener());
        }
    }

    public void addListenerOnButtonClick() {


        edittextBudget = (EditText) findViewById(R.id.edittext_budget);
        calculate = (Button) findViewById(R.id.button_calculate);

        suggestedEditText1 = (EditText) findViewById(R.id.suggested_edittext1);
        suggestedEditText2 = (EditText) findViewById(R.id.suggested_edittext2);
        suggestedEditText3 = (EditText) findViewById(R.id.suggested_edittext3);
        suggestedEditText4 = (EditText) findViewById(R.id.suggested_edittext4);
        suggestedEditText5 = (EditText) findViewById(R.id.suggested_edittext5);
        suggestedEditText6 = (EditText) findViewById(R.id.suggested_edittext6);
        suggestedEditText7 = (EditText) findViewById(R.id.suggested_edittext7);
        suggestedEditText8 = (EditText) findViewById(R.id.suggested_edittext8);
        suggestedEditText9 = (EditText) findViewById(R.id.suggested_edittext9);
        suggestedEditText10 = (EditText) findViewById(R.id.suggested_edittext10);
        suggestedEditText11 = (EditText) findViewById(R.id.suggested_edittext11);
        suggestedEditText12 = (EditText) findViewById(R.id.suggested_edittext12);
        suggested= new EditText[]{suggestedEditText1, suggestedEditText2, suggestedEditText3,suggestedEditText4, suggestedEditText5, suggestedEditText6,
                suggestedEditText7, suggestedEditText8, suggestedEditText9,suggestedEditText10, suggestedEditText11, suggestedEditText12};

        amountEditText1 = (EditText) findViewById(R.id.amount_edittext1);
        amountEditText2 = (EditText) findViewById(R.id.amount_edittext2);
        amountEditText3 = (EditText) findViewById(R.id.amount_edittext3);
        amountEditText4 = (EditText) findViewById(R.id.amount_edittext4);
        amountEditText5 = (EditText) findViewById(R.id.amount_edittext5);
        amountEditText6 = (EditText) findViewById(R.id.amount_edittext6);
        amountEditText7 = (EditText) findViewById(R.id.amount_edittext7);
        amountEditText8 = (EditText) findViewById(R.id.amount_edittext8);
        amountEditText9 = (EditText) findViewById(R.id.amount_edittext9);
        amountEditText10 = (EditText) findViewById(R.id.amount_edittext10);
        amountEditText11 = (EditText) findViewById(R.id.amount_edittext11);
        amountEditText12 = (EditText) findViewById(R.id.amount_edittext12);
        amount= new EditText[]{amountEditText1, amountEditText2, amountEditText3,amountEditText4, amountEditText5, amountEditText6,
                amountEditText7, amountEditText8, amountEditText9,amountEditText10, amountEditText11, amountEditText12};

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetText=edittextBudget.getText().toString();
                count=selectedItem;
                if(!budgetText.isEmpty()) {

                    float budget = Float.parseFloat(budgetText);
                    float budgetLeft=budget;
                    for (int i = 0; i <item ; i++) {
                        value[i]=0;
                    }
                    for (int i = 0; i < item; i++) {
                        String amt = amount[i].getText().toString();
                        if (!amt.isEmpty() && checkBoxes[i].isChecked()) {
                            //amount assigned by user
                            value[i] = Float.parseFloat(amt);
                            budgetLeft = budgetLeft - value[i];
                            count--;
                        }
                    }
                    if (budgetLeft > 0) {
                        budget=budgetLeft;
                        for (int i = 0; i < item; i++) {
                            //slected items
                            if (checkBoxes[i].isChecked()) {
                                String amt = amount[i].getText().toString();
                                if (amt.isEmpty()) {                 //amount not  assigned
                                    value[i] = (float) (percent[i] / 100.0) * budget;           //calculate value
                                    budgetLeft = budgetLeft - value[i];
                                }

                            } else {
                                value[i] = 0;
                            }
                        }
                    }

                    if (budgetLeft > 0) {
                        float add = (float) budgetLeft / count;      //divide remaining amount
                        for (int i = 0; i < item; i++) {
                            String amt = amount[i].getText().toString();
                            if (checkBoxes[i].isChecked() && amt.isEmpty()) {
                                value[i] += add;
                            }
                        }
                    }
                    for (int i = 0; i <item ; i++) {
                        suggested[i].setText(String.valueOf(value[i]));
                    };
                }
                for (int i = 0; i <item ; i++) {
                    DecimalFormat precision = new DecimalFormat("0.00");
                    suggested[i].setText(precision.format(value[i]));
                }

            }
        });
    }

    class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){ selectedItem++; }
            else{ selectedItem--; }
//        Toast.makeText(getApplicationContext(),"selected item is" +selectedItem , Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
