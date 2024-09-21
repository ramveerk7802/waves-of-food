package com.example.food.otherClass

class MobileNumberValid {
     fun numberCheck(number:String):Boolean{
        if(number.length<10 || number.length>10)
            return false
        else{
            for(i in number.indices){
                if(i==0 && number[i]<'6')
                        return false
                else if(number[i]<'0' || number[i]>'9')
                        return false
            }
        }
        return true
    }
}