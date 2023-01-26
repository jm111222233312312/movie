package com.cookandroid.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Button btn;
    private MovieInterface apiInterface;
    List<Movie> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        //list는 인터페이스라 초기화시킬때 아래처럼 요구.
        list = new ArrayList<>();//빈껍데기
        adapter = new MovieAdapter(list);//빈껍데기에 데이터 뿌리기
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);//리니어레이아웃메니저를 이용해 어뎁터와 뷰를 결합한 레이아웃을 만들고
        recyclerView.setLayoutManager(linearLayoutManager);//만든 레아이웃을 뿌림(setLayoutManager)
        recyclerView.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            //retrofit2 이용. 버튼 클릭 시 네트워크에 연결,어뎁터에 추가, 데이터 뿌리기->먼저 MovieClient class 파일 생성하기
            @Override
            public void onClick(View view) {
            apiInterface = MovieClient.getClient().create(MovieInterface.class);
            Call<List<Movie>> call = apiInterface.goGetMovie();

            call.enqueue(new Callback<List<Movie>>() {
                @Override
                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                    Log.d("TAG",response.code()+"");
                    Log.d("TAG",response.toString()+"");
                    String displayResponse = "";

                    List<Movie> resource = response.body();//body에 데이터 넣기
                    Log.d("TAG",resource.size()+"");//size=개수
                    Log.d("adapter.getItemCount()",adapter.getItemCount()+"");
                    for (Movie zip : resource){
                        list.add(zip);
                    }
                    Toast.makeText(getApplicationContext(),adapter.getItemCount()+"",Toast.LENGTH_SHORT).show();
               adapter.notifyDataSetChanged();//바뀌면 화면에 보여줌
                }

                @Override
                public void onFailure(Call<List<Movie>> call, Throwable t) {

                }
            });

            }
        });
    }
}