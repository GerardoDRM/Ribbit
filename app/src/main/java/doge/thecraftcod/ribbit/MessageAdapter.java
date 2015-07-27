package doge.thecraftcod.ribbit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by gerardo on 27/07/15.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject>{

    private Context mContext;
    private List<ParseObject> mList;

    public MessageAdapter(Context context, List<ParseObject> list) {
        super(context, R.layout.message_item, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.meesage_icon);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.message_sender);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();

        }

        ParseObject message = mList.get(position);
        Log.d("Error", message.getString(ParseConstants.KEY_FILE_TYPE));
        if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
            holder.iconImageView.setImageResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        }
        else {
            holder.iconImageView.setImageResource(R.drawable.abc_ic_voice_search_api_mtrl_alpha);

        }
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));





        return convertView;

    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
    }
}
