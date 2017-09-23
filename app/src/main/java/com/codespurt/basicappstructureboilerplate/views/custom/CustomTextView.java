package com.codespurt.basicappstructureboilerplate.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.engine.App;

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.custom);
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + App.fontTextView + App.fontExtension);
            setTypeface(myTypeface);
            typedArray.recycle();
        }
    }

    public void setBold(CustomTextView textView) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        set(textView, spanString);
    }

    public void setItalic(CustomTextView textView) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
        set(textView, spanString);
    }

    public void setUnderline(CustomTextView textView) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        set(textView, spanString);
    }

    public void setBoldItalic(CustomTextView textView) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
        set(textView, spanString);
    }

    public void setBoldUnderline(CustomTextView textView) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        set(textView, spanString);
    }

    public void setBoldItalicUnderline(CustomTextView textView) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        set(textView, spanString);
    }

    public void setTextColor(CustomTextView textView, int color) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new ForegroundColorSpan(color), 0, spanString.length(), 0);
        setColoredText(textView, spanString);
    }

    public void setTextColor(CustomTextView textView, int color, int numberOfCharacters) {
        SpannableString spanString = new SpannableString(textView.getText().toString().trim());
        spanString.setSpan(new ForegroundColorSpan(color), 0, numberOfCharacters, 0);
        setColoredText(textView, spanString);
    }

    private void set(CustomTextView textView, SpannableString text) {
        textView.setText(text);
    }

    private void setColoredText(CustomTextView textView, SpannableString text) {
        textView.setText(text, BufferType.SPANNABLE);
    }
}