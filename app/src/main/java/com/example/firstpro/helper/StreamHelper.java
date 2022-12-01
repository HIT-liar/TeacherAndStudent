package com.example.firstpro.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamHelper {

    public static String InputToString(InputStream is) throws IOException{
        StringBuilder builder = new StringBuilder();

        String line;
        InputStreamReader inputStreamReader =new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while((line = bufferedReader.readLine())!=null){
            builder.append(line);
        }

        inputStreamReader.close();
        bufferedReader.close();

        return builder.toString();
    }
}
