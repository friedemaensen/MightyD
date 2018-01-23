package test.com.example.rich1016.musterloesung1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vofr1011 on 23.01.2018.
 */

public class TrackListViewAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<Track> trackList;

    public TrackListViewAdapter (Context context, List<Track> tracks) {
        layoutInflater = LayoutInflater.from(context);
        trackList = tracks;
    }

    @Override
    public int getCount() {
        return trackList.size();
    }

    @Override
    public Object getItem(int i) {
        return trackList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        if (convertView == null) {
            vi = layoutInflater.inflate(R.layout.track_list_item, null);
            TextView textView = (TextView) vi.findViewById(R.id.tvTrackListItem);
            textView.setText(trackList.get(i).getName());
        }
        return vi;
    }

}
