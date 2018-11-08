package com.fight.light.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.fight.light.fragment.TaskModel;
import com.fight.light.fragment.TaskPresenter;
import com.fight.light.R;
import com.fight.light.base.BaseActivity;
import com.fight.light.fragment.TaskFragment;
import com.fight.light.util.FragmentUtils;
import com.fight.light.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    private TaskPresenter mTaskPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true); //显示左上角图片
        actionBar.setDisplayShowTitleEnabled(true);//显示标题

        drawerLayout.setStatusBarBackground(R.color.colorAccent);

        setupDrawerContent(nav_view);

        TaskFragment taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (taskFragment == null){
            taskFragment = TaskFragment.newInstance();
            FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),taskFragment,R.id.contentFrame);

        }
        mTaskPresenter = new TaskPresenter((TaskModel.View) taskFragment);


    }

    private void setupDrawerContent(NavigationView navigationView){

         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                      switch (item.getItemId()){
                          case R.id.list_navigation_menu_item:
                              ToastUtils.showShortToast("HAHHAH");
                              break;
                          case R.id.statistics_navigation_menu_item:
                              ToastUtils.showShortToast("GGAAG");
                              break;
                              default:
                                  break;
                      }
                          item.setCheckable(true);
                       drawerLayout.closeDrawers();
                        return true;
             }
         });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                ToastUtils.showShortToast("6666666");
                drawerLayout.openDrawer(Gravity.START);
                return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
