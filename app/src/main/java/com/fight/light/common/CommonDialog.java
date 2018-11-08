package com.fight.light.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.View;

public class CommonDialog extends Dialog {
    private DialogController mController;
    public CommonDialog(@NonNull Context context) {
        super(context);
        this.mController = new DialogController(context,this);
    }
    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mController = new DialogController(context,this);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mController.setTitle(title);
    }

    /**
     * @see Builder#setCustomTitle(View)
     */
    public void setCustomTitle(View customTitleView) {
        mController.setCustomTitle(customTitleView);
    }

    public void setMessage(CharSequence message) {
        mController.setMessage(message);
    }

    /**
     * Set the view to display in that dialog.
     */
    public void setView(View view) {
        mController.setView(view);
    }
    /**
     * Set resId to 0 if you don't want an icon.
     * @param resId the resourceId of the drawable to use as the icon or 0
     * if you don't want an icon.
     */
    public void setIcon(@DrawableRes int resId) {
        mController.setIcon(resId);
    }

    public void setIcon(Drawable icon) {
        mController.setIcon(icon);
    }
    /**
     * Set a message to be sent when a button is pressed.
     *
     * @param whichButton Which button to set the message for, can be one of
     *            {@link DialogInterface#BUTTON_POSITIVE},
     *            {@link DialogInterface#BUTTON_NEGATIVE}, or
     *            {@link DialogInterface#BUTTON_NEUTRAL}
     * @param text The text to display in positive button.
     * @param msg The {@link Message} to be sent when clicked.
     */
    public void setButton(int whichButton, CharSequence text, Message msg) {
        mController.setButton(whichButton, text, null, msg);
    }

    /**
     * Set a listener to be invoked when the positive button of the dialog is pressed.
     *
     * @param whichButton Which button to set the listener on, can be one of
     *            {@link DialogInterface#BUTTON_POSITIVE},
     *            {@link DialogInterface#BUTTON_NEGATIVE}, or
     *            {@link DialogInterface#BUTTON_NEUTRAL}
     * @param text The text to display in positive button.
     * @param listener The {@link OnClickListener} to use.
     */
    public void setButton(int whichButton, CharSequence text, OnClickListener listener) {
        mController.setButton(whichButton, text, listener, null);
    }
    /**
     * Set an icon as supplied by a theme attribute. e.g. android.R.attr.alertDialogIcon
     *
     * @param attrId ID of a theme attribute that points to a drawable resource.
     */
    public void setIconAttribute(@AttrRes int attrId) {
        TypedValue out = new TypedValue();
        getContext().getTheme().resolveAttribute(attrId, out, true);
        mController.setIcon(out.resourceId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            mController.installContent();
    }

    public static class Builder {
        private final DialogController.AlertParams P;
        private int resStyle;
        public Builder(Context context) {
            P = new DialogController.AlertParams(context);
        }

        public Context getContext() {
            return P.mContext;
        }
        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }
        /**
         * Set the title using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@IdRes int resId, @StringRes int titleId) {
            P.titleRes = resId;
            P.mTitle = P.mContext.getText(titleId);
            return this;
        }
        /**
         * Set the title displayed in the {@link Dialog}.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@IdRes int resId, CharSequence title) {
            P.titleRes = resId;
            P.mTitle = title;
            return this;
        }

        /**
         * Set the title using the custom view {@code customTitleView}.
         * <p>
         * The methods {@link #setTitle(int)} and {@link #setIcon(int)} should
         * be sufficient for most titles, but this is provided if the title
         * needs more customization. Using this will replace the title and icon
         * set via the other methods.
         * <p>
         * <strong>Note:</strong> To ensure consistent styling, the custom view
         * should be inflated or constructed using the alert dialog's themed
         * context obtained via {@link #getContext()}.
         *
         * @param customTitleView the custom view to use as the title
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public Builder setCustomTitle(View customTitleView) {
            P.mCustomTitleView = customTitleView;
            return this;
        }

        /**
         * Set the message to display using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(@IdRes int resId, @StringRes int messageId) {
            P.messageRes = resId;
            P.mMessage = P.mContext.getText(messageId);
            return this;
        }

        /**
         * Set the message to display.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(@IdRes int resId, CharSequence message) {
            P.messageRes = resId;
            P.mMessage = message;
            return this;
        }

        /**
         * Set the resource id of the {@link Drawable} to be used in the title.
         * <p>
         * Takes precedence over values set using {@link #setIcon(Drawable)}.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setIcon(@IdRes int resId, @DrawableRes int iconId) {
            P.iconRes = resId;
            P.mIconId = iconId;
            return this;
        }

        /**
         * Set the {@link Drawable} to be used in the title.
         * <p>
         * <strong>Note:</strong> To ensure consistent styling, the drawable
         * should be inflated or constructed using the alert dialog's themed
         * context obtained via {@link #getContext()}.
         *
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public Builder setIcon(@IdRes int resId, Drawable icon) {
            P.iconRes = resId;
            P.mIcon = icon;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         * @param textId The resource id of the text to display in the positive button
         * @param listener The {@link OnClickListener} to use.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButton(@IdRes int resId, @StringRes int textId, final OnClickListener listener) {
            P.positiveRes = resId;
            P.mPositiveButtonText = P.mContext.getText(textId);
            P.mPositiveButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         * @param text The text to display in the positive button
         * @param listener The {@link OnClickListener} to use.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButton(@IdRes int resId, CharSequence text, final OnClickListener listener) {
            P.positiveRes = resId;
            P.mPositiveButtonText = text;
            P.mPositiveButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         * @param textId The resource id of the text to display in the negative button
         * @param listener The {@link OnClickListener} to use.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButton(@IdRes int resId, @StringRes int textId, final OnClickListener listener) {
            P.negativeRes = resId;
            P.mNegativeButtonText = P.mContext.getText(textId);
            P.mNegativeButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         * @param text The text to display in the negative button
         * @param listener The {@link OnClickListener} to use.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButton(@IdRes int resId, CharSequence text, final OnClickListener listener) {
            P.negativeRes = resId;
            P.mNegativeButtonText = text;
            P.mNegativeButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the neutral button of the dialog is pressed.
         * @param textId The resource id of the text to display in the neutral button
         * @param listener The {@link OnClickListener} to use.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNeutralButton(@IdRes int resId, @StringRes int textId, final OnClickListener listener) {
            P.neutralRes = resId;
            P.mNeutralButtonText = P.mContext.getText(textId);
            P.mNeutralButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the neutral button of the dialog is pressed.
         * @param text The text to display in the neutral button
         * @param listener The {@link OnClickListener} to use.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNeutralButton(@IdRes int resId, CharSequence text, final OnClickListener listener) {
            P.neutralRes = resId;
            P.mNeutralButtonText = text;
            P.mNeutralButtonListener = listener;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(OnDismissListener) setOnDismissListener}.</p>
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(OnDismissListener)
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * Set a custom view resource to be the contents of the Dialog. The
         * resource will be inflated, adding all top-level views to the screen.
         *
         * @param layoutResId Resource ID to be inflated.
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public Builder setView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * Sets a custom view to be the contents of the alert dialog.
         * <p>
         * When using a pre-Holo theme, if the supplied view is an instance of
         * a {@link } then the light background will be used.
         * <p>
         * <strong>Note:</strong> To ensure consistent styling, the custom view
         * should be inflated or constructed using the alert dialog's themed
         * context obtained via {@link #getContext()}.
         *
         * @param view the view to use as the contents of the alert dialog
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }


        /**
         * Creates an {@link CommonDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public CommonDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final CommonDialog dialog;
            if (resStyle!= 0){
                dialog = new CommonDialog(P.mContext, resStyle);
            }else {
                dialog = new CommonDialog(P.mContext);
            }
            P.apply(dialog.mController);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Creates an {@link CommonDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     CommonDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public CommonDialog show() {
            final CommonDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
