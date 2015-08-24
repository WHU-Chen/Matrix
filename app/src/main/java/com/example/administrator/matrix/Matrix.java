package com.example.administrator.matrix;

/**
 * Created by Administrator on 2015/5/25.
 */
public class Matrix {
    private Fraction ary[];
    public static final int NODEFINE=-2147483648;
    private int row,col;
    public void arow_plus_brow_multi_n(int a,int b,Fraction n){
        if(a>row||b>col||a<0||b<0)return;
        int i=(a-1)*col,j=(b-1)*col;
        for(;i<=(a-1)*col+col-1;i++,j++){
            ary[i]=ary[i].plus(ary[j].multi(n));
        }
    }
    public void arow_divide_b(int a,Fraction b){
        if(a>row||a<0)return;
        int i=(a-1)*col;
        for(;i<=(a-1)*col+col-1;i++){
            ary[i]=ary[i].divide(b);
        }
    }
    public void swapABrow(int a,int b){
        Fraction p;
        int i=(a-1)*col,j=(b-1)*col;
        for(;i<=(a-1)*col+col-1;i++,j++){
            p=ary[i];
            ary[i]=ary[j];
            ary[j]=p;
        }
    }
    public Matrix get_del_ab_matrix(int aRow,int bCol){
        Matrix k;
        Fraction fra[]=new Fraction[100];
        for(int i=0, ii=0;i<row;i++,ii++){
            if(i==aRow-1){--ii;continue;}
            for(int j=0,jj=0;j<col;j++,jj++){
               if(j==bCol-1){jj--;continue;}
                fra[ii*(col-1)+jj]=ary[i*col+j];
            }
        }
        k=new Matrix(fra,row-1,col-1);
        return k;
    }
    public Fraction getElement(int a,int b){
        if(a>row||b>col||a<0||b<0)return null;
        return ary[col*(a-1)+b-1];
    }
    public Matrix(Fraction a[],int row ,int col){
        this.row=row;
        this.col=col;
        ary=new Fraction[100];
        for(int i=0;i<row*col;i++){
            ary[i]=a[i];
        }
    }
    public Fraction[] getAry() {
        return ary;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public Matrix(Matrix a){
        ary=new Fraction[100];
        this.row=a.getRow();
        this.col=a.getCol();
        Fraction[] tem=a.getAry();
        for(int i=0;i<row*col;i++){
            ary[i]=tem[i];
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o==null)return false;
        if(this==o)return true;
        if(getClass()!=o.getClass())return false;
        Matrix other=(Matrix)o;
        if(other.getRow()!=row||other.getCol()!=col)return false;
        for(int i=0;i<col*row-1;i++){
            if(other.getElement(i/col+1,i%col+1)!=ary[i])return false;
        }
        return true;
    }

    public Matrix niMatrix(){
        Fraction hl=hanglie();
        if(hl.is_zero())return null;
        Matrix ans=null;
        if(row<0||col<0||row!=col)return null;
        Fraction ansFra[]=new Fraction[100];
        for(int i=0;i<col*row;i++){
            int ansRow=i%col+1,ansCol=i/col+1;//直接计算A的伴随矩阵，遂先转置
            int aRow=i/col+1,aCol=i%col+1;//直接计算A的伴随矩阵，遂先转置
            int toRow=(ansRow-1),toCol=ansCol-1;
            int extra=((ansRow+ansCol)%2==0)?1:(-1);
            ansFra[toRow*col+toCol]=get_del_ab_matrix(aRow,aCol).hanglie().divide(hl).multi(new Fraction(extra));
        }
        ans=new Matrix(ansFra,row,col);
        return ans;
    }
    public Fraction hanglie(){
        if(row!=col)return null;
        Matrix a=new Matrix(this);
        int extra1=1;
        for(int i=1;i<=a.getRow();i++){
            if(a.getElement(i,i).getZi()==0){
                boolean is_find=false;
                for(int j=i+1;j<=a.getRow();j++){
                    if(a.getElement(j,i).getZi()!=0){
                        is_find=true;
                        a.swapABrow(j,i);
                        extra1*=-1;
                    }
                }
                if(!is_find)return new Fraction(0);
            }
            for(int j=i+1;j<=a.getRow();j++) {
                Fraction n = a.getElement(j,i).divide(a.getElement(i,i));
                a.arow_plus_brow_multi_n(j,i,n.bianHao());
            }
        }
        Fraction ans=new Fraction(1,1);
        for(int i=1;i<=a.getRow();i++){
            ans=ans.multi(a.getElement(i,i));
        }
        return ans.multi(new Fraction(extra1));
    }
    public int calRank(){
        Matrix a=new Matrix(this);
        int rank=a.getRow();
        for(int i=1;i<=a.getRow();i++){
            if(a.getElement(i,i).getZi()==0){
                boolean is_find=false;
                for(int j=i+1;j<=a.getRow();j++){
                    if(a.getElement(j,i).getZi()!=0){
                        is_find=true;
                        a.swapABrow(j,i);
                    }
                }
                if(!is_find){--rank;continue;}
            }
            for(int j=i+1;j<=a.getRow();j++) {
                Fraction n = a.getElement(j,i).divide(a.getElement(i,i));
                a.arow_plus_brow_multi_n(j,i,n.bianHao());
            }
        }
        return rank;
    }
    public Matrix simB(){
        Matrix a=new Matrix(this);
        for(int i=1;i<=a.getRow();i++){
            if(a.getElement(i,i).getZi()==0){
                boolean is_find=false;
                for(int j=i+1;j<=a.getRow();j++){
                    if(a.getElement(j,i).getZi()!=0){
                        is_find=true;
                        a.swapABrow(j,i);
                    }
                }
                if(!is_find){continue;}
            }
            for(int j=i+1;j<=a.getRow();j++) {
                Fraction n = a.getElement(j,i).divide(a.getElement(i,i));
                a.arow_plus_brow_multi_n(j,i,n.bianHao());
            }
            for(int j=i-1;j>=1;j--) {
                Fraction n = a.getElement(j,i).divide(a.getElement(i,i));
                a.arow_plus_brow_multi_n(j,i,n.bianHao());
            }
        }
        for(int i=1;i<=a.getRow();i++){
            int j=i;
            while(!a.getElement(j,j).is_zero()&&j>=2){
                if(a.getElement(j-1,j-1).is_zero()){
                    a.swapABrow(j,j-1);
                }
                --j;
            }
        }
        for(int i=1;i<=a.getRow();i++){
            int j=i;
            while(j<=a.getRow()&&a.getElement(i,j).is_zero())j++;
            if(j>a.getRow())continue;
            a.arow_divide_b(i,a.getElement(i,j));
        }
        return a;

    }
    public String toString(){
        StringBuilder k=new StringBuilder();
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                k.append(ary[i * col + j]);
                k.append(",");
            }
            k.append("\n");
        }
        return k.toString();
    }
    static public void main(String[] arc){
        Fraction[] a=new Fraction[]{
                new Fraction(2),new Fraction(3),new Fraction(-1),
                new Fraction(1),new Fraction(2),new Fraction(0),
                new Fraction(-1),new Fraction(2),new Fraction(-2),
        };

        Matrix b=new Matrix(a,3,3);
        System.out.println(b.hanglie()+"\n"+b.niMatrix());
    }
}
