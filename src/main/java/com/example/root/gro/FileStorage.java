package com.example.root.gro;


import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class FileStorage {

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void setDirectoryStructure(String folderName) {
        File f = new File(Environment.getExternalStorageDirectory() + "/" + folderName);
        File dir1 = new File(Environment.getExternalStorageDirectory() + "/" + folderName + "/gyro-data");
        File dir2 = new File(Environment.getExternalStorageDirectory() + "/" + folderName + "/acc-data");

        // check if DataSensors/ exists
        if (!f.exists()) {
            f.mkdirs();
        }

        // if yes create further directories
        if (f.exists()) {
            if (!dir1.exists() && !dir2.exists()) {
                dir1.mkdir();
                dir2.mkdir();

                String msg = folderName + " Created";
                Log.i(msg, msg);

            }
        }
    }

    public void writeToFile(Context ctx, String path, String filename, float[] data) {
        DataOutputStream dataOutputStream;


        File file = new File(Environment.getExternalStorageDirectory() + path, filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                dataOutputStream = new DataOutputStream(ctx.openFileOutput(filename, Context.MODE_PRIVATE));
                dataOutputStream.writeFloat(data[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Toast.makeText(ctx.getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();

    }


}
