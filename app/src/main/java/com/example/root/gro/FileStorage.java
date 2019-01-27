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
    File gpxfile = new File(root,"data.csv");
   // File gpxfile1 = new File(root,"gyro.csv");
   // File gpxfile2 = new File(root, "accelero.csv");

    boolean existenceoffile = gpxfile.exists();
    //boolean existenceoff1 = gpxfile1.exists();
    //boolean existenceoff2 = gpxfile2.exists();


    private void writeFileHeader(String h1,String h2, String h3, String h4, String h5, String h6, String h7)throws IOException{

        String line = String.format("%s,%s,%s,%s,%s,%s,%s\n",h1,h2,h3,h4,h5,h6,h7);
        writer.append(line);
    }

    private void writeCSVdata(String date, float a, float b, float c, float d, float e, float f)throws IOException{
        String line = String.format("%s,%f,%f,%f,%f,%f,%f\n",date,a,b,c,d,e,f);
        writer.append(line);
    }

    public void csvfilesinitialize()
    {
        if(!existenceoffile)
        {
            try{
                writer = new FileWriter(gpxfile);
                writeFileHeader("Timestamp","Gx","Gy","Gz","Ax","Ay","Az");

                writer.flush();
                writer.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        /*if(!existenceoff1)
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
        }*/
    }


    /*public void gyrowriter(Float v1, Float v2, Float v3, String date) throws IOException {
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
    }*/

    public void datawriter(String date, Float v1, Float v2, Float v3, Float v4, Float v5, Float v6) throws IOException {

        try{
            writer = new FileWriter(gpxfile,true);
            writeCSVdata(date,v1,v2,v3,v4,v5,v6);

            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
