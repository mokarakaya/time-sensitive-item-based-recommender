package com.main;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Gson gson= new Gson();
        String json = gson.toJson(1);
        
    }
}
