package com.example.damaiapp_interview.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damaiapp_interview.R;
import com.example.damaiapp_interview.DataStruct.Attraction;
import com.example.damaiapp_interview.Tools.RecycleViewAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText searchInputText;
    private NavController navController;

    private Context context;
    private final int UPDATE_RECYCLE_VIEW=0;
    final String url  ="https://script.google.com/macros/s/AKfycbyvk9Gxa5UisCySFYasojru2sZnsazNxHqP1FrjduFeHN-5RCs/exec?action=all";
    private List<Attraction> attractions=new ArrayList<>();

    private final  static String TAG = "SearchFragment";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_RECYCLE_VIEW:

                    List <Attraction> _attractions =(List<Attraction>)msg.obj;
                    recyclerView.setAdapter(new RecycleViewAdapter(context,_attractions,navController));
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navController =findNavController (this);
        return inflater.inflate(R.layout.search_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView=view.findViewById(R.id.attractions_recycler_view);
        searchInputText =view.findViewById(R.id.search_input_text);
        searchInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keyword =searchInputText.getText().toString();

                Log.i(TAG,"Keyword change to "+keyword);
                if (keyword =="") {
                    recyclerView.setAdapter(new RecycleViewAdapter(context,attractions,navController));
                }
                else {
                    List <Attraction> filterAttractions =new ArrayList<>();
                    for (int i =0;i<attractions.size();i++)
                    {
                        Attraction attraction =attractions.get(i);
                        if (attraction.getName().contains(keyword))
                        {
                            filterAttractions.add(attraction);
                        }
                    }
                    recyclerView.setAdapter(new RecycleViewAdapter(context,filterAttractions,navController));

                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (attractions.size() ==0) {
            updateAttractionList();
        }




    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;


    }

    public void updateAttractionList ()
    {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.e(TAG,"Http get fail "+ e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                /**取得回傳*/
                String result = response.body().string();
                Log.i(TAG,"Http get successful");


                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String dataJsonObject =jsonObject.getString("data");
                    JSONArray attractionsObjArray  =new JSONArray(dataJsonObject);


                    for (int i =0;i<attractionsObjArray.length() ;i++)
                    {
                        JSONObject attractionObj = attractionsObjArray.getJSONObject(i);
                        int id  =attractionObj.getInt("id");
                        String name =attractionObj.getString("name");
                        Attraction attraction =new Attraction(id,name);
                        attractions.add(attraction);
                    }

                    Message msg = Message.obtain();
                    msg.what = UPDATE_RECYCLE_VIEW;
                    msg.obj = attractions;
                    mHandler.sendMessage(msg);
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

}
