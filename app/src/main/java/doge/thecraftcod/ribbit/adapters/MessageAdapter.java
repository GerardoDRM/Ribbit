package doge.thecraftcod.ribbit.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

import doge.thecraftcod.ribbit.R;
import doge.thecraftcod.ribbit.utils.ParseConstants;

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
            holder.timeLabel = (TextView) convertView.findViewById(R.id.timeLabel);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();

        }

        ParseObject message = mList.get(position);

        Date createdAt = message.getCreatedAt();
        long now = new Date().getTime();
        String convertedDate = DateUtils.getRelativeTimeSpanString(
                createdAt.getTime(),
                now,
                DateUtils.DAY_IN_MILLIS).toString();

        holder.timeLabel.setText(convertedDate);


        if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
            holder.iconImageView.setImageResource(R.mipmap.ic_picture);
        }
        else {
            holder.iconImageView.setImageResource(R.mipmap.ic_video);

        }
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));

        return convertView;

    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
        TextView timeLabel;
    }

    public void refill(List<ParseObject> messages) {
        mList.clear();
        mList.addAll(messages);
        notifyDataSetChanged();
    }
}
