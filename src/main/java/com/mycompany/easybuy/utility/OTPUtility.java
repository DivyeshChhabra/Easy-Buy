package com.mycompany.easybuy.utility;

import java.util.*;

public class OTPUtility {
    
    int maximum = 999999;
    int minimum = 100000;
   
    Random random = new Random();
    int otp;

    public int getOtp() {
        return this.otp;
    }

    public void setOtp() {
        this.otp = random.nextInt(maximum-minimum) + minimum;
    }
}
