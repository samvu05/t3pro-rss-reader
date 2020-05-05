package com.sam.t3prorssreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvTitle;
    private ArrayList<String> arrayTitle;
    private ArrayList<String> arrayLink;
    private ArrayAdapter adapter;
    private ImageButton btnEditLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        listenClick();
        new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
    }

    private void initWidget() {
        btnEditLink = findViewById(R.id.btn_edit_link);
        lvTitle = findViewById(R.id.lvTitle);
        arrayTitle = new ArrayList<>();
        arrayLink = new ArrayList<>();
        adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayTitle);
        lvTitle.setAdapter(adapter);
    }

    private void listenClick() {
        lvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("link",arrayLink.get(i));
                startActivity(intent);
            }
        });
        btnEditLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditLinkActivity.class);
                startActivityForResult(intent,10);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(10, 1, data);
        if (requestCode == 10){
            if (resultCode == 1){
                String urlResult = data.getStringExtra("link");
                new ReadRSS().execute(urlResult);
                initWidget();
                listenClick();
                Toast.makeText(this,urlResult,Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class ReadRSS extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());fileList();
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");

            String tieuDe ="";
            String link="";
            for (int i= 0; i< nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i);
                tieuDe = parser.getValue(element,"title");
                arrayTitle.add(tieuDe);
                link = parser.getValue(element,"link");
                arrayLink.add(link);
            }
            adapter.notifyDataSetChanged();
        }
    }
}