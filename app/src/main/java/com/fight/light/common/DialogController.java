package com.fight.light.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class DialogController {

 private Context context;
 private Dialog dialog;
    private CharSequence mTitle;
    private CharSequence mMessage;
    ListView mListView;
    private View mView;

    private int mViewLayoutResId;

    TextView mButtonPositive;
    private CharSequence mButtonPositiveText;
    Message mButtonPositiveMessage;

    TextView mButtonNegative;
    private CharSequence mButtonNegativeText;
    Message mButtonNegativeMessage;

    TextView mButtonNeutral;
    private CharSequence mButtonNeutralText;
    Message mButtonNeutralMessage;
    private int mIconId = 0;
    private Drawable mIcon;
    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mMessageView;
    private View mCustomTitleView;
    Handler mHandler;
    private int titleRes;
    private int messageRes;
    private int positiveRes;
    private int negativeRes;
    private int neutralRes;
    private int iconRes; //标题图片
    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public void setMessageRes(int messageRes) {
        this.messageRes = messageRes;
    }

    public void setPositiveRes(int positiveRes) {
        this.positiveRes = positiveRes;
    }

    public void setNegativeRes(int negativeRes) {
        this.negativeRes = negativeRes;
    }

    public void setNeutralRes(int neutralRes) {
        this.neutralRes = neutralRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }


    private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Message m;
            if (v == mButtonPositive && mButtonPositiveMessage != null) {
                m = Message.obtain(mButtonPositiveMessage);
            } else if (v == mButtonNegative && mButtonNegativeMessage != null) {
                m = Message.obtain(mButtonNegativeMessage);
            } else if (v == mButtonNeutral && mButtonNeutralMessage != null) {
                m = Message.obtain(mButtonNeutralMessage);
            } else {
                m = null;
            }

            if (m != null) {
                m.sendToTarget();
            }
            // Post a message so we dismiss after the above handlers are executed
            mHandler.obtainMessage(DialogController.ButtonHandler.MSG_DISMISS_DIALOG, dialog)
                    .sendToTarget();
        }
    };

    private static final class ButtonHandler extends Handler {
        // Button clicks have Message.what as the BUTTON{1,2,3} constant
        private static final int MSG_DISMISS_DIALOG = 1;

        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialog) {
            mDialog = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case DialogInterface.BUTTON_POSITIVE:
                case DialogInterface.BUTTON_NEGATIVE:
                case DialogInterface.BUTTON_NEUTRAL:
                    ((DialogInterface.OnClickListener) msg.obj).onClick(mDialog.get(), msg.what);
                    break;

                case MSG_DISMISS_DIALOG:
                    ((DialogInterface) msg.obj).dismiss();
            }
        }
    }

    public DialogController(Context context, Dialog dialog){
     this.context = context;
     this.dialog = dialog;
        mHandler = new DialogController.ButtonHandler(dialog);
}
    public void installContent() {
        if (mView!= null){
            dialog.setContentView(mView);
        }else if (mViewLayoutResId != 0){
            mView = LayoutInflater.from(context).inflate(mViewLayoutResId,null);
            dialog.setContentView(mView);
        }
        setupView();
        setupButtons(mView);
    }
    private void setupView() {

        if (mCustomTitleView!= null) {}else{
            boolean hasTextTitle = !TextUtils.isEmpty(mTitle);
            if (hasTextTitle) {
                mTitleView = mView.findViewById(titleRes);

                mTitleView.setText(mTitle);
                if (iconRes!= 0) {
                    mIconView = mView.findViewById(iconRes);
                    if (mIconId != 0) {
                        mIconView.setImageResource(mIconId);
                    } else if (mIcon != null) {
                        mIconView.setImageDrawable(mIcon);
                    } else {
                        // Apply the padding from the icon to ensure the title is
                        // aligned correctly.
                        mTitleView.setPadding(mIconView.getPaddingLeft(),
                                mIconView.getPaddingTop(),
                                mIconView.getPaddingRight(),
                                mIconView.getPaddingBottom());
                        mIconView.setVisibility(View.GONE);
                    }
                }
            }
        }
        boolean hasTestMessage = !TextUtils.isEmpty(mMessage);
       if (hasTestMessage){
           mMessageView = mView.findViewById(messageRes);
           mMessageView.setText(mMessage);
       }

    }

    private void setupButtons(View buttonPanel) {
        int BIT_BUTTON_POSITIVE = 1;
        int BIT_BUTTON_NEGATIVE = 2;
        int BIT_BUTTON_NEUTRAL = 4;
        int whichButtons = 0;
        if (positiveRes!= 0) {
            mButtonPositive =  buttonPanel.findViewById(positiveRes);
            mButtonPositive.setOnClickListener(mButtonHandler);
            if (TextUtils.isEmpty(mButtonPositiveText)) {
                mButtonPositive.setVisibility(View.GONE);
            } else {
                mButtonPositive.setText(mButtonPositiveText);
                mButtonPositive.setVisibility(View.VISIBLE);
                whichButtons = whichButtons | BIT_BUTTON_POSITIVE;
            }
        }
        if (negativeRes!= 0) {
            mButtonNegative =  buttonPanel.findViewById(negativeRes);
            mButtonNegative.setOnClickListener(mButtonHandler);

            if (TextUtils.isEmpty(mButtonNegativeText)) {
                mButtonNegative.setVisibility(View.GONE);
            } else {
                mButtonNegative.setText(mButtonNegativeText);
                mButtonNegative.setVisibility(View.VISIBLE);

                whichButtons = whichButtons | BIT_BUTTON_NEGATIVE;
            }
        }
            if (neutralRes!= 0) {
                mButtonNeutral =  buttonPanel.findViewById(neutralRes);
                mButtonNeutral.setOnClickListener(mButtonHandler);

                if (TextUtils.isEmpty(mButtonNeutralText)) {
                    mButtonNeutral.setVisibility(View.GONE);
                } else {
                    mButtonNeutral.setText(mButtonNeutralText);
                    mButtonNeutral.setVisibility(View.VISIBLE);

                    whichButtons = whichButtons | BIT_BUTTON_NEUTRAL;
                }
            }
        final boolean hasButtons = whichButtons != 0;
        if (!hasButtons) {
            buttonPanel.setVisibility(View.GONE);
        }
    }
    private void centerButton(Button button) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.weight = 0.5f;
        button.setLayoutParams(params);
    }
    public void setTitle(CharSequence title) {
        mTitle = title;
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
    }

    /**
     * @see AlertDialog.Builder#setCustomTitle(View)
     */
    public void setCustomTitle(View customTitleView) {
        mCustomTitleView = customTitleView;
    }

    public void setMessage(CharSequence message) {
        mMessage = message;
        if (mMessageView != null) {
            mMessageView.setText(message);
        }
    }

    /**
     * Set the view resource to display in the dialog.
     */
    public void setView(int layoutResId) {
        mView = null;
        mViewLayoutResId = layoutResId;
    }

    /**
     * Set the view to display in the dialog.
     */
    public void setView(View view) {
        mView = view;
        mViewLayoutResId = 0;
    }


    /**
     * Sets a click listener or a message to be sent when the button is clicked.
     * You only need to pass one of {@code listener} or {@code msg}.
     *
     * @param whichButton Which button, can be one of
     *                    {@link DialogInterface#BUTTON_POSITIVE},
     *                    {@link DialogInterface#BUTTON_NEGATIVE}, or
     *                    {@link DialogInterface#BUTTON_NEUTRAL}
     * @param text        The text to display in positive button.
     * @param listener    The {@link DialogInterface.OnClickListener} to use.
     * @param msg         The {@link Message} to be sent when clicked.
     */
    public void setButton(int whichButton, CharSequence text,
                          DialogInterface.OnClickListener listener, Message msg) {

        if (msg == null && listener != null) {
            msg = mHandler.obtainMessage(whichButton, listener);
        }

        switch (whichButton) {

            case DialogInterface.BUTTON_POSITIVE:
                mButtonPositiveText = text;
                mButtonPositiveMessage = msg;
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                mButtonNegativeText = text;
                mButtonNegativeMessage = msg;
                break;

            case DialogInterface.BUTTON_NEUTRAL:
                mButtonNeutralText = text;
                mButtonNeutralMessage = msg;
                break;

            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    /**
     * Specifies the icon to display next to the alert title.
     *
     * @param resId the resource identifier of the drawable to use as the icon,
     *              or 0 for no icon
     */
    public void setIcon(int resId) {
        mIcon = null;
        mIconId = resId;
        if (mIconView != null) {
            if (resId != 0) {
                mIconView.setVisibility(View.VISIBLE);
                mIconView.setImageResource(mIconId);
            } else {
                mIconView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Specifies the icon to display next to the alert title.
     *
     * @param icon the drawable to use as the icon or null for no icon
     */
    public void setIcon(Drawable icon) {
        mIcon = icon;
        mIconId = 0;
        if (mIconView != null) {
            if (icon != null) {
                mIconView.setVisibility(View.VISIBLE);
                mIconView.setImageDrawable(icon);
            } else {
                mIconView.setVisibility(View.GONE);
            }
        }
    }
    /**
     * @param attrId the attributeId of the theme-specific drawable
     *               to resolve the resourceId for.
     *
     * @return resId the resourceId of the theme-specific drawable
     */
    public int getIconAttributeResId(int attrId) {
        TypedValue out = new TypedValue();
        context.getTheme().resolveAttribute(attrId, out, true);
        return out.resourceId;
    }
    public static class AlertParams {
        public final Context mContext;
        public int mIconId = 0;
        public Drawable mIcon;
        public int mIconAttrId = 0;
        public CharSequence mTitle;
        public View mCustomTitleView;
        public CharSequence mMessage;
        public CharSequence mPositiveButtonText;
        public Drawable mPositiveButtonIcon;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mNegativeButtonText;
        public Drawable mNegativeButtonIcon;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNeutralButtonText;
        public Drawable mNeutralButtonIcon;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public boolean mCancelable;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public int mViewLayoutResId;
        public View mView;
        public int titleRes;
        public int messageRes;
        public int positiveRes;
        public int negativeRes;
        public int neutralRes;
        public int iconRes; //标题图片

        public AlertParams(Context context) {
            this.mContext = context;
            this.mCancelable = true;
        }
        public void apply(DialogController dialog) {
            if (this.mCustomTitleView != null) {
                dialog.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    dialog.setTitleRes(this.titleRes);
                    dialog.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    dialog.setTitleRes(this.iconRes);
                    dialog.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    dialog.setTitleRes(this.iconRes);
                    dialog.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    dialog.setTitleRes(this.iconRes);
                    dialog.setIcon(dialog.getIconAttributeResId(this.mIconAttrId));
                }
            }

            if (this.mMessage != null) {
                dialog.setMessageRes(this.messageRes);
                dialog.setMessage(this.mMessage);
            }

            if (mPositiveButtonText != null) {
                dialog.setPositiveRes(this.positiveRes);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, mPositiveButtonText,
                        mPositiveButtonListener, null);
            }
            if (mNegativeButtonText != null) {
                dialog.setNegativeRes(this.negativeRes);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, mNegativeButtonText,
                        mNegativeButtonListener, null);
            }
            if (mNeutralButtonText != null) {
                dialog.setNeutralRes(this.neutralRes);
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, mNeutralButtonText,
                        mNeutralButtonListener, null);
            }


            if (this.mView != null) {
                dialog.setView(this.mView);
            } else if (this.mViewLayoutResId != 0) {
                dialog.setView(this.mViewLayoutResId);
            }else{
                throw new NullPointerException("dialog view is null");
            }
               /*
            dialog.setCancelable(mCancelable);
            dialog.setOnCancelListener(mOnCancelListener);
            if (mOnKeyListener != null) {
                dialog.setOnKeyListener(mOnKeyListener);
            }
            */
        }
    }
}
