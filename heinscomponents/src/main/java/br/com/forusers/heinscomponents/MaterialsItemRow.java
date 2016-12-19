package br.com.forusers.heinscomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MaterialsItemRow extends RelativeLayout {

    private int defStyleAttr;
    private boolean editable;
    /**
     * Referência aos componentes do layout definidos no xml do custom component
     */
    private ImageView mRightImage ;
    private TextView mTvHint;
    private TextView mTvText;
    private AttributeSet attributeSet;
    private RelativeLayout mLayout;

    private String mText;
    private String mHint;

    private boolean showSeparator;
    private View mSeparator;
    private ImageView mLeftImage;
    private boolean hasMarginRight;
    private int margin72;

    public MaterialsItemRow(Context context) {
        super(context);
        try {
            init();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e);
        }
    }

    public MaterialsItemRow(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        try {
            this.defStyleAttr = defStyleAttr;
            this.attributeSet = attributeSet;
            init();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e);
        }
    }

    public MaterialsItemRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        try {
            this.attributeSet = attributeSet;
            init();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }
    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    public void setText(String text){
        this.mText = text;
        mTvText.setText(mText);
    }
    public String getText(){
        return mText;
    }


    public void setHint(String hint){
        mHint = hint;
        mTvHint.setText(hint);
    }

    public void setEditable(boolean editable){
        try {
            this.editable = editable;
            mRightImage.setVisibility(editable ? VISIBLE : INVISIBLE);

            if (hasMarginRight) {
                ((LayoutParams) mRightImage.getLayoutParams()).rightMargin = margin72;
            }
            invalidate();
        }catch (Exception e){
            Log.e(getClass().getName(), "Erro ao criar LabelMS", e);
        }
    }

    private void init(){
        try {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup view;
            try {
                view = (ViewGroup) inflater.inflate(R.layout.materials_item_row, this, true);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Erro ao inflar o layout. Provavelmente o contexto enviado não é o do componente pai. Utilize parente.getContext().", e);
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(null);
            }

            TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.MaterialsItemRow, defStyleAttr, 0);


            //Parâmetros
            mText = a.getString(R.styleable.MaterialsItemRow_text);
            mHint = a.getString(R.styleable.MaterialsItemRow_hint);
            editable = a.getBoolean(R.styleable.MaterialsItemRow_editable, true);
            showSeparator = a.getBoolean(R.styleable.MaterialsItemRow_showSeparator, true);
            int rightImgResource = a.getResourceId(R.styleable.MaterialsItemRow_rightImage, editable ? R.drawable.ic_edit : -1);
            int leftImgResource = a.getResourceId(R.styleable.MaterialsItemRow_leftImage, -1);
            hasMarginRight = a.getBoolean(R.styleable.MaterialsItemRow_marginRight, false);
            boolean hasMarginLeft = a.getBoolean(R.styleable.MaterialsItemRow_marginLeft, false);

            //Styles
            int textColor = a.getResourceId(R.styleable.MaterialsItemRow_textColorMS, R.color.primaryText);
            int textColorHint = a.getResourceId(R.styleable.MaterialsItemRow_textColorHintMS, R.color.secondaryText);

            a.recycle();

            mRightImage = (ImageView) view.findViewById(R.id.MaterialsItemRow_rightImage);
            mLeftImage = (ImageView) view.findViewById(R.id.MaterialsItemRow_leftImage);
            mTvHint = (TextView) view.findViewById(R.id.MaterialsItemRow_hint);
            mTvText = (TextView) view.findViewById(R.id.MaterialsItemRow_value);
            mLayout = (RelativeLayout) view.findViewById(R.id.MaterialsItemRow_layout);
            mSeparator = view.findViewById(R.id.MaterialsItemRow_separator);
            View leftLayout = view.findViewById(R.id.MaterialsItemRow_layoutLeftImage);

            mTvText.setText(mText);
            mTvHint.setText(mHint);

            mTvHint.setTextColor(ContextCompat.getColor(getContext(), textColorHint));
            mTvText.setTextColor(ContextCompat.getColor(getContext(), textColor));

            mSeparator.setVisibility(showSeparator ? VISIBLE : INVISIBLE);

            if (rightImgResource != -1) {
                mRightImage.setImageResource(rightImgResource);
                mRightImage.setAlpha(0.7f);
            }

            margin72 = (int) getResources().getDimension(R.dimen.itemRow_height);

//        setPadding(leftPadding, 0 , rightPadding, 0);

            if (leftImgResource != -1) {
                leftLayout.setVisibility(View.VISIBLE);
                mLeftImage.setImageResource(leftImgResource);

            } else {
                leftLayout.setVisibility(View.GONE);
                if (hasMarginLeft) {
                    ((LayoutParams) mTvText.getLayoutParams()).leftMargin = margin72;
                }
            }

            setEditable(editable);
        }catch (Exception e){
            Log.e(getClass().getName(), "Erro ao criar LabelMS", e);
        }
    }

    public ImageView getLeftImage() {
        return mLeftImage;
    }

    public ImageView getRightImage() {
        return mRightImage;
    }

    public String getHint() {
        return mHint;
    }
}
