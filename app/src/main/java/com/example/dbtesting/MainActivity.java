package com.example.dbtesting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseAccess db;
    EditText name,email,address;
    Button btnInsert,btnView,btnUpdate,btnDelete;
    TextView tname,temail,taddress;
    String ename,eEmail,eAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name =findViewById(R.id.editText);
        email=findViewById(R.id.editText2);
        address=findViewById(R.id.editText3);
        btnInsert=findViewById(R.id.button);
        btnView = findViewById(R.id.button2);
        btnUpdate = findViewById(R.id.button3);
        btnDelete = findViewById(R.id.button4);

        btnInsert.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        tname=findViewById(R.id.textView);
        temail=findViewById(R.id.textView2);
        taddress=findViewById(R.id.textView3);

        this.db = DatabaseAccess.getInstance(getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        if(v==btnInsert) {
            if (name.getText().toString().equals("") || email.getText().toString().equals("") || address.getText().toString().equals("")) {
                new AlertDialog.Builder(this)
                        .setMessage("Do you want to exit?")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .show();
            } else {
                insert();
                Toast.makeText(this, "Insert DB successfully", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v==btnView){
            System.err.println("view >>>>");
            List<Employee> list = getLastEmplopyee();
            Employee emp = new Employee();
            emp = list.get(list.size()-1);
            ename = emp.getName();
            eEmail = emp.getEmail();
            eAddress = emp.getAddress();

            tname.setText(ename);
            temail.setText(eEmail);
            taddress.setText(eAddress);
        }
        else if(v==btnUpdate){
            updateEmployee();
        }
        else if(v==btnDelete){
            deleteAllEmployee();
            tname.setText("name");
            temail.setText("email");
            taddress.setText("address");
        }
    }

    public void insert(){
        db.open();
        Employee employee=new Employee();
        employee.setName(name.getText().toString());
        employee.setEmail(email.getText().toString());
        employee.setAddress(address.getText().toString());
        System.err.println("insert>>>>>");
        db.insertEmployee(employee);
        db.close();

    }
    public List<Employee> getLastEmplopyee(){
        List<Employee> list = new ArrayList<Employee>();
        db.open();
        list = db.getEmployee();
        db.close();
        return list;
    }

    public void updateEmployee() {
        db.open();
        Employee newEmployee = new Employee();
        newEmployee.setName(name.getText().toString());
        newEmployee.setEmail(email.getText().toString());
        newEmployee.setAddress(address.getText().toString());

        List<Employee> elist = db.getEmployee();
        Employee oldep = elist.get(elist.size()-1);
        db.updateEmployee(oldep,newEmployee);
        db.close();

    }

    public void deleteAllEmployee()
    {
        db.open();
        db.deleteEmployee();
        db.close();
    }
}
