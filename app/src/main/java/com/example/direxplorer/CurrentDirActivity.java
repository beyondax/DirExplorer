package com.example.direxplorer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class CurrentDirActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "CurrentDirActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private ArrayList<DirPath> mDirPathList = new ArrayList<>();


    private RecyclerView mRecyclerView;
    protected RecyclerView.Adapter<DirRecyclerViewAdapter.MyViewHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            getDirPath();
        }

        setContentView(R.layout.activity_main);

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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Toast.makeText(getApplicationContext(), R.string.granted_permission, Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    getDirPath();
                } else {
                    // permission denied, boo!
                    Toast.makeText(getApplicationContext(), R.string.no_permission, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onCellClicked(int position) {
        Log.d(TAG, "onCellClicked() called with: position = [" + position + "]");
        mDirPathList.get(position).getDirPath();
    }

    public void getDirPath() {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
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
