package com.isha.leave_hod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class List_adapter extends ArrayAdapter<Model> {

    //the hero list that will be displayed
    private List<Model> heroList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public List_adapter(List<Model> heroList, Context mCtx) {
        super(mCtx, R.layout.item, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.item, null, true);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.textView);
        TextView textViewId = listViewItem.findViewById(R.id.textView1);


        //Getting the hero for the specified position
        Model model = heroList.get(position);

        //setting hero values to textviews
        textViewName.setText(model.getName());
        textViewId.setText(model.getId());


        //returning the listitem
        return listViewItem;
    }
}
