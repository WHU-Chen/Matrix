package com.example.administrator.matrix;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private EditText[][] ed;
    private EditText jie;
    private TextView hang,rank;
    private Button hanglie,qiuni,hangToSimB,btnUpJie,btnToZero,btnToDouble;
    private int maxNum=5+1;
    private Matrix backUp,toDoubleBack;
    private Fraction hangLieNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed=new EditText[maxNum][maxNum+1];
        for(int i=1;i<maxNum;i++){
            ed[i]=new EditText[maxNum+1];
            for(int j=1;j<maxNum+1;j++){
                ed[i][j]= (EditText) findViewById(getResources().getIdentifier("editText"+i+""+j,"id","com.example.administrator.matrix"));//动态获取ID
                if(i>3||j>4)ed[i][j].setVisibility(View.GONE);
            }
        }
        backUp=null;
        jie= (EditText) findViewById(R.id.editTextJie);
        hanglie=(Button)findViewById(R.id.btnHang);
        qiuni=(Button)findViewById(R.id.btnNi);
        hang= (TextView) findViewById(R.id.textViewHang);
        hanglie.setOnClickListener(this);
        qiuni.setOnClickListener(this);
        rank= (TextView) findViewById(R.id.textViewRank);
        hangToSimB=(Button)findViewById(R.id.btnHangToSimWithB);
        hangToSimB.setOnClickListener(this);
        btnUpJie=(Button)findViewById(R.id.btnUpJie);
        btnUpJie.setOnClickListener(this);
        btnToZero= (Button) findViewById(R.id.btnToZero);
        btnToZero.setOnClickListener(this);
        btnToDouble=(Button)findViewById(R.id.btnToDouble);
        btnToDouble.setOnClickListener(this);
        hangLieNum=null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHang:
                calHang();
                break;
            case R.id.btnNi:
                calNi();
                break;
            case R.id.btnHangToSimWithB:
                toSimB();
                break;
            case R.id.btnUpJie:
                upJie();
                break;
            case R.id.btnToZero:
                toZero();
                break;
            case R.id.btnToDouble:
                toDouble();
                break;

        }
    }

    private void toDouble() {
        int jieNum=Integer.parseInt(jie.getText().toString())+1;
        Fraction e[]=new Fraction[100];
        for(int i=0;i<jieNum*(jieNum-1);i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            e[i]=Fraction.StringToFraction(ed[aRow][aCol].getText().toString());
        }
        toDoubleBack=new Matrix(e,jieNum,jieNum);
        for(int i=0;i<jieNum*(jieNum-1);i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            ed[aRow][aCol].setText(String.valueOf(e[i].toDouble()));
        }
        btnToDouble.setText("返回");
        qiuni.setVisibility(View.INVISIBLE);
        hanglie.setVisibility(View.INVISIBLE);
        hangToSimB.setVisibility(View.INVISIBLE);
        if(hangLieNum!=null){
            hang.setText("行列式为"+hangLieNum.toDouble());
        }
        btnToDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jieNum=Integer.parseInt(jie.getText().toString())+1;
                btnToDouble.setText("化为小数");
                btnToDouble.setOnClickListener(MainActivity.this);
                for(int i=0;i<jieNum*(jieNum-1);i++){
                    int aRow=i/jieNum+1,aCol=i%jieNum+1;
                    ed[aRow][aCol].setText(toDoubleBack.getElement(aRow,aCol).toString());
                }
                toDoubleBack=null;
                qiuni.setVisibility(View.VISIBLE);
                hanglie.setVisibility(View.VISIBLE);
                hangToSimB.setVisibility(View.VISIBLE);
                if(hangLieNum!=null){
                    hang.setText("行列式为"+hangLieNum);
                }
            }
        });
    }

    private View.OnClickListener toDoubleBack() {
        int jieNum=Integer.parseInt(jie.getText().toString())+1;
        btnToDouble.setText("化为小数");
        btnToDouble.setOnClickListener(MainActivity.this);
        for(int i=0;i<jieNum*(jieNum-1);i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            ed[aRow][aCol].setText(toDoubleBack.getElement(aRow,aCol).toString());
        }
        qiuni.setVisibility(View.VISIBLE);
        hanglie.setVisibility(View.VISIBLE);
        hangToSimB.setVisibility(View.VISIBLE);
        toDoubleBack=null;
        if(hangLieNum!=null){
            hang.setText("行列式为"+hangLieNum);
        }
        return null;
    }

    private void toZero() {
        if(toDoubleBack!=null){
            btnToDouble.setText("化为小数");
            btnToDouble.setOnClickListener(MainActivity.this);
            toDoubleBack=null;
            qiuni.setVisibility(View.VISIBLE);
            hanglie.setVisibility(View.VISIBLE);
            hangToSimB.setVisibility(View.VISIBLE);
        }
        int jieNum=Integer.parseInt(jie.getText().toString());
        for(int i=1;i<=jieNum;i++){
            for(int j=1;j<=jieNum+1;j++){
                ed[i][j].setText("0");
            }
        }
        hangLieNum=null;
    }
    private void upJie() {
        if(toDoubleBack!=null){
            toZero();
        }
        int jieNum=Integer.parseInt(jie.getText().toString());
        for(int i=1;i<maxNum;i++){
            for(int j=1;j<maxNum+1;j++){
                if(i>jieNum||j>jieNum+1)ed[i][j].setVisibility(View.GONE);
                else ed[i][j].setVisibility(View.VISIBLE);
            }
        }
        rank.setText("秩");
        hang.setText("行列式");
        hangToSimB.setText("带B的化简");
        hangLieNum=null;
        hangToSimB.setOnClickListener(MainActivity.this);
    }
    private void toSimB() {
        if(toDoubleBack!=null){
            toDoubleBack();
        }
        int jieNum=Integer.parseInt(jie.getText().toString())+1;
        if(jieNum>maxNum){
            jie.setText("5");
            return;
        }
        Fraction a[]=new Fraction[100];
        for(int i=0;i<jieNum*jieNum;i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            if(aRow>=6)a[i]=new Fraction(0);
            else a[i]=Fraction.StringToFraction(ed[aRow][aCol].getText().toString());
        }
        backUp=new Matrix(a,jieNum,jieNum);
        Matrix ans=backUp.simB();
        for(int i=0;i<jieNum*(jieNum-1);i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            ed[aRow][aCol].setText(ans.getElement(aRow, aCol).toString());
        }
        hangToSimB.setText("返回");
        hangToSimB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangToSimB.setText("带B的化简");
                for(int i=0;i<backUp.getCol()*backUp.getRow();i++){
                    int aRow=i/backUp.getCol()+1,aCol=i%backUp.getCol()+1;
                    if(aRow>=6)break;
                    ed[aRow][aCol].setText(backUp.getElement(aRow, aCol).toString());
                }
                hangToSimB.setOnClickListener(MainActivity.this);
            }
        });

    }
    private void calNi() {
        if(toDoubleBack!=null){
            toDoubleBack();
        }
        int jieNum=Integer.parseInt(jie.getText().toString());
        if(jieNum>maxNum){
            jie.setText("5");
            return;
        }
        Fraction a[]=new Fraction[100];
        for(int i=0;i<jieNum*jieNum;i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            a[i]=Fraction.StringToFraction(ed[aRow][aCol].getText().toString());
        }
        Matrix ans=new Matrix(a,jieNum,jieNum);
        hangLieNum = ans.hanglie();
        if(hangLieNum.is_zero()){
            hang.setText("行列式为0无逆矩阵");
            rank.setText("矩阵的秩为"+ans.calRank());
            return;
        }
        rank.setText("秩为"+ans.getCol());
        hang.setText("行列式为"+hangLieNum.toString());
        Matrix ni=ans.niMatrix();
        for(int i=0;i<jieNum*jieNum;i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            ed[aRow][aCol].setText(ni.getElement(aRow, aCol).toString());
        }

    }
    private void calHang() {
        if(toDoubleBack!=null){
            toDoubleBack();
        }
        int jieNum=Integer.parseInt(jie.getText().toString());
        Fraction a[]=new Fraction[100];
        for(int i=0;i<jieNum*jieNum;i++){
            int aRow=i/jieNum+1,aCol=i%jieNum+1;
            a[i]=Fraction.StringToFraction(ed[aRow][aCol].getText().toString());
        }
        Matrix ans=new Matrix(a,jieNum,jieNum);
        hangLieNum=ans.hanglie();
        hang.setText("行列式为"+hangLieNum.toString());
    }

}
