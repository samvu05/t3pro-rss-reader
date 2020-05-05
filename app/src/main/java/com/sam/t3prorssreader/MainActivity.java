package com.sam.t3prorssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
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
//            NodeList nodeList = document.getElementsByTagName("title");
            String tieuDe ="";
//            for (int i= 0; i< nodeList.getLength();i++){
//                Element element = (Element) nodeList.item(i);
//                tieuDe += parser.getValue(element,"title")+"\n";
//            }
//            Toast.makeText(MainActivity.this,tieuDe,Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this,document.getXmlVersion(),Toast.LENGTH_LONG).show();
        }
    }
}
