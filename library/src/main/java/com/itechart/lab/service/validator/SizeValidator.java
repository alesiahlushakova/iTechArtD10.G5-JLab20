package com.itechart.lab.service.validator;

import java.io.File;


public class SizeValidator implements Validator {
    @Override
    public boolean validate(File fileItem) {
        return fileItem != null && fileItem.length() < 2000000;
    }
}
