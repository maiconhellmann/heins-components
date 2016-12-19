package br.com.forusers.heinscomponents;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.signature.StringSignature;

public class GlideImageView extends FrameLayout {

    private int defStyleAttr;
    private AttributeSet attributeSet;
    private ImageView.ScaleType mLoadedScaleType = ImageView.ScaleType.CENTER_INSIDE;
    private ImageView.ScaleType mPlaceHolderScaleType = ImageView.ScaleType.CENTER_INSIDE;
    private boolean mAdjustViewBoundsOnReady = false;

    private int containerId=0;
    private RotateAnimation animation;
    private ImageView mImageView;
    private View mProgressBar;

    private int placeHolder;
    private boolean skypMemoryCache=false;

    public GlideImageView(Context context) {
        super(context);
        init();
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attributeSet = attrs;
        init();
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attributeSet = attrs;
        this.defStyleAttr = defStyleAttr;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attributeSet = attrs;

        this.defStyleAttr = defStyleAttr;
        init();
    }

    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }


    private void init() {
        try{
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.glide_image_view, this, true);
            mImageView = (ImageView) view.findViewById(R.id.glideImageView_imageView);
            mProgressBar = view.findViewById(R.id.glideImageView_progressBar);

            placeHolder = R.drawable.ic_camera;

            setPlaceHolderImage(mImageView);
        }catch (Exception e){
            Log.e("ImageViewURL", "Erro ao iniciar componente. ", e);
        }
    }

    public void setPlaceHolderImage(ImageView imgView){
        stopLoading();
        imgView.setImageResource(placeHolder);
        imgView.setScaleType(mPlaceHolderScaleType);
    }

    public void hidePlaceHolder(){
        getImgView().setImageBitmap(null);
    }


    public void setPlaceHolderImage(){
        setPlaceHolderImage(mImageView);
    }

    public void setLoading(){
        hidePlaceHolder();
        setLoading(mImageView);
    }

    public void setLoading(ImageView imgView){
        mProgressBar.setVisibility(VISIBLE);
    }

    public void stopLoading(){
        mProgressBar.setVisibility(GONE);
    }

    public void setImageUri(@NonNull String uri){
        try {
            Glide.clear(mImageView);

            //Se não possuir uri então utiliza o placeholder
            if (uri.isEmpty()) {
                setPlaceHolderImage(mImageView);
            } else {

                //verifica se existe em disco
                Glide.with(mImageView.getContext())
                        .load(uri)
                        .error(placeHolder)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(skypMemoryCache)
                        .crossFade()
                        .into(new ViewTarget<ImageView, GlideDrawable>(mImageView) {

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                                try {
                                    stopLoading();
                                    if(resource != null){
                                        view.setAdjustViewBounds(mAdjustViewBoundsOnReady);
                                        this.view.setImageDrawable(resource);
                                        view.setScaleType(mLoadedScaleType);
                                    }else{
                                        setPlaceHolderImage();
                                    }
                                } catch (Exception e) {
                                    Log.e(getClass().getSimpleName(), "Erro ao salvar imagem",e);
                                }
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                setLoading();
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                Log.w(getClass().getSimpleName(), e);
                                stopLoading();
                                ImageView myView = this.view;
                                myView.setImageDrawable(errorDrawable);
                            }

                        });
            }
        }catch (Exception e){
            Log.e("mrplibrary-android", "Erro ao baixar imagem informada", e);
        }
    }

    public void onClickImageView(ImageView iv, final Bitmap image){
        //adicionar evento somente quando houver um container pai
        if(containerId > 0) {
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageZoom(v, image);
                }
            });
        }
    }

    /**
     * Abre um layout com zoom da imagem. Abre somente quando informado um "containerId" que seja um FrameLayout.
     * @param v
     * @param image
     */
    public void imageZoom(View v, Bitmap image){
        if(containerId > 0) {
            final ViewGroup rootLayout = (ViewGroup) v.getRootView().findViewById(containerId);

            if (rootLayout != null) {
                final ImageView view = new ImageView(rootLayout.getContext());
                view.setImageBitmap(image);
                view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.black_transparent, getContext().getTheme()));

                view.setPadding(50, 50, 50, 50);
                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        rootLayout.removeView(view);
                    }
                });
                rootLayout.addView(view);
            }
        }
    }



    /**
     * Atribui um container pai para exibir a imagem em Zoom ao clicar.
     * @param containerId
     */
    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public ImageView getImgView() {
        return mImageView;
    }

    /**
     * Calcula o inSampleSize, que recimencioa a imagem conforme o tamanho do container atual.
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public void setPlaceHolderScaleType(ImageView.ScaleType mPlaceHolderScaleType) {
        this.mPlaceHolderScaleType = mPlaceHolderScaleType;
    }

    public void setLoadedScaleType(ImageView.ScaleType mLoadedScaleType) {
        this.mLoadedScaleType = mLoadedScaleType;
    }

    public boolean isAdjustViewBoundsOnReady() {
        return mAdjustViewBoundsOnReady;
    }

    public void setAdjustViewBoundsOnReady(boolean mAdjustViewBoundsOnReady) {
        this.mAdjustViewBoundsOnReady = mAdjustViewBoundsOnReady;
    }

    public void setPlaceHolder(@DrawableRes int placeHolder) {
        this.placeHolder = placeHolder;
        setPlaceHolderImage();
    }

    public void setSkypMemoryCache(boolean skypMemoryCache) {
        this.skypMemoryCache = skypMemoryCache;
    }
}
