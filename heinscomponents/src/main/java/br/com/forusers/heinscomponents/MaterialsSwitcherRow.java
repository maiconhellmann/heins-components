package br.com.forusers.heinscomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO: Add a class header comment!
 */

public class MaterialsSwitcherRow extends FrameLayout {

    private int defStyleAttr;
    private AttributeSet attributeSet;
    private TextView primaryTextView;
    private TextView secondaryTextView;
    private ImageView imageView;
    private ViewGroup rootView;
    private View separatorView;

    public MaterialsSwitcherRow(Context context) {
        super(context);
        try {
            init();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e);
        }
    }

    public MaterialsSwitcherRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            this.attributeSet = attrs;
            init();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e);
        }
    }

    public MaterialsSwitcherRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            this.defStyleAttr = defStyleAttr;
            this.attributeSet = attrs;
            init();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaterialsSwitcherRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        try {
            this.defStyleAttr = defStyleAttr;
            this.attributeSet = attrs;
            init();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e);
        }
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup view;
        try {
            view = (ViewGroup) inflater.inflate(R.layout.materials_switcher_row, this, true);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Erro ao inflar o layout. Provavelmente o contexto enviado não é o do componente pai. Utilize parente.getContext().", e);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(null);
        }

        TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.MaterialsSwitcherRow, defStyleAttr, 0);


        //Parâmetros
        String primaryText = a.getString(R.styleable.MaterialsSwitcherRow_primaryText);
        String secondaryText = a.getString(R.styleable.MaterialsSwitcherRow_secondaryText);
        boolean showSeparator = a.getBoolean(R.styleable.MaterialsSwitcherRow_showSeparator, true);
        int imgResource = a.getResourceId(R.styleable.MaterialsSwitcherRow_leftImage, -1);

        primaryTextView = (TextView)view.findViewById(R.id.materialsSwitcherRow_textPrimary);
        secondaryTextView = (TextView)view.findViewById(R.id.materialsSwitcherRow_textSecondary);
        imageView =(ImageView) view.findViewById(R.id.materialsSwitcherRow_imageview);
        separatorView = view.findViewById(R.id.MaterialsSwitcherRow_separator);

        this.rootView = view;

        //Inicializa os valores dos componentes
        setPrimaryText(primaryText);
        setSecondaryText(secondaryText);
        setImage(imgResource);

        if(!showSeparator){
            separatorView.setVisibility(GONE);
        }

        a.recycle();
    }

    /**
     * Atribui evento onclick ao componente
     * @param listener
     */
    public void setListener(OnClickListener listener){
        rootView.setOnClickListener(listener);
    }

    /**
     * Alias para alterar o text primario
     * @param text
     */
    public void setPrimaryText(String text){
        getPrimaryTextView().setText(text);
    }

    /**
     * Alias para alterar o texto secundario
     * @param text
     */
    public void setSecondaryText(String text){
        getSecondaryTextView().setText(text);
    }

    /**
     * Alias para alterar imagem
     * @param imageResource
     */
    public void setImage(@DrawableRes int imageResource){
        getImageView().setImageResource(imageResource);
    }

     public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getSecondaryTextView() {
        return secondaryTextView;
    }

    public void setSecondaryTextView(TextView secondaryTextView) {
        this.secondaryTextView = secondaryTextView;
    }

    public TextView getPrimaryTextView() {
        return primaryTextView;
    }

    public void setPrimaryTextView(TextView primaryTextView) {
        this.primaryTextView = primaryTextView;
    }
}
