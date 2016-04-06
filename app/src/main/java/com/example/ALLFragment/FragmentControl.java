package com.example.ALLFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.ALLFragment.charge.ChargeFragment;
import com.example.ALLFragment.find_lost.FindLostFragment;
import com.example.ALLFragment.library.LibraryFragment;
import com.example.ALLFragment.main_page.MainPageFragment;
import com.example.ALLFragment.setting.SettingFragment;
import com.example.ALLFragment.study_materials.StudyMaterialsFragment;
import com.example.swuassistant.Constant;
import com.example.swuassistant.R;
import com.example.ALLFragment.swujw.grade.GradesFragment;
import com.example.ALLFragment.swujw.schedule.ScheduleFragment;

/**
 * Created by 张孟尧 on 2016/4/6.
 */
public class FragmentControl
{
    /*主界面布局*/
    private static MainPageFragment mainPageFragment;
    /*课程表界面布局*/
    private static ScheduleFragment scheduleFragment;
//    private static ScheduleTableFragment scheduleTableFragment;

    /*成绩界面布局*/
    private static GradesFragment gradesFragment;
    /*学习资料界面布局*/
    private static StudyMaterialsFragment studyMaterialsFragment;
    /*图书馆界面布局*/
    private static LibraryFragment libraryFragment;
    /*水电费界面布局*/
    private static ChargeFragment chargeFragment;
    /*失物找寻界面布局*/
    private static FindLostFragment findLostFragment;
private static SettingFragment settingFragment;

    private FragmentManager fragmentManager;
    public FragmentControl(FragmentManager fragmentManager)
    {
        this.fragmentManager=fragmentManager;
    }
    public void initFragment(FragmentManager fragmentManager)
    {
        FragmentTransaction transaction;
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();

        mainPageFragment = new MainPageFragment();
        transaction.add(R.id.content, mainPageFragment, Constant.FRAGMENTTAG[0]);


        scheduleFragment = new ScheduleFragment();
        transaction.add(R.id.content, scheduleFragment, Constant.FRAGMENTTAG[1]);

        gradesFragment = new GradesFragment();
        transaction.add(R.id.content, gradesFragment, Constant.FRAGMENTTAG[2]);

        studyMaterialsFragment = new StudyMaterialsFragment();
        transaction.add(R.id.content, studyMaterialsFragment, Constant.FRAGMENTTAG[3]);

        findLostFragment = new FindLostFragment();
        transaction.add(R.id.content, findLostFragment, Constant.FRAGMENTTAG[4]);

        chargeFragment = new ChargeFragment();
        transaction.add(R.id.content, chargeFragment, Constant.FRAGMENTTAG[5]);

        libraryFragment = new LibraryFragment();
        transaction.add(R.id.content, libraryFragment, Constant.FRAGMENTTAG[6]);
        transaction.commit();
        hideFragments(transaction);
    }


