package com.swuos.ALLFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.swuos.ALLFragment.card.EcardFragmentImp;
import com.swuos.ALLFragment.charge.ChargeFragment;
import com.swuos.ALLFragment.find_lost.FindLostFragment;
import com.swuos.ALLFragment.library.lib.views.LibFragment;
import com.swuos.ALLFragment.main_page.MainPageFragment;
import com.swuos.ALLFragment.setting.SettingFragment;
import com.swuos.ALLFragment.study_materials.StudyMaterialsFragment;
import com.swuos.ALLFragment.swujw.grade.GradesFragment;
import com.swuos.ALLFragment.swujw.schedule.ScheduleFragment;
import com.swuos.ALLFragment.wifi.WifiFragment;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/4/6.
 */
public class FragmentControl {
    /*主界面布局*/
    private static MainPageFragment mainPageFragment;
    /*课程表界面布局*/
    private static ScheduleFragment scheduleFragment;
    //    private static ScheduleTableFragment scheduleTableFragment;

    private static EcardFragmentImp cardfragment;

    /*成绩界面布局*/
    private static GradesFragment gradesFragment;
    /*学习资料界面布局*/
    private static StudyMaterialsFragment studyMaterialsFragment;
    /*图书馆界面布局*/
    private static LibFragment libraryFragment;
    /*水电费界面布局*/
    private static ChargeFragment chargeFragment;
    /*失物找寻界面布局*/
    private static FindLostFragment findLostFragment;
    private static SettingFragment settingFragment;
    private static WifiFragment WifiFragment;
    private static FragmentManager fragmentManager;
    private static String name;
    private static String pd;

    public FragmentControl(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        name = MainActivity.sharedPreferences.getString("userName", "nothing");
        pd = MainActivity.sharedPreferences.getString("password", "nothing");
    }

    public void initFragment(FragmentManager fragmentManager) {
        FragmentTransaction transaction;
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();

        mainPageFragment = new MainPageFragment();
        transaction.add(R.id.content, mainPageFragment, Constant.FRAGMENTTAG[0]);


        scheduleFragment = new ScheduleFragment();
        transaction.add(R.id.content, scheduleFragment, Constant.FRAGMENTTAG[1]);

        gradesFragment = new GradesFragment();
        transaction.add(R.id.content, gradesFragment, Constant.FRAGMENTTAG[2]);

        cardfragment = new EcardFragmentImp();
        transaction.add(R.id.content, cardfragment, Constant.FRAGMENTTAG[3]);

        studyMaterialsFragment = new StudyMaterialsFragment();
        transaction.add(R.id.content, studyMaterialsFragment, Constant.FRAGMENTTAG[3]);

        findLostFragment = new FindLostFragment();
        transaction.add(R.id.content, findLostFragment, Constant.FRAGMENTTAG[4]);

        chargeFragment = new ChargeFragment();
        transaction.add(R.id.content, chargeFragment, Constant.FRAGMENTTAG[5]);


        libraryFragment = new LibFragment();
        transaction.add(R.id.content, libraryFragment, Constant.FRAGMENTTAG[6]);
        transaction.commit();
        hideFragments(transaction);
    }


