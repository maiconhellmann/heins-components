package br.com.forusers.heinscomponents.example;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;

import br.com.forusers.heinscomponents.GlideImageView;
import br.com.forusers.heinscomponents.MaterialsItemRow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GlideImageView glideImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glideImageView = (GlideImageView)findViewById(R.id.glideImageView);

        findViewById(R.id.mainActivity_setUri).setOnClickListener(this);
        findViewById(R.id.mainActivity_clear).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mainActivity_clear){
            onClickClearImage();
        }else if(v.getId() == R.id.mainActivity_setUri){
            onClickSetUri();
        }
    }

    private void onClickSetUri() {
        try {
            glideImageView.setSkypMemoryCache(true);
            glideImageView.setImageUri("http://www.w3schools.com/css/img_fjords.jpg");
        }catch (Exception e){
            showErrorMessage(e);
        }
    }

    private void onClickClearImage() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_github:
                onClickGithub();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onClickGithub() {
        try{
            String url = getString(R.string.github_url);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            showErrorMessage(e);
        }
    }
    private void showErrorMessage(Exception e){
        Log.e(getClass().getSimpleName(), getString(R.string.error), e);

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.error);
        b.setMessage(e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        b.show();
    }
}
