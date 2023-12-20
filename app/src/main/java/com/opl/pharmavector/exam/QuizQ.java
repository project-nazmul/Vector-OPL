package com.opl.pharmavector.exam;

public class QuizQ {
    String _QUIZ_QUES;
    String _OPTIONA;
    String _OPTIONB;
    String _OPTIONC;
    String _OPTIOND;
    String _ANSWER;

    public QuizQ(String QUIZ_QUESTION, String OPTIONA, String OPTIONB,String OPTIONC, String OPTIOND,String ANSWER){
        this._QUIZ_QUES = QUIZ_QUESTION;
        this._OPTIONA = OPTIONA;
        this._OPTIONB = OPTIONB;
        this._OPTIONC = OPTIONC;
        this._OPTIOND = OPTIOND;
        this._ANSWER = ANSWER;
    }

    public String getques() { return this._QUIZ_QUES;}
    public String getoptiona(){
        return this._OPTIONA;
    }
    public String getoptionb() {return this._OPTIONB;}
    public String getoptionc() {return this._OPTIONC;}
    public String getoptiond() {return this._OPTIOND;}
    public String getanswer() {return this._ANSWER;}
}



