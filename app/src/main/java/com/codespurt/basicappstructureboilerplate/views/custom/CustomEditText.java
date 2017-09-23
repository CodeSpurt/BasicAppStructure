package com.codespurt.basicappstructureboilerplate.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.engine.App;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {

    private Drawable clear = getResources().getDrawable(R.drawable.vector_clear);

    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        clear.setBounds(0, 0, clear.getIntrinsicWidth(), clear.getIntrinsicHeight());
        handleClearButton();

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.custom);
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + App.fontEditText + App.fontExtension);
            setTypeface(myTypeface);
            typedArray.recycle();
        }

        addListeners();
    }

    private void addListeners() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CustomEditText customEditText = CustomEditText.this;
                if (customEditText.getCompoundDrawables()[2] == null)
                    return false;
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > customEditText.getWidth() - customEditText.getPaddingRight() - clear.getIntrinsicWidth()) {
                    customEditText.setText("");
                    CustomEditText.this.handleClearButton();
                }
                return false;
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CustomEditText.this.handleClearButton();
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
    }

    private void handleClearButton() {
        if (this.getText().toString().equals("")) {
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
        } else {
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], clear, this.getCompoundDrawables()[3]);
        }
    }
}