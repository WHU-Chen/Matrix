package com.example.administrator.matrix;

/**
 * Created by Administrator on 2015/5/25.
 */
public class Fraction {
    private int zi,mu;
    public void setZi(int zi) {
        this.zi = zi;
    }
    public static final Fraction StringToFraction(String a){
        char b[]=a.toCharArray();
        if(b.length==0)return new Fraction(0);
        for(int j=0;j<b.length;j++){
            if((b[j]>'9'||b[j]<'0')&&b[j]!='.'&&b[j]!='/'&&b[j]!='-')return new Fraction(0);
        }
        int i;
        for(i=0;i<b.length;i++){
            if(b[i]=='.')break;
        }
        if(i<b.length){
            for(int j=i+1;j<b.length;j++){
                if((b[j]>'9'||b[j]<'0'))return new Fraction(0);
            }
            int zi=Integer.parseInt(a.substring(0,i)+a.substring(i+1,b.length));
            int mu= (int) Math.pow(10,b.length-i-1);
            return new Fraction(zi,mu);
        }
        for(i=0;i<b.length;i++){
            if(b[i]=='/')break;
        }
        for(int j=i+1;j<b.length;j++){
            if((b[j]>'9'||b[j]<'0'))return new Fraction(0);
        }
        int zi=Integer.parseInt(a.substring(0,i));
        if(i+1>=b.length)return new Fraction(zi);
        int mu=Integer.parseInt(a.substring(i+1,b.length));
        return new Fraction(zi,mu);
    }
    public void setMu(int mu) {
        this.mu = mu;
    }
    public int getZi() {
        return zi;
    }
    public int getMu() {
        return mu;
    }
    private int gcd(int a,int b){
        int temp;
        if(b<0){
            b=-b;
        }
        if(a<0)a=-a;
        if(a<b){temp=a;a=b;b=temp;}

        while(b!=0){
            temp=a%b;
            a=b;
            b=temp;
        }
        return a;
    }
    public boolean is_zero(){
        return zi==0?true:false;
    }
    public Fraction(int zi,int mu){
        int i=gcd(zi,mu);
        this.zi=zi/i;
        this.mu=mu/i;
    }
    public Fraction(int zi){
        this.zi=zi;
        this.mu=1;
    }


    public Fraction plus(Fraction b){
        Fraction a;
        int ansZ=zi*b.getMu()+b.getZi()*mu,ansM=b.getMu()*mu;
        int gcd1=gcd(ansZ,ansM);
        a=new Fraction(ansZ/gcd1,ansM/gcd1);
        return a;
    }
    public Fraction plus(int b){
        Fraction a;
        int ansZ=zi+b*mu,ansM=mu;
        a=new Fraction(ansZ,ansM);
        return a;
    }

    public Fraction multi(Fraction b){
        Fraction a;
        int ansZ=zi*b.getZi(),ansM=mu*b.getMu();
        int gcd1=gcd(ansZ,ansM);
        if(ansM<0){
            ansZ=-ansZ;
            ansM=-ansM;
        }
        else if(ansM==0){return null;}

        a=new Fraction(ansZ/gcd1,ansM/gcd1);
        return a;
    }
    public Fraction multi(int b){
        Fraction a;
        int ansZ=zi-b*mu,ansM=mu;
        if(ansM<0){
            ansZ=-ansZ;
            ansM=-ansM;
        }
        a=new Fraction(ansZ,ansM);
        if(a.getMu()==0)return null;
        return a;
    }

    public Fraction divide(Fraction b){
        return this.multi(new Fraction(b.getMu(),b.getZi()));
    }
    public Fraction divide(int b){
        if(b==0)return null;
        return this.multi(new Fraction(1,b));
    }

    public Fraction minus(Fraction b){
        return this.plus(new Fraction((-b.getZi()),b.getMu()));
    }
    public Fraction minus(int b){
        return this.plus(new Fraction(-b));
    }

    public Fraction daoShu(){
        return new Fraction(mu,zi);
    }
    public Fraction bianHao(){
        return new Fraction(-zi,mu);
    }

    public double toDouble(){return (double)zi/(double)mu;}
    @Override
    public String toString(){
        if(mu==1){
            return String.valueOf(zi);
        }
        String a=new String(String.valueOf(zi)+'/'+String.valueOf(mu));
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null)return false;
        if(this==o)return true;
        if(getClass()!=o.getClass())return false;
        Fraction other=(Fraction) o;
        int gcd1=gcd(zi,mu),oZi=other.getZi(),oMu=other.getMu();
        zi=zi/gcd1;mu=mu/gcd1;
        gcd1=gcd(oZi,oMu);
        if(zi==(oZi/gcd1)&&mu==(oMu/gcd1))return true;
        else return false;
    }


    static public void main(String[] args){
        Fraction a=new Fraction(10,2);
        System.out.println(a.multi(new Fraction(9,-27)));
    }

}
