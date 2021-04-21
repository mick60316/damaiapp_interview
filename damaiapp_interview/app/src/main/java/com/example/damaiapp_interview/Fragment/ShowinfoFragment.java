package com.example.damaiapp_interview.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.damaiapp_interview.R;
import com.example.damaiapp_interview.DataStruct.Attraction;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ShowinfoFragment extends Fragment  implements View.OnClickListener, TextWatcher {
    private NavController navController;
    private Attraction attraction;
    private String apiRoot = "https://script.google.com/macros/s/AKfycbyvk9Gxa5UisCySFYasojru2sZnsazNxHqP1FrjduFeHN-5RCs/exec?action=search&id=%d";
    private final static String TAG ="ShowinfoFragment";
    private ImageView imageView;
    private Button nextImageButton,preImageButton;
    private List<Bitmap> images =new ArrayList<>();
    private int imageIndex= 0;
    private TextView nameTextView,introTextview;
    private EditText remindEditText;
    private final static int UPDATE_NAME_TEXTVIEW=0;
    private final static int UPDATE_INTRO_TEXTVIEW=1;
    private final static int UPDATE_REMIND_TEXTVIEW=2;
    private int currentId;

    private OkHttpClient client;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_NAME_TEXTVIEW:
                    nameTextView.setText(msg.obj.toString());
                    break;
                case UPDATE_INTRO_TEXTVIEW:
                    introTextview.setText(msg.obj.toString());
                    break;
                case UPDATE_REMIND_TEXTVIEW:
                    remindEditText.setText(msg.obj.toString());
                default:
                    break;
            }
        }
    };


    private void updateInfoToUI(int id)
    {
        client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        Request request = new Request.Builder()
                .url(String.format(apiRoot,id))
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.e(TAG,"Http get fail "+ e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG,result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String name = jsonObject.getString("name");
                    String intro =jsonObject.getString("intro");
                    String remind=jsonObject.getString("remind");
                    String imageUrls = jsonObject.getString("images");

                    Message msg = Message.obtain();
                    msg.what = UPDATE_NAME_TEXTVIEW;
                    msg.obj = name;
                    mHandler.sendMessage(msg);
                    msg = Message.obtain();
                    msg.what = UPDATE_INTRO_TEXTVIEW;
                    msg.obj = intro;
                    mHandler.sendMessage(msg);
                    msg = Message.obtain();
                    msg.what = UPDATE_REMIND_TEXTVIEW;
                    msg.obj = remind;
                    mHandler.sendMessage(msg);



                    String [] imageUrl = imageUrls.split(",");
                    for (int i =0;i<imageUrl.length ;i++)
                    {
                        if (imageUrl[i].length() >1)
                        {
                            new DownloadImageTask().execute(imageUrl[i]);
                        }
                    }
                }catch (JSONException e)
                {



                }


            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navController =findNavController (this);
        return inflater.inflate(R.layout.showinfo_fragment, container, false);
    }

    private void linkUserInterface (View view)
    {
        imageView =view.findViewById(R.id.imageview);
        nextImageButton =view.findViewById(R.id.next_image_button);
        preImageButton =view.findViewById(R.id.pre_image_button);
        nameTextView =view.findViewById(R.id.name_textview);
        introTextview =view.findViewById(R.id.intro_textview);
        remindEditText =view.findViewById(R.id.remind_input_text);

        nextImageButton.setOnClickListener(this);
        preImageButton.setOnClickListener(this);
        nextImageButton.setVisibility(View.INVISIBLE);
        preImageButton.setVisibility(View.INVISIBLE);

        remindEditText.addTextChangedListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle =getArguments();
        attraction= (Attraction) bundle.getSerializable("Attraction");

        currentId =attraction.getId();

        updateInfoToUI(currentId);
        linkUserInterface(view);
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        public DownloadImageTask() {

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(images.size()==0)
            {
                imageView.setImageBitmap(result);
            }
            else
            {
                nextImageButton.setVisibility(View.VISIBLE);
                preImageButton.setVisibility(View.VISIBLE);
            }
            images.add(result);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.next_image_button:
                imageIndex++;
                imageIndex%= images.size();
                imageView.setImageBitmap(images.get(imageIndex));
                break;
            case R.id.pre_image_button:
                imageIndex--;
                if (imageIndex <0 ) imageIndex =images.size()-1;
                imageView.setImageBitmap(images.get(imageIndex));
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        postRemind(currentId,remindEditText.getText().toString());
    }
    private void postRemind (int id ,String remind)
    {
        Log.i(TAG,id+" "+remind);
        RequestBody formBody = new FormBody.Builder()
                .add("action", "remind")
                .add("id", ""+id)
                .add("remind", remind)
                .build();

        Request request = new Request.Builder()
                .url("https://script.google.com/macros/s/AKfycbyvk9Gxa5UisCySFYasojru2sZnsazNxHqP1FrjduFeHN-5RCs/exec")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG,"Http post fail "+ e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG,"Http post remind success ");
            }
        });

    }


}
