package com.example.retrofittutorialtwo;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private TextView textViewResult;
    private JsonPlaceholderApi jsonPlaceholderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult = (TextView) findViewById(R.id.text_view_result);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

       getPosts();
       // getComments();


    }

    private void getPosts(){

        Map<String, String>parameters =  new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort", "id");
        parameters.put("_order","desc");

        Call<List<Post>>call = jsonPlaceholderApi.getPosts(parameters);

//        Call<List<Post>>call = jsonPlaceholderApi.getPosts(new Integer[]{1,2,3}, "id", "desc");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("code "+ response.code());
                    return;
                }

                List<Post>posts = response.body();

                for(Post post:posts){
                    String content="";

                    content +=  "ID: "+post.getId() + "\n";
                    content +=  "Userid :" + post.getUserId() + "\n";
                    content += "Title :" + post.getTitle() + "\n";
                    content += "Text :" + post.getText() + "\n\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void getComments(){
        Call<List<Comment>>call = jsonPlaceholderApi.getComments(3);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code :"+ response.code());
                    return;
                }
                List<Comment>comments = response.body();

                for(Comment comment: comments){

                    String content ="";

                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getId() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}