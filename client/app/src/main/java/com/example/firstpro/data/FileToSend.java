package com.example.firstpro.data;

import java.io.File;

public class FileToSend implements java.io.Serializable{

    private static final long serialVersionUID = 3L;
    private File file;
    public FileToSend(File file){
       this.file = file;
    }

    public FileToSend(){

    }

    public String GetFileName(){
        if(file!=null){
            return file.getName();
        }
        return "课件.pptx";
    }

}
