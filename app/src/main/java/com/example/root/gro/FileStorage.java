package com.example.root.gro;

import android.content.Context;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import android.os.Environment;
import android.view.View;


public class FileStorage {

    FileWriter writer;
    File root = Environment.getExternalStorageDirectory();
    File gpxfile1 = new File(root,"gyro.csv");
    File gpxfile2 = new File(root, "accelero.csv");

    boolean existenceoff1 = gpxfile1.exists();
    boolean existenceoff2 = gpxfile2.exists();


    private void writeFileHeader(String h1,String h2, String h3, String h4)throws IOException{

        String line = String.format("%s,%s,%s,%s\n",h1,h2,h3,h4);
        writer.append(line);
    }

    private void writeCSVdata(float a, float b, float c, String date)throws IOException{
        String line = String.format("%f,%f,%f,%s\n",a,b,c,date);
        writer.append(line);
    }

    public void csvfilesinitialize()
    {
        if(!existenceoff1)
        {
            try{
                writer = new FileWriter(gpxfile1);
                writeFileHeader("Gx", "Gy", "Gz","Timestamp");

                writer.flush();
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if(!existenceoff2)
        {
            try{
                writer = new FileWriter(gpxfile2);
                writeFileHeader("Ax", "Ay", "Az","Timestamp");

                writer.flush();
                writer.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void gyrowriter(Float v1, Float v2, Float v3, String date) throws IOException {
        try {

            writer = new FileWriter(gpxfile1,true);
            writeCSVdata(v1,v2,v3,date);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accelerowriter(Float v1, Float v2, Float v3, String date) throws IOException {
        try {

            writer = new FileWriter(gpxfile2,true);
            writeCSVdata(v1,v2,v3,date);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
