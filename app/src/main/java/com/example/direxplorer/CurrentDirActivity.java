package com.example.direxplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class CurrentDirActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "CurrentDirActivity";

    private ArrayList<DirPath> mDirPathList = new ArrayList<>();



    private RecyclerView mRecyclerView;
    protected RecyclerView.Adapter<DirRecyclerViewAdapter.MyViewHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DirPath mDirPath = new DirPath(Environment.getExternalStorageDirectory().getAbsolutePath());
//        mDirPathList.add(mDirPath);

        mRecyclerView = findViewById(R.id.root_dir_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.HORIZONTAL));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DirRecyclerViewAdapter<>(mDirPathList, this);
        mRecyclerView.setAdapter(mAdapter);

        getDirPath();

    }

    @Override
    public void onCellClicked(int position) {
        Log.d(TAG, "onCellClicked() called with: position = [" + position + "]");
        Intent intent = new Intent(this, SelectedDirActivity.class);
//        intent.putExtra("name", mDirPathNameList.get(position));
        startActivity(intent);
    }

    public void getDirPath() {

        File file = new File(Environment.getExternalStorageDirectory().toString());
        File[] files = file.listFiles();
        int i = 1;
        for(File f : files)
        {
            if(f.isFile() || f.isDirectory())
            {
                try
                {
                   mDirPathList.add(new DirPath(f.getAbsolutePath()));
                    i++;
                }
                catch(Exception e){}
            }
        }
//        File[] directories = directory.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                return pathname.isDirectory();
//            }
//        });


    }
}
