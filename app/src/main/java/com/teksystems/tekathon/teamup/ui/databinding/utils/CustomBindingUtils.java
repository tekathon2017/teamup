package com.teksystems.tekathon.teamup.ui.databinding.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teksystems.tekathon.teamup.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mayank Tiwari on 19/09/16.
 */

public class CustomBindingUtils {

    private static final String TAG = "CustomBindingUtils";

    public interface StringRule {
        public boolean validate(Editable s);
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return color != 0 ? new ColorDrawable(color) : null;
    }

    @BindingAdapter("android:watcher")
    public static void bindTextWatcher(EditText pEditText, TextWatcher pTextWatcher) {
        pEditText.addTextChangedListener(pTextWatcher);
    }

    @BindingAdapter(value = {"email:rule", "email:errorMsg"}, requireAll = true)
    public static void bindTextChange(final EditText pEditText, final StringRule pStringRule, final String msg) {
        pEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!pStringRule.validate(s)) {
                    pEditText.setError(msg);
                }
            }
        });
    }

    @BindingAdapter(value = {"bind:imageUrl", "bind:imageFillColor"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, int imageFillColor) {
        ColorDrawable colorDrawable = null;
        if (imageFillColor == 0) {
            imageFillColor = R.color.lightGray;
        } else {
            colorDrawable = convertColorToDrawable(imageFillColor);
        }

//        ColorDrawable colorDrawable;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            colorDrawable = new ColorDrawable(imageView.getResources().getColor(imageFillColor, imageView.getContext().getTheme()));
//        } else {
//            colorDrawable = new ColorDrawable(imageView.getResources().getColor(imageFillColor));
//        }

        if (colorDrawable != null) {
            Glide.with(imageView.getContext()).load(url)
//                    .centerCrop()
                    .placeholder(colorDrawable)
//                    .crossFade()
                    .dontAnimate()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext()).load(url)
//                    .centerCrop()
                    .placeholder(imageFillColor)
//                    .crossFade()
                    .dontAnimate()
                    .into(imageView);
        }
    }

    @BindingAdapter("app:civ_border_color")
    public static void setCircleImageViewBorderColor(CircleImageView imageView, int resourceId) {
        imageView.setBorderColor(resourceId);
    }

    /**
     * Unlike the support library app:srcCompat, this will ONLY work with vectors.
     *
     * @param imageView
     * @param resourceId
     */
    @BindingAdapter("android:src")
    public static void setImage(ImageView imageView, int resourceId) {
        Drawable drawable = VectorDrawableCompat.create(imageView.getResources(), resourceId, imageView.getContext().getTheme());
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter("app:srcCompat")
    public static void bindSrcCompat(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter("bind:colorTint")
    public static void setColorTint(ImageView view, int color) {
        DrawableCompat.setTint(view.getDrawable(), color);
    }

    /**
     * Unlike the support library app:srcCompat, this will ONLY work with vectors.
     *
     * @param textView
     * @param resourceId
     */
    @BindingAdapter("android:drawableLeft")
    public static void setDrawableLeft(TextView textView, int resourceId) {
        Drawable drawable = VectorDrawableCompat.create(textView.getResources(), resourceId, textView.getContext().getTheme());
        Drawable[] drawables = textView.getCompoundDrawables();
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable,
                drawables[1], drawables[2], drawables[3]);
    }

    /**
     * Unlike the support library app:srcCompat, this will ONLY work with vectors.
     *
     * @param textView
     * @param drawable
     */
    @BindingAdapter("android:drawableRight")
    public static void setDrawableRight(TextInputEditText textView, Drawable drawable) {
//        Log.d(TAG, "setDrawableRight() called with: textView = [" + textView + "], resourceId = [" + resourceId + "]");
//        Drawable drawable = VectorDrawableCompat.create(textView.getResources(), resourceId, textView.getContext().getTheme());
        Drawable[] drawables = textView.getCompoundDrawables();
        textView.setCompoundDrawablesWithIntrinsicBounds(drawables[1], drawables[2], drawable, drawables[3]);
    }
}
