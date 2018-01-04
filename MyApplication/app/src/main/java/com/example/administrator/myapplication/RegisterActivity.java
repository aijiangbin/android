package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Context  mContext  =RegisterActivity.this ;

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("reg","---------onStart----------");
    }

    @Override
    protected void onStop() {
        Log.e("reg","---------onStop----------");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("reg","---------onDestroy----------");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.e("reg","---------onPause----------");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e("reg","---------onResume----------");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent  intent = getIntent() ;
       Bundle  bundle =  intent.getExtras() ;
        Log.e("reg","---------onCreate----------" + bundle.get("key"));
        Log.e("reg","---------baseActivity----------" + getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

          Button   commit  =  findViewById(R.id.commit)  ;
          commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  content = "" ;
                 EditText  name =  findViewById(R.id.regName)   ;
                 content +=  (    "姓名："+ name.getText().toString()  ) ;
                RadioGroup  radgroup =   findViewById(R.id.radioGroup)   ;
                for (int i = 0; i < radgroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radgroup.getChildAt(i);
                    if (rd.isChecked()) {
                        content +=  ( "性别："+rd.getText()  ) ;
                        break;
                    }
                }
                //Toast.makeText(  getApplicationContext() , "你注册的信息："+content , Toast.LENGTH_LONG ).show(); ;
                write("person.txt" , content );
            }
        });

    }


  public  void write( String  filename , String  filedetail){
      FileHelper fHelper = new FileHelper(mContext);
      try {
          fHelper.save(filename, filedetail);
          Toast.makeText(getApplicationContext(), "数据写入成功", Toast.LENGTH_SHORT).show();
      } catch (Exception e) {
          e.printStackTrace();
          Toast.makeText(getApplicationContext(), "数据写入失败", Toast.LENGTH_SHORT).show();
      }
  }


}
