package com.example.administrator.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PublicationActivity extends AppCompatActivity {

    private ListView listView;
    private MyAdapter adapter;
    private boolean isPaging;//是否进行分页操作

    private String CITY_PATH="http://192.168.1.101:8080/Web_ListView_Paging/CityAction?pageNo=";
    List<String> total=new ArrayList<String>();//放置记录的总条数

    private ProgressDialog dialog;
    private static int pageNo=1;
    private static String  keyword  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        listView=(ListView) this.findViewById(R.id.pubListView);
       // adapter=new MyAdapter();
       // adapter.binData(getData());
       // listView.setAdapter(adapter);

        dialog=new ProgressDialog(PublicationActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("loading...");
        pageNo = 1 ;
        keyword = "" ;
        new MyTask().execute(pageNo+"" , keyword );
        adapter=new MyAdapter();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if(isPaging&&scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    new MyTask().execute(pageNo+"" , keyword );
                    Toast.makeText(PublicationActivity.this, "分页，获取数据"+pageNo, 1).show();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                isPaging=(firstVisibleItem+visibleItemCount==totalItemCount);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    class MyTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<String> doInBackground(String... params) {
            // TODO Auto-generated method stub

            String result= HttpUtil.httpGet(params);//用一个http协议取出数据
            //List<String> list=JsonTools.parseJsonList(jsonString);
            long  pageNo = Long.valueOf(params[0]);
            List<String> list = new ArrayList<>() ;
            try{
                JSONObject jsonObject = new JSONObject(result) ;
                JSONObject  jsonResult  =  jsonObject.getJSONObject("results");
                int  total = jsonResult.getInt("total") ;
                JSONArray pubArray = jsonResult.getJSONArray("commentlist") ;
                for ( int  i = 0 ; i< pubArray.length()  ; i++){
                    JSONObject  obj =    (JSONObject)pubArray.get(i) ;
                    String  pubTitle =  obj.getString("pubTitle")  ;
                    Long num = pageNo * 10 +i-10 ;
                    list.add("第"+num +"条："+pubTitle )  ;
                    //System.out.println(i+"=====================服务器返回的信息：：" + pubTitle );
                }
            }catch ( Exception e){
               Log.e("publication" , e.toString() );
            }
            if(list == null || list.size() == 0 ){
                list.add("没有查询到记录！")  ;
            }
           /* for(int i = 0 ; i<15 ; i++){
                Long num = pageNo * 10 +i-10 ;
                list.add("我爱你+"+num )  ;
            }*/
            return list;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //adapter=new MyAdapter();
            total.addAll(result);
            adapter.binData(total);//解决下拉不能返回
            if(pageNo==1){
                listView.setAdapter(adapter);
            }//解决每次刷新之后都返回到第一项
            //listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            pageNo++;//更新UI的时候页码加1
            dialog.dismiss();
        }

    }



    //自定义适配器
    class MyAdapter extends BaseAdapter {

        List<String> list;//本地化list
        public MyAdapter() {
            // TODO Auto-generated constructor stub
        }

        //绑定数据
        public void binData(List<String> list){
            this.list=list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView textView=null;
            if(convertView==null){
                textView=new TextView(PublicationActivity.this);
            }else{
                textView=(TextView) convertView;
            }
            textView.setTextSize(20);
            textView.setText(list.get(position));
            return textView;
        }

    }

    //数据源
    public List<String> getData(){
        List<String> list=new ArrayList<String>();
        for(int i=0;i<=35;i++){
            list.add("CCNU"+i);
        }
        return list;
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
