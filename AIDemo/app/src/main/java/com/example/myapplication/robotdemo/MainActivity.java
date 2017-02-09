package com.example.myapplication.robotdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.bean.ChatMessage;
import com.example.myapplication.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {
    private ListView mListView = null;
    private ChatMessageAdapter mAdapter = null;
    private List<ChatMessage> mDatas = null;
    private EditText mInputMsg = null;
    private Button mSendMsg = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatMessage fromMessage = (ChatMessage) msg.obj;
            mDatas.add(fromMessage);
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        initListener();
    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(MainActivity.this, "不说话小珈可不会和你聊天哦！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //输出流数据
                ChatMessage toMessage = new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();  //刷新
                mListView.setSelection(mDatas.size() - 1); //表示将列表移动到指定的mDatas.size()-1处。
                mInputMsg.setText("");
                new Thread() {
                    @Override
                    public void run() {
                        //因为输入流数据涉及到网络操作，写到线程里去
                        ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        mHandler.sendMessage(m);
                    }
                }.start();

            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("你好,小珈为您服务", ChatMessage.Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_msg);
        mInputMsg = (EditText) findViewById(R.id.et_msg);
        mSendMsg = (Button) findViewById(R.id.send_msg);
    }
}
