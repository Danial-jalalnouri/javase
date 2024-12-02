package com.mysite.phonebook.oop;

public class BigDog extends Dog implements Talkable {
    @Override
    public String talk(){
        return "Big " + super.talk();
        //  Big + vagh vagh
    }

    public String doubleTalk(){
        return "vagh vagh vagh vagh";
    }
}
