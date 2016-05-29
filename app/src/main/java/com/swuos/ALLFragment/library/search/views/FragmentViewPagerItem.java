package com.swuos.ALLFragment.library.search.views;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.swuos.ALLFragment.library.search.model.LibHoldInfo;
import com.swuos.ALLFragment.library.search.utils.LibTools;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import io.github.zhitaocai.toastcompat.ToastCompat;


/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public class FragmentViewPagerItem extends Fragment {
    public static final String KEY_FRAGMENT_LIBINFO = "LibHoldInfo";
    private View itemView;
    private TextView textViewCollectPlace;
    private TextView textViewDeptName;
    private TextView textViewisbn;
    private TextView textViewAccessNum;
    private TextView textViewBarCode;
    private TextView textViewBookStatus;
    private AppCompatButton buttonShowCollectPlace;
    private LibHoldInfo libHoldInfo;
    private LibTools libTools;
    private String address;
    private MaterialDialog dialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initDialog("结果: "+address);
                    dialog.show();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.library_bookdetail_item, container, false);
        textViewCollectPlace = (TextView) itemView.findViewById(R.id.textViewLibHoldingCollectPlace);
        textViewDeptName = (TextView) itemView.findViewById(R.id.textViewLibHoldingDeptName);
        textViewisbn = (TextView) itemView.findViewById(R.id.textViewLibHoldingisbn);
        textViewAccessNum = (TextView) itemView.findViewById(R.id.textViewLibAccessNum);
        textViewBarCode = (TextView) itemView.findViewById(R.id.textViewLibBarCode);
        textViewBookStatus = (TextView) itemView.findViewById(R.id.textViewLibHoldingBookStatus);
        buttonShowCollectPlace = (AppCompatButton) itemView.findViewById(R.id.btnLibInfoShowCollectPlace);
        return itemView;
    }

    public static FragmentViewPagerItem newInstance(LibHoldInfo libHoldInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_FRAGMENT_LIBINFO, libHoldInfo);
        FragmentViewPagerItem fragmentViewPagerItem = new FragmentViewPagerItem();
        fragmentViewPagerItem.setArguments(bundle);
        return fragmentViewPagerItem;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        libTools = new LibTools();
        libHoldInfo = (LibHoldInfo) getArguments().getSerializable(KEY_FRAGMENT_LIBINFO);
        textViewCollectPlace.setText("馆藏地址:" + libHoldInfo.getindustryTitle());
        textViewDeptName.setText("所属校区:" + libHoldInfo.getDeptName());
        textViewisbn.setText("索书号:" + libHoldInfo.getisbn());
        textViewAccessNum.setText("登录号:" + libHoldInfo.getaccessionNumber());
        textViewBarCode.setText("条形码:" + libHoldInfo.getbarCode());
        textViewBookStatus.setText("书刊状态:" + libHoldInfo.getBookstatus());
        buttonShowCollectPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "馆藏查询中...", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SALog.d("kklog","FragmentViewPagerItem barCode==>"+libHoldInfo.getbarCode());
                        address = libTools.getCollectAddress(libHoldInfo.getbarCode());
                        mHandler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });
    }

    private void initDialog(String s) {
        dialog = new MaterialDialog.Builder(getActivity())
                .title("结果")
                .content(s)
                .positiveText("确定")
                .cancelable(true)
                .negativeText("在浏览器打开")
                .positiveColor(Color.parseColor("#48b360"))
                .build();
        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(Constant.libraryCollectingAddress+libHoldInfo.getbarCode());
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }
}
