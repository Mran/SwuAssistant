package com.swuos.swuassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.swuos.net.OkhttpNet;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/5/17.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView zmy;
    private TextView yk;
    private TextView csd;
    private TextView tp;
    private TextView gky;
    private TextView version;

    private TextView swuos;
    private TextView feedback;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.SHOW:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case Constant.EMPTY:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        /*设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("关于我们");
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable d = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationIcon(d);
        initview();
    }

    private void initview() {
        zmy = (TextView) findViewById(R.id.developer_zmy);
        zmy.setMovementMethod(LinkMovementMethod.getInstance());
        yk = (TextView) findViewById(R.id.developer_yk);
        yk.setMovementMethod(LinkMovementMethod.getInstance());
        csd = (TextView) findViewById(R.id.developer_csd);
        csd.setMovementMethod(LinkMovementMethod.getInstance());
        tp = (TextView) findViewById(R.id.developer_tp);
        gky = (TextView) findViewById(R.id.developer_gky);

        version = (TextView) findViewById(R.id.version);
        try {
            version.setText("版本 :" + getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        swuos = (TextView) findViewById(R.id.swuos);
        swuos.setMovementMethod(LinkMovementMethod.getInstance());

        feedback = (TextView) findViewById(R.id.about_feedback);
        feedback.setOnClickListener(this);
        gky.setOnClickListener(this);
        tp.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SALog.d("settingActivity", "onOptionsItemSelected");
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.about_actity_out, 0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.about_feedback:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                //                dialog.setView(R.layout.feedback_layout);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View view = layoutInflater.inflate(R.layout.feedback_layout, null);
                dialog.setView(view);
                final TextView conactTextView = (TextView) view.findViewById(R.id.feedback_contact);
                final TextView contentTextView = (TextView) view.findViewById(R.id.feedback_content);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String contact = conactTextView.getText().toString();
                        final String issue = contentTextView.getText().toString();
                        if (issue != null && !issue.equals("")) {
                            final JsonObject postjson = new JsonObject();
                            postjson.addProperty("swuID", "");
                            postjson.addProperty("contact", contact);
                            postjson.addProperty("issue", issue);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OkhttpNet okhttpNet = new OkhttpNet();
                                    String response = okhttpNet.doPost(Constant.urlReportIssue, postjson);
                                    Message message = new Message();
                                    message.what = Constant.SHOW;
                                    message.obj = response;
                                    handler.sendMessage(message);
                                }
                            }).start();

                        } else if (issue.equals("")) {
                            Message message = new Message();
                            message.what = Constant.EMPTY;
                            message.obj = "反馈内容为空";
                            handler.sendMessage(message);
                        }

                    }
                });

                dialog.show();
                break;
            case R.id.developer_tp:
                Intent intent1 = new Intent(Intent.ACTION_SENDTO);
                intent1.setType("message/rfc822");
                intent1.putExtra(Intent.EXTRA_EMAIL, "99240947@qq.com");
                intent1.setData(Uri.parse("mailto:" + "99240947@qq.com"));

                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_FROM_BACKGROUND);
                try {

                    startActivity(Intent.createChooser(intent1, "Send mail..."));
                } catch (android.content.ActivityNotFoundException e) {
                    e.printStackTrace();
                    SALog.d("Email error:", e.toString());
                }
                break;
            case R.id.developer_gky:
                Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                intent2.setType("message/rfc822");
                intent2.putExtra(Intent.EXTRA_EMAIL, "610524299@qq.com");
                intent2.setData(Uri.parse("mailto:" + "610524299@qq.com"));

                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_FROM_BACKGROUND);
                try {

                    startActivity(Intent.createChooser(intent2, "Send mail..."));

                } catch (android.content.ActivityNotFoundException e) {
                    e.printStackTrace();
                    SALog.d("Email error:", e.toString());
                }
                break;
            default:
                break;
        }
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

}
