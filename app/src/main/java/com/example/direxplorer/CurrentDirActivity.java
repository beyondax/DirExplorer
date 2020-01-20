package com.example.direxplorer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class CurrentDirActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "CurrentDirActivity";
    private static final int STORAGE_PERMISSION_CODE = 23;
    private ArrayList<DirPath> mDirPathList = new ArrayList<>();


    private RecyclerView mRecyclerView;
    protected RecyclerView.Adapter<DirRecyclerViewAdapter.MyViewHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDirPath();

        mRecyclerView = findViewById(R.id.root_dir_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DirRecyclerViewAdapter(mDirPathList, this);
        mRecyclerView.setAdapter(mAdapter);
        

        Log.d(TAG, "onCreate: " + mDirPathList.size());
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCellClicked(int position) {
        Log.d(TAG, "onCellClicked() called with: position = [" + position + "]");
        Intent intent = new Intent(this, SelectedDirActivity.class);
        intent.putExtra("name", mDirPathList.get(position));
//        startActivity(intent);
    }

    public void getDirPath() {

        File file = new File(Environment.getExternalStorageDirectory().toString());
        File[] files = file.listFiles();
        Log.d(TAG, "getDirPath: " + files.length);
        int i = 0;
        for (File f : files) {
            if (f.isFile()) {
                try {
                    mDirPathList.add(i, new DirPath(f.getPath(), f.getName(), false));
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    mDirPathList.add(i, new DirPath(f.getPath(), f.getName(), true));
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
