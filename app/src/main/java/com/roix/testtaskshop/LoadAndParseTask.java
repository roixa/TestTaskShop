package com.roix.testtaskshop;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by roix on 16.11.2016.
 */

public class LoadAndParseTask extends AsyncTask<LoadAndParseTask.TaskCallback,Void,List<Item>>{

    private TaskCallback callback;
    @Override
    protected List<Item> doInBackground(TaskCallback... params) {
        ArrayList<Item> ret=null;
        callback=params[0];
        try {
            ret=new ArrayList<>();
            URL url = new URL(Constants.FILE_URL);
            URLConnection conn = url.openConnection();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            NodeList nodes = doc.getElementsByTagName(Constants.TAG_ITEM);


            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                Item item=new Item();
                item.setId(element.getAttribute(Constants.TAG_ID));
                item.setName(element.getAttribute(Constants.TAG_NAME));
                item.setCost(element.getAttribute(Constants.TAG_COST));
                ret.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("@@@","LoadAndParseTask  Exception");

        }
        return ret;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
        if(callback!=null)callback.onLoad(items);
    }


    public interface TaskCallback{
        void onLoad(List<Item> items);
    }
}
