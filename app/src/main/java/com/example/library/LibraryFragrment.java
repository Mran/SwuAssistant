package com.example.library;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class LibraryFragrment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View libraryLayout = inflater.inflate(R.layout.library_layout, container, false);


        /*Do something in here*/

        return libraryLayout;
    }
}
