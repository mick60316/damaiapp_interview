package com.example.damaiapp_interview.Tools;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damaiapp_interview.DataStruct.Attraction;
import com.example.damaiapp_interview.R;

import java.util.List;

public class RecycleViewAdapter extends  RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context context;
    private NavController navController;
    private List<Attraction> attractions;
    final static String TAG = "RecycleViewAdapter";

    public RecycleViewAdapter(Context context, List<Attraction> attractions,NavController navController)
    {
        this.context=context;
        this.navController=navController;
        this.attractions=attractions;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        Button button;

        View itemView;
        private ViewHolder(View itemView){
            super(itemView);
            this.itemView =itemView;
            button=itemView.findViewById(R.id.button);

        }

    }
    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder");
        View itemView = LayoutInflater.from(context).inflate(R.layout.attraction_recycle_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        final Attraction attraction =attractions.get(position);
        System.out.println(position+" "+attraction.getName());
        Button bt =holder.button;
        bt.setText(attraction.getId() +" "+attraction.getName());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle =new Bundle();
                bundle.putSerializable("Attraction",attraction);
                Log.i(TAG,"Attraction click "+attraction.getName());
                navController.navigate(R.id.action_searchFragment_to_showinfoFragment,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }
}
