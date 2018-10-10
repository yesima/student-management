package com.school;

public class Util {

    /**
     * Checks whether the given parameters are empty or not
     * @param parameters String parameters
     * @return false if any of the parameters is empty, true otherwise
     */
    public static boolean parameterCheck(String ... parameters){
        boolean isError = false;
        for(String parameter : parameters) {
            if(parameter == null || parameter.isEmpty()){
                isError = true;
            }
        }
        return isError;
    }

}
