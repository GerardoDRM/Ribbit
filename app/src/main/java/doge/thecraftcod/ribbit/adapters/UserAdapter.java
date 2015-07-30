package doge.thecraftcod.ribbit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import doge.thecraftcod.ribbit.R;
import doge.thecraftcod.ribbit.utils.MD5Util;

/**
 * Created by gerardo on 27/07/15.
 */
public class UserAdapter extends ArrayAdapter<ParseUser>{

    private Context mContext;
    private List<ParseUser> mUsers;

    public UserAdapter(Context context, List<ParseUser> users) {
        super(context, R.layout.message_item, users);
        this.mContext = context;
        this.mUsers = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, null);
            holder = new ViewHolder();
            holder.userImage = (ImageView) convertView.findViewById(R.id.userImage);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabelGrid);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();

        }

        ParseUser user = mUsers.get(position);

        // we use gravatar in order to get a photo from email
        String email = user.getEmail();

        if (email.equals("")) {
            holder.userImage.setImageResource(R.mipmap.avatar_empty);
        }
        else {
            String hash = MD5Util.md5Hex(email);
            String gravatarURL = "http://www.gravatar.com/avatar/" + hash +
                    "?s=204&d=404";

            Picasso.with(mContext)
                    .load(gravatarURL)
                    .placeholder(R.mipmap.avatar_empty)
                    .into(holder.userImage);
        }

        /*if (user.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
            holder.iconImageView.setImageResource(R.mipmap.ic_picture);
        }
        else {
            holder.iconImageView.setImageResource(R.mipmap.ic_video);

        }*/
        holder.nameLabel.setText(user.getUsername());

        return convertView;

    }

    private static class ViewHolder {
        ImageView userImage;
        TextView nameLabel;
    }

    public void refill(List<ParseUser> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }
}
