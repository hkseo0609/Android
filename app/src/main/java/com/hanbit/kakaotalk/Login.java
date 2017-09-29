package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Context context = Login.this;
        final EditText inputId= (EditText) findViewById(R.id.inputId);
        final EditText inputPwd = (EditText) findViewById(R.id.inputPwd);
        final MemberLogin login = new MemberLogin(context);

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = String.valueOf(inputId.getText().toString());
                final String pwd = String.valueOf(inputPwd.getText().toString());
                Log.d("입력된 비번:",pwd);
                new Service.IPredicate() {
                    @Override
                    public void excute() {
                        if(login.excute(id,pwd)){
                            Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context,MemberList.class));
                        }else{
                            Toast.makeText(context,"로그인 실패",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context,Login.class));
                        };

                    }
                }.excute();
            }
        });
        findViewById(R.id.cancleBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputId.setText("");
                inputPwd.setText("");
            }
        });
    }

    private abstract class LoginQuery extends index.QueryFactory{
        SQLiteOpenHelper helper;
        public LoginQuery(Context context) {
            super(context);
            helper = new index.SqLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class MemberLogin extends LoginQuery{
        public MemberLogin(Context context) {
            super(context);
        }

        public boolean excute(String id, String pwd){
            return super
                    .getDatabase()
                    .rawQuery(String.format("select * from %s where %s = '%s' and %s = '%s';", Cons.MEM_TBL, Cons.SEQ, id, Cons.PASS, pwd),null)
                    .moveToLast();
        }
    }
}
