package id.ac.ukdw.ti.yuanlukito.wifidatacollector.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.ac.ukdw.ti.yuanlukito.wifidatacollector.R;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.LocationData;

/**
 * Created by Yuan Lukito on 22-Apr-15.
 */
public class LocationListAdapter extends BaseAdapter {
    private ArrayList<LocationData> locationDatas;
    private Activity activity;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return locationDatas.size();
    }

    @Override
    public LocationData getItem(int position) {
        return locationDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.location_list_item, null);
        LocationData ld = locationDatas.get(position);

        TextView textViewLocationName = (TextView) convertView.findViewById(R.id.textViewLocationName);
        

        return convertView;
    }
}
