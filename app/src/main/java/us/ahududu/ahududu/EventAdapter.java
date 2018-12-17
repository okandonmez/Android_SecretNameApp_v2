package us.ahududu.ahududu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Event> mKisiListesi;

    public EventAdapter(Activity activity, List<Event> kisiler) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = kisiler;
    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public Event getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.flowcard, null);

        TextView txtPrice = satirView.findViewById(R.id.txtPrice);
        TextView txtCategory = satirView.findViewById(R.id.txtCategory);
        TextView txtEventTitle = satirView.findViewById(R.id.txtEventTitle);
        TextView txtTime = satirView.findViewById(R.id.txtTime);

        ImageView imageView = satirView.findViewById(R.id.imgEvent);

        Event kisi = mKisiListesi.get(position);

        txtPrice.setText(kisi.price);
        txtCategory.setText(kisi.category);
        txtEventTitle.setText(kisi.title);
        txtTime.setText(kisi.time);

        return satirView;
    }
}