package us.ahududu.ahududu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DiscoverAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Discover> mKisiListesi;

    public DiscoverAdapter(Activity activity, List<Discover> kisiler) {
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
    public Discover getItem(int position) {
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
        String url;
        satirView = mInflater.inflate(R.layout.discovercard, null);

        TextView txtEventTitle = satirView.findViewById(R.id.txtTitle);
        TextView txtDetail = satirView.findViewById(R.id.txtDiscoverDetail);
        ImageView imageView = satirView.findViewById(R.id.imgDiscover);

        Discover kisi = mKisiListesi.get(position);

        url = kisi.url;
        Picasso.get().load(url).into(imageView);
        txtEventTitle.setText(kisi.title);

        DesignTools designTools = new DesignTools(kisi.mContext);
        txtEventTitle.setTypeface(designTools.getTypeFace("fonts/metropolis.regular.otf"));

        txtDetail.setText(Html.fromHtml(kisi.detail1 + " " + "<font color=#d00053>" + kisi.detail2 + "</font>" + " indirim"));

        return satirView;
    }
}
