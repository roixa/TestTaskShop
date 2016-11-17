package com.roix.testtaskshop;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadAndParseTask.TaskCallback, View.OnClickListener{
    private RecyclerView recyclerView;
    private TextView total;
    private Button button;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        total=(TextView)findViewById(R.id.total);
        button=(Button)findViewById(R.id.buy);
        button.setOnClickListener(this);
        updateViews(null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadAndParseTask().execute(this);

    }

    @Override
    public void onLoad(List<Item> items) {
        this.items=items;
        recyclerView.setAdapter(new ItemsAdapter(items,this));
    }

    public void updateViews(List<Item> items){
        this.items=items;
        total.setText(calcTotal(items));
    }

    @Override
    public void onClick(View v) {
        if(items==null)return;
        sendEmail(formMessage(items));
    }

    private String formMessage(List<Item> items){
        StringBuilder builder=new StringBuilder();
        for(Item item:items)
            builder.append(item.getName()).append(" ").append(item.getCount()).append("\n");
        builder.append(calcTotal(items));
        return builder.toString();
    }

    private String calcTotal(List<Item> items){
        int totalCount=0;
        if(items!=null)
            for(Item item:items){
                totalCount+=Integer.parseInt(item.getCost())*item.getCount();
            }
        return ""+totalCount;
    }

    private void sendEmail(String body){

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My buy list");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
