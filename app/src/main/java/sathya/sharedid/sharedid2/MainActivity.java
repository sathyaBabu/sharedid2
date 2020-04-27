package sathya.sharedid.sharedid2;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


// accessing resources from the other process boundry Followed by a github link
// https://github.com/ikust/hello-sharedprocess

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button buttonSave;
    TextView textView;

    String packageName = "sathya.sharedid.sharedid1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView2);
    }

    public void LoadFile(View view) {

        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
           // textView.setText(applicationInfo.dataDir);
            Log.d("tag","from packageManager GET_META_DATA  : "+applicationInfo);
            String filePath = applicationInfo.dataDir+"/files/Sathya.txt";
           // Guess WHAT ??? Following method cant read the file across the sand box!!!
           // Lets get to the manifest file and set a common USERID between files( Process )
            readFile(filePath);

            // phase 2


          //  Context friendContext = this.createPackageContext( "sathya.sharedid.sharedid1", Context.CONTEXT_IGNORE_SECURITY);

         //   And access some resources of that application:

//            friendContext.getResources().getString(R.id.editText);
//            friendContext.getResources().getDrawable(R.mipmap.ic_launcher_round);
//            friendContext.registerReceiver();


        } catch (PackageManager.NameNotFoundException e) {

            textView.setTextColor(Color.RED);
            textView.setText("OOPS!!! " + e);

        }
    }

    public  void readFile(String filepath) {

        FileInputStream fileInputStream ;

        try {
            fileInputStream = new FileInputStream(new File(filepath));
            int noOfBytesRead  = -1 ;
            StringBuffer stringBuffer  = new StringBuffer();
            while(( noOfBytesRead = fileInputStream.read())!=-1) {
                stringBuffer.append((char)noOfBytesRead);
            }
            textView.setTextColor(Color.BLUE);
           textView.setText("\n Read From \n"+filepath+"\n"+stringBuffer);


        } catch (FileNotFoundException e) {
            textView.setTextColor(Color.RED);
            textView.setText("Oops! "+e);
        } catch (IOException e) {

            textView.setTextColor(Color.RED);
            textView.setText("Oops! "+e);
        }


    }
}

/*
 The name of a Linux user ID that will be shared with other applications. By default,
 Android assigns each application its own unique user ID. However,
 if this attribute is set to the same value for two or more applications,
 they will all share the same ID — provided that they are also signed by the same certificate.
 Application with the same user ID can access each other's data and, if desired, run in the same process.

Notice that they need to be signed by the same certificate.

Two applications share the same user id may access each other's resource.

For example:

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.shareusertesta"
    android:versionCode="1"
    android:versionName="1.0"
    android:sharedUserId="sathya.sharedid.sharedid">

Then we can init a new context of com.example by:

Context friendContext = this.createPackageContext( "com.example",Context.CONTEXT_IGNORE_SECURITY);

And access some resources of that application:

friendContext.getResources().getString(id);
friendContext.getResources().getDrawable(id);
friendContext.registerReceiver(...);

 */

