    public void fragmentStateCheck(Bundle saveInstanceState,FragmentManager fragmentManager,int fragmentPosition)
    {
        if (saveInstanceState == null)
        {
            fragmentSelection(fragmentPosition);
        } else
        {
            if (mainPageFragment != null)
            {
                mainPageFragment = (MainPageFragment)  fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[0]);
            }

            if (scheduleFragment != null)
            {
                scheduleFragment = (ScheduleFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[1]);
            }
            if (gradesFragment != null)
            {
                gradesFragment = (GradesFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[2]);
            }
            if (studyMaterialsFragment != null)
            {
                studyMaterialsFragment = (StudyMaterialsFragment)fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[3]);
            }
            if (findLostFragment != null)
            {
                findLostFragment = (FindLostFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[4]);
            }
            if (chargeFragment != null)
            {
                chargeFragment = (ChargeFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[5]);
            }
            if (libraryFragment != null)
            {
                libraryFragment = (LibraryFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[6]);
            }
            fragmentSelection(fragmentPosition);
        }
    }

    public void fragmentSelection(int id)
    {
        FragmentTransaction transaction;
        transaction = fragmentManager.beginTransaction();

        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (id)
        {
            case R.id.nav_main:
                if (mainPageFragment == null)
                {
                    // 如果mainPageFragment为空，则创建一个并添加到界面上
                    mainPageFragment = new MainPageFragment();
                    transaction.add(R.id.content, mainPageFragment, Constant.FRAGMENTTAG[0]);

                } else
                {
                    // 如果mainPageFragment不为空，则直接将它显示出来
                    transaction.show(mainPageFragment);

                }

                break;
            case R.id.nav_schedule:
                if (scheduleFragment == null)
                {
                    // 如果scheduleTableFragment为空，则创建一个并添加到界面上
                    scheduleFragment = new ScheduleFragment();
                    transaction.add(R.id.content, scheduleFragment, Constant.FRAGMENTTAG[1]);
                    ;
                } else
                {
                    // 如果scheduleFragment不为空，则直接将它显示出来

                    transaction.show(scheduleFragment);
                }

                break;
            case R.id.nav_grades:

                if (gradesFragment == null)
                {
                    // 如果GradesFragment为空，则创建一个并添加到界面上
                    gradesFragment = new GradesFragment();
                    transaction.add(R.id.content, gradesFragment, Constant.FRAGMENTTAG[2]);
                } else
                {
                    // 如果GradesFragment不为空，则直接将它显示出来
                    transaction.show(gradesFragment);
                }

                break;
            case R.id.nav_study_materials:

                if (studyMaterialsFragment == null)
                {
                    // 如果studyMaterialsFragment为空，则创建一个并添加到界面上
                    studyMaterialsFragment = new StudyMaterialsFragment();
                    transaction.add(R.id.content, studyMaterialsFragment, Constant.FRAGMENTTAG[3]);
                } else
                {
                    // 如果studyMaterialsFragment不为空，则直接将它显示出来
                    transaction.show(studyMaterialsFragment);
                }
                break;
            case R.id.nav_find_lost:


                if (findLostFragment == null)
                {
                    // 如果findLostFragment为空，则创建一个并添加到界面上
                    findLostFragment = new FindLostFragment();
                    transaction.add(R.id.content, findLostFragment, Constant.FRAGMENTTAG[4]);
                } else
                {
                    // 如果findLostFragment不为空，则直接将它显示出来
                    transaction.show(findLostFragment);
                }
                break;
            case R.id.nav_charge:

                if (chargeFragment == null)
                {
                    // 如果chargeFragment为空，则创建一个并添加到界面上
                    chargeFragment = new ChargeFragment();
                    transaction.add(R.id.content, chargeFragment, Constant.FRAGMENTTAG[5]);
                } else
                {
                    // 如果chargeFragment不为空，则直接将它显示出来
                    transaction.show(chargeFragment);
                }

                break;
            case R.id.nav_library:

                if (libraryFragment == null)
                {
                    // 如果libraryFragrment为空，则创建一个并添加到界面上
                    libraryFragment = new LibraryFragment();
                    transaction.add(R.id.content, libraryFragment, Constant.FRAGMENTTAG[6]);
                } else
                {
                    // 如果libraryFragrment不为空，则直接将它显示出来
                    transaction.show(libraryFragment);
                }
                break;
            case R.id.action_settings:
                    settingFragment=new SettingFragment();
//                    transaction.add(R.id.content,settingFragment,Constant.FRAGMENTTAG[6]);
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction fragmentTransaction)
    {
        if (mainPageFragment != null)
        {
            fragmentTransaction.hide(mainPageFragment);
        }
        if (gradesFragment != null)
        {
            fragmentTransaction.hide(gradesFragment);
        }

        if (scheduleFragment != null)
        {
            fragmentTransaction.hide(scheduleFragment);
        }
        if (studyMaterialsFragment != null)
        {
            fragmentTransaction.hide(studyMaterialsFragment);
        }
        if (libraryFragment != null)
        {
            fragmentTransaction.hide(libraryFragment);
        }
        if (chargeFragment != null)
        {
            fragmentTransaction.hide(chargeFragment);
        }
        if (findLostFragment != null)
        {
            fragmentTransaction.hide(findLostFragment);
        }

    }
}
