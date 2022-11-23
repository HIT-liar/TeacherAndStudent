package com.example.firstpro.data;

public class ChoseQues  implements java.io.Serializable{

    private static final long serialVersionUID = 2L;
    private String question;
    private String A;
    private String B;
    private String C;
    private String D;
    private String ans;
    private boolean kind = false;

    public  ChoseQues(boolean kind){
        this.kind = kind;
    }

    public void setQuestion(String question){
        this.question = question;
    }
    public String getQuestion(){
        return question;
    }
    public void setA(String A){
        this.A = A;
    }
    public String getA(){
        return A;
    }
    public void setB(String B){
        this.B = B;
    }
    public String getB(){
        return B;
    }
    public void setC(String C){
        this.C = C;
    }
    public String getC(){
        return C;
    }
    public void setD(String D){
        this.D = D;
    }
    public String getD(){
        return D;
    }
    public void setAns(String ans){
        this.ans = ans;
    }
    public String getAns(){
        return ans;
    }
}
