package com.mysite.banking.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.banking.util.ScannerWrapper;

public abstract class BaseConsole {
    final ScannerWrapper scannerWrapper;
    final ObjectMapper objectMapper;

    public BaseConsole(){
        scannerWrapper  = ScannerWrapper.getInstance();
        objectMapper = new ObjectMapper();
    }
}
