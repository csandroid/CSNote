package commm.example.android.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by android on 2017/12/25.
 */

public class NotesAdapter extends BaseAdapter {

    Context mContext;//上下文对象
    int layoutId;//布局id
    List<Notebean> myList;//数据源
    public NotesAdapter(Context context,int id,List<Notebean> list){
        mContext=context;
        layoutId=id;
        myList=list;

    }

    //返回列表项的数量
    @Override
    public int getCount() {
        return myList.size();
    }
    //返回某个列表项内容
    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    //返回某个列表项id
    @Override
    public long getItemId(int position) {
        return position;
    }
    //返回每一行item的布局
    /*
    int position:表示第几项的视图
    View convertView:表示当前项代表的视图
    ViewGroup parent:父布局，指ListView本身
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //将布局文件id解析为视图
        convertView = LayoutInflater.from(mContext).inflate(layoutId,parent,false);

        //绑定view中的控件
        TextView titleText = (TextView) convertView.findViewById(R.id.title);
        TextView bodyText=(TextView)convertView.findViewById(R.id.body);

        //为TextView控件设置相对应的文本
        Notebean m=myList.get(position);
        titleText.setText("姓名:"+m.getTitle());
        bodyText.setText("学号:"+m.getBody());


        //为布局设置文本数据后返回，这个convertView就是ListView的子项
        return convertView;
    }
}



