package br.com.forusers.heinscomponents.example;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;

import br.com.forusers.heinscomponents.GlideImageView;
import br.com.forusers.heinscomponents.MaterialsItemRow;

public class MainActivity extends AppCompatActivity {

    private GlideImageView glideImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glideImageView = (GlideImageView)findViewById(R.id.glideImageView);
    }

    public void setUriGlideImageView(View view) {
        glideImageView.setSkypMemoryCache(true);
        glideImageView.setImageUri("http://www.w3schools.com/css/img_fjords.jpg");
    }

    public void clearGlideImageView(View view) {

        new ClearCacheAsyncTask(glideImageView.getImgView().getContext()).execute();

        glideImageView.setPlaceHolderImage();
    }

    private class ClearCacheAsyncTask extends AsyncTask<Void,Void,Void>{

        private final Context context;

        ClearCacheAsyncTask(Context context) {
            this.context=context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Glide.get(context).clearDiskCache();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Glide.get(glideImageView.getImgView().getContext()).clearMemory();
        }
    }
}
