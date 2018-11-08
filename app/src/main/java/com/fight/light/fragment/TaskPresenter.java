package com.fight.light.fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yawei.kang on 2018/1/26.
 */

public class TaskPresenter implements TaskModel.Presenter{

    private TaskModel.View mTaskView;
    List<String> stringList  = new ArrayList<>();
    public TaskPresenter(TaskModel.View tasksView) {
           this.mTaskView = tasksView;
           mTaskView.setPresenter(this);
    }


    @Override
    public void clearTask() {
//       mTaskView.
    }

/*    @Override
    public void start() {
        stringList.add("HAHHA");
        stringList.add("67666");
        stringList.add("hahaa");
        mTaskView.showList(stringList);
    }*/
}
