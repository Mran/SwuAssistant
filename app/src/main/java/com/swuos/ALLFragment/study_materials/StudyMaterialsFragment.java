package com.swuos.ALLFragment.study_materials;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class StudyMaterialsFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View studyMaterialsLayout = inflater.inflate(R.layout.study_materials_layout, container, false);


        /*Do something in here*/

        return studyMaterialsLayout;
    }
}