    public void fragmentStateCheck(Bundle saveInstanceState, FragmentManager fragmentManager, int fragmentPosition) {
        if (saveInstanceState == null) {
            //            initFragment(fragmentManager);
            fragmentSelection(fragmentPosition);
            SALog.d("MainActity", "加载各个fragment");
        } else {
            SALog.d("MainActity", "saveInstanceState存在数据,重新加载fragment");
            if (mainPageFragment != null) {
                mainPageFragment = (MainPageFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[0]);
                SALog.d("MainActity", "saveInstanceState存在数据,findMainPage");
            }

            if (scheduleFragment != null) {
                scheduleFragment = (ScheduleFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[1]);
                SALog.d("MainActity", "saveInstanceState存在数据,findSchedulFragment");
            }
            if (gradesFragment != null) {
                gradesFragment = (GradesFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[2]);
            }
            if (cardfragment != null) {
                cardfragment = (EcardFragmentImp) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[8]);
            }

            if (studyMaterialsFragment != null) {
                studyMaterialsFragment = (StudyMaterialsFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[3]);
            }
            if (findLostFragment != null) {
                findLostFragment = (FindLostFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[4]);
            }
            if (chargeFragment != null) {
                chargeFragment = (ChargeFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[5]);
            }
            if (WifiFragment != null) {
                WifiFragment = (WifiFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[7]);
            }
            if (libraryFragment != null) {
                libraryFragment = (LibFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[6]);
                SALog.d("MainActity", "saveInstanceState存在数据,findLibraryFragment");
            }
            fragmentSelection(fragmentPosition);
        }
    }

    public static void fragmentSelection(int id) {
        FragmentTransaction transaction;
        transaction = fragmentManager.beginTransaction();

        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (id) {
            //            case R.id.nav_main:
            //                if (mainPageFragment == null) {
            //                    // 如果mainPageFragment为空，则创建一个并添加到界面上
            //                    mainPageFragment = new MainPageFragment();
            //                    transaction.add(R.id.content, mainPageFragment, Constant.FRAGMENTTAG[0]);
            //
            //                } else {
            //                    // 如果mainPageFragment不为空，则直接将它显示出来
            //                    transaction.show(mainPageFragment);
            //
            //                }
            //
            //                break;
            case R.id.nav_schedule:
                if (scheduleFragment == null) {
                    // 如果scheduleTableFragment为空，则创建一个并添加到界面上
                    scheduleFragment = new ScheduleFragment();
                    transaction.add(R.id.content, scheduleFragment, Constant.FRAGMENTTAG[1]);
                    ;
                } else {
                    // 如果scheduleFragment不为空，则直接将它显示出来

                    transaction.show(scheduleFragment);
                }

                break;
            case R.id.nav_grades:

                if (gradesFragment == null) {
                    // 如果GradesFragment为空，则创建一个并添加到界面上
                    gradesFragment = new GradesFragment();
                    transaction.add(R.id.content, gradesFragment, Constant.FRAGMENTTAG[2]);
                } else {
                    // 如果GradesFragment不为空，则直接将它显示出来
                    transaction.show(gradesFragment);
                }

                break;
            case R.id.nav_library:

                if (libraryFragment == null) {
                    // 如果libraryFragrment为空，则创建一个并添加到界面上
                    libraryFragment = new  LibFragment();
                    transaction.add(R.id.content, libraryFragment, Constant.FRAGMENTTAG[6]);
                } else {
                    // 如果libraryFragrment不为空，则直接将它显示出来
                    transaction.show(libraryFragment);
                }
                break;
            case R.id.nav_wifi:
                if (WifiFragment == null) {
                    WifiFragment = new WifiFragment();
                    transaction.add(R.id.content, WifiFragment, Constant.FRAGMENTTAG[7]);
                } else
                    transaction.show(WifiFragment);
                break;
            case R.id.nav_ecard:
                if (cardfragment == null) {
                    // 如果studyMaterialsFragment为空，则创建一个并添加到界面上
                    cardfragment = new EcardFragmentImp();
                    transaction.add(R.id.content, cardfragment, Constant.FRAGMENTTAG[8]);
                } else {
                    // 如果studyMaterialsFragment不为空，则直接将它显示出来
                    transaction.show(cardfragment);
                }
                break;
            /*case R.id.nav_study_materials:

                if (studyMaterialsFragment == null) {
                    // 如果studyMaterialsFragment为空，则创建一个并添加到界面上
                    studyMaterialsFragment = new StudyMaterialsFragment();
                    transaction.add(R.id.content, studyMaterialsFragment, Constant.FRAGMENTTAG[3]);
                } else {
                    // 如果studyMaterialsFragment不为空，则直接将它显示出来
                    transaction.show(studyMaterialsFragment);
                }
                break;
            case R.id.nav_find_lost:


                if (findLostFragment == null) {
                    // 如果findLostFragment为空，则创建一个并添加到界面上
                    findLostFragment = new FindLostFragment();
                    transaction.add(R.id.content, findLostFragment, Constant.FRAGMENTTAG[4]);
                } else {
                    // 如果findLostFragment不为空，则直接将它显示出来
                    transaction.show(findLostFragment);
                }
                break;
            case R.id.nav_charge:

                if (chargeFragment == null) {
                    // 如果chargeFragment为空，则创建一个并添加到界面上
                    chargeFragment = new ChargeFragment();
                    transaction.add(R.id.content, chargeFragment, Constant.FRAGMENTTAG[5]);
                } else {
                    // 如果chargeFragment不为空，则直接将它显示出来
                    transaction.show(chargeFragment);
                }

                break;*/
            default:
                break;
        }
        transaction.commit();
    }

    private static void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mainPageFragment != null) {
            SALog.d("MainActity", "Hidemain");
            fragmentTransaction.hide(mainPageFragment);
        }
        if (gradesFragment != null) {
            SALog.d("MainActity", "HideGrades");
            fragmentTransaction.hide(gradesFragment);
        }

        if (scheduleFragment != null) {
            SALog.d("MainActity", "Hideschedule");
            fragmentTransaction.hide(scheduleFragment);
        }
        if (studyMaterialsFragment != null) {
            fragmentTransaction.hide(studyMaterialsFragment);
        }
        if (WifiFragment != null) {
            SALog.d("MainActity", "HideWifi");

            fragmentTransaction.hide(WifiFragment);
        }
        if (libraryFragment != null) {
            SALog.d("MainActity", "HideLibrary");
            fragmentTransaction.hide(libraryFragment);
        }
        if (cardfragment != null) {
            fragmentTransaction.hide(cardfragment);
        }

        if (chargeFragment != null) {
            fragmentTransaction.hide(chargeFragment);
        }
        if (findLostFragment != null) {
            fragmentTransaction.hide(findLostFragment);
        }

    }
}
