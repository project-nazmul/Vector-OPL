package com.opl.pharmavector.activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.opl.pharmavector.R;
import com.squareup.picasso.Picasso;

public class MessageShowActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView titleTextView;
    private TextView timeStampTextView;
    private TextView articleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_show);

        viewInitialization();
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        //receive data from MyFirebaseMessagingService class
        String title = getIntent().getStringExtra("title");
        String timeStamp = getIntent().getStringExtra("timestamp");
        String article = getIntent().getStringExtra("article_data");
        String imageUrl = getIntent().getStringExtra("image");
        //Set data on UI
        titleTextView.setText(title);
        titleTextView.setTypeface(face);
        timeStampTextView.setText(timeStamp);
        timeStampTextView.setTypeface(face);
        articleTextView.setText(article);
        timeStampTextView.setTypeface(face);
        Picasso.get().load(imageUrl).error(R.drawable.default_image).into(imageView);
    }

    private void viewInitialization() {
        imageView = (ImageView) findViewById(R.id.featureGraphics);
        titleTextView = (TextView) findViewById(R.id.header);
        timeStampTextView = (TextView) findViewById(R.id.timeStamp);
        articleTextView = (TextView) findViewById(R.id.article);
    }
}
