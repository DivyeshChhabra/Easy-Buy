package com.mycompany.easybuy.helper; /* Package Name */

public class Helper {
    public static String getSmallContent(String description){
        String[] tokens = description.split(" ");
        if(tokens.length>15){
            String modifiedDescription = "";
            for(int i=0;i<15;i++){
                modifiedDescription += tokens[i]+" ";
            }
            return modifiedDescription+"..."; 
        }else{
            return description+"...";
        }
    }
}
