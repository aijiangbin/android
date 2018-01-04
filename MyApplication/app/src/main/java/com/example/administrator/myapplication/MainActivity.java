package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity   implements AdapterView.OnItemClickListener{


    private String[] names = new String[]{"B神", "基神", "曹神"};
    private String[] says = new String[]{"无形被黑，最为致命", "大神好厉害~", "我将带头日狗~"};
    private int[] imgIds = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher};

    private List<Animal> mData = null;
    private Context mContext;
    private AnimalAdapter mAdapter = null;
    private ListView list_animal;

    private LinearLayout ly_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adapt_1();
        //readContact();
       // testAnimalAdapter();
    }



    //注册
    public void  registerPerson(  View  view ){
        Button  button  = (Button) view ;
       ;
        Log.e("reg" , " 注册 "+ button.getText().toString() )  ;
        Intent  regIntent = new Intent(this , RegisterActivity.class) ;
         Bundle  bundle = new Bundle() ;
         bundle.putString("key" , "欢迎来到注册也没");
         regIntent.putExtras(bundle);
        startActivity(regIntent);

    }
    //菜单
    public void  menuTest(  View  view ){
        Button  button  = (Button) view ;
        Log.e("menuTest" , " 菜单 " )  ;
        Intent  intent = new Intent(this , MenuActivity.class) ;
        startActivity(intent);

    }
    //成果
    public void  pubMenu(  View  view ){
        Button  button  = (Button) view ;
        Log.e("menuTest" , " 成果 " )  ;
        Intent  intent = new Intent(this , PublicationActivity.class) ;
        startActivity(intent);

    }

    //2.4.4 Adapter基础讲解  ArrayAdapter
    public void adapt(){
        //要显示的数据
        String[] strs = {"基神","B神","翔神","曹神","J神"};
        //创建ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_expandable_list_item_1,strs);
        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
        ListView list_test = (ListView) findViewById(R.id.list_test);
        list_test.setAdapter(adapter);
    }
    //2.4.4 Adapter基础讲解   SimpleAdapter
    public void adapt_1(){
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("touxiang", imgIds[i]);
            showitem.put("name", names[i]);
            showitem.put("says", says[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
        ListView listView = (ListView) findViewById(R.id.list_test);
        listView.setAdapter(myAdapter);
    }
      // SimpleCursorAdapter
    public  void readContact(){
        ListView list_test = (ListView) findViewById(R.id.list_test);
        //读取联系人
        Cursor cursor = getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        SimpleCursorAdapter spcAdapter = new SimpleCursorAdapter(this,R.layout.list_contact,cursor,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER},
                new int[]{R.id.list_name,R.id.list_phone});
        list_test.setAdapter(spcAdapter);
    }

    public  void testAnimalAdapter(){
        mContext = MainActivity.this;
        list_animal = (ListView) findViewById(R.id.list_test);
        //动态加载顶部View和底部View
        final LayoutInflater inflater = LayoutInflater.from(this);
        View headView = inflater.inflate(R.layout.view_header, null, false);
        View footView = inflater.inflate(R.layout.view_footer, null, false);

        mData = new LinkedList<Animal>();
        mData.add(new Animal("狗说", "你是狗么?", R.mipmap.ic_icon_dog));
        mData.add(new Animal("牛说", "你是牛么?", R.mipmap.ic_icon_cow));
        mData.add(new Animal("鸭说", "你是鸭么?", R.mipmap.ic_icon_duck));
        mData.add(new Animal("鱼说", "你是鱼么?", R.mipmap.ic_icon_fish));
        mData.add(new Animal("马说", "你是马么?", R.mipmap.ic_icon_horse));
        mAdapter = new AnimalAdapter((LinkedList<Animal>) mData, mContext);
        //添加表头和表尾需要写在setAdapter方法调用之前！！！
        list_animal.addHeaderView(headView);
        list_animal.addFooterView(footView);

        list_animal.setAdapter(mAdapter);
        list_animal.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("error"   , view.getId()+"--------" ) ;
        Toast.makeText(mContext,"你点击了第" + position + "项",Toast.LENGTH_LONG).show();
    }
}
