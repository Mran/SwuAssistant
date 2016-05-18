package com.swuos.ALLFragment.main_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.swuos.ALLFragment.FragmentControl;
import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class MainPageFragment extends Fragment implements View.OnClickListener {
    private RecyclerView schdeduleRecyclerView;
    private View mainPageLayout;
    private Button wifi_button;
    private Button card_button;
    private FragmentControl fragmentControl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainPageLayout = inflater.inflate(R.layout.main_page_layout, container, false);
        initView();
        return mainPageLayout;
    }

    private void initView() {
/*        schdeduleRecyclerView = (RecyclerView) mainPageLayout.findViewById(R.id.schedyle_recycleview);
        schdeduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        schdeduleRecyclerView.setHasFixedSize(true);
        schdeduleRecyclerView.setAdapter(new ScheduleRecycleViewAdapter(getContext()));*/
        //        wifi_button = (Button) mainPageLayout.findViewById(R.id.main_page_wifi);
        //        wifi_button.setOnClickListener(this);
        //        card_button = (Button) mainPageLayout.findViewById(R.id.main_page_card);
        //        card_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //            case R.id.main_page_wifi:
            //                Intent intent = new Intent();
            //                intent.setClass(getContext(), WifiActivity.class);
            //                startActivity(intent);
            //                break;
            //            case R.id.main_page_card:
            //                break;
            default:
                break;
        }
    }
}
