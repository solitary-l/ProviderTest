package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick (View v){
                //添加数据
                Uri uri = Uri.parse("content://com.example.wordsbook.provider/wordbook");
                ContentValues values = new ContentValues();
                values.put("word","solitary");
                values.put("meanings" ,"孤独");
                values.put("exampleSentence","paul was a shy,pleasant,solitary man！");
                Uri newUri = getContentResolver().insert(uri,values);
                newword = newUri.getPathSegments().get(1);
            }
        });

        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick (View v){
                //查询数据
                Uri uri = Uri.parse("content://com.example.wordsbook.provider/wordbook");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if (cursor !=null)
                {
                    while (cursor.moveToNext()){
                        String word = cursor.getString(cursor.getColumnIndex("word"));
                        String meanings = cursor.getString(cursor.getColumnIndex("meanings"));
                        String exampleSentence = cursor.getString(cursor.getColumnIndex("exampleSentence"));
                        Log.d("MainActivity","word name is "+word);
                        Log.d("MainActivity","meanings :"+meanings);
                        Log.d("MainActivity","exampleSentence:"+exampleSentence);
                    }
                    cursor.close();
                }
            }

        });
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick (View v){
                //更新数据
                Uri uri = Uri.parse("content://com.example.wordsbook.provider/wordbook/"+newword);
                ContentValues values = new ContentValues();
                values.put("word","solitary");
                values.put("meanings","独立生活能力");
                values.put("exampleSentence","solitary");
                getContentResolver().update(uri,values,null,null);
            }
        });
        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //删除数据
                Uri uri = Uri.parse("content://com.example.wordsbook.provider/wordbook/"+newword);
                getContentResolver().delete(uri,null,null);
            }
                                      }

        );
    }
}
