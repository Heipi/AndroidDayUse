package com.fight.light.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fight.light.R;
import com.fight.light.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yawei.kang on 2018/1/26.
 */

public class TaskFragment extends Fragment implements TaskModel.View{

    private TaskModel.Presenter mPresenter;
    @BindView(R.id.tasks_list)
    ListView tasks_list;
    List<String> stringList  = new ArrayList<>();
    private TaskAdapter mAdapter;
    public static TaskFragment newInstance(){
        return new TaskFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* stringList.add("HAHHA");
        stringList.add("67666");
        stringList.add("hahaa");*/

    }

    @Override
    public void setPresenter(TaskModel.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismisProgress() {

    }

    @Override
    public void showTip(String message) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_frag,container,false);
        ButterKnife.bind(this,root);
        mAdapter = new TaskAdapter(stringList,mtaskItemListener);
        tasks_list.setAdapter(mAdapter);

        /*tasks_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShortToast("666");
//                clearTaskItem(position);
            }
        });*/

//        mPresenter.start();
        return root;
    }
TaskItemListener mtaskItemListener = new TaskItemListener() {
    @Override
    public void onTaskClick(int position) {
          ToastUtils.showShortToast("77777777"+position);
    }

    @Override
    public void onCompleteTaskClicl() {

    }
};


    @Override
    public void showList(List<String> list) {
        stringList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearTaskItem(int position) {
        ToastUtils.showShortToast("55555");
          stringList.remove(position);
          mAdapter.notifyDataSetChanged();
    }

    private class TaskAdapter extends BaseAdapter{
        private List<String> stringList;
        private TaskItemListener mItemListener;
        public TaskAdapter(List<String> stringList,TaskItemListener itemListener) {
                this.stringList= stringList;
                this.mItemListener  = itemListener;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position,  View convertView, ViewGroup parent) {
                     ViewHolder viewHolder;
                     if(convertView ==null) {
                         viewHolder =  new ViewHolder();
                         convertView = LayoutInflater.from(getActivity()).inflate(R.layout.task_item, parent, false);
                         viewHolder.title = convertView.findViewById(R.id.title);
                         convertView.setTag(viewHolder);
                     }else{
                         viewHolder = (ViewHolder) convertView.getTag();
                     }
                  viewHolder.title.setText(stringList.get(position));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener!= null){
                        mItemListener.onTaskClick(position);
                    }
                }
            });
                   return convertView;
        }
        class ViewHolder {
         private TextView title;

        }
    }


    public interface TaskItemListener{

        void onTaskClick(int position);
        void onCompleteTaskClicl();

    }
}
