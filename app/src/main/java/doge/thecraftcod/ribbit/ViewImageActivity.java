package doge.thecraftcod.ribbit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ViewImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Uri imageUri = getIntent().getData();

        // Using picasso to get an image from web
        // ,, Pretty cool
        Picasso.with(this).load(imageUri.toString()).into(image);

    }

}
