package shun_min_chang.hyena.dialog.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import shun_min_chang.hyena.dialog.R;
import shun_min_chang.hyena.dialog.utils.Utils;

import static android.support.constraint.ConstraintSet.*;

/**
 * Created by shunminchang on 2018/1/18.
 */

public class Dialog extends DialogFragment implements View.OnClickListener, TextWatcher {
    private static final String TAG = "Dialog";
    private String content, positiveText, negativeText, editHint, editWarningText;
    private int backgroundColor, contentColor;
    private int positiveTextColor, positiveBackgroundColor;
    private int negativeTextColor, negativeBackgroundColor;
    private int progressTextColor;
    private int warningResId, editWarningResId, progressStyle;
    private int dialogWidth;
    private boolean cancel, warning, edit, progress;
    private OnClickListener positiveClickListener;
    private OnClickListener negativeClickListener;
    private OnEditTextChangListener editTextChangListener;
    private int MARGIN;

    // Bundle Key
    public static final String CANCEL = "CANCEL";
    public static final String WARNING = "WARNING";
    public static final String EDIT = "EDIT";
    public static final String EDIT_HINT = "EDIT_HINT";
    public static final String EDIT_WARNING_TEXT = "EDIT_WARNING_TEXT";
    public static final String EDIT_WARNING_ICON = "EDIT_WARNING_ICON";
    public static final String PROGRESS = "PROGRESS";
    public static final String PROGRESS_BAR_STYLE = "PROGRESS_BAR_STYLE";
    public static final String PROGRESS_TEXT_COLOR = "PROGRESS_TEXT_COLOR";
    public static final String WARNING_DRAWABLE_RES_ID = "WARNING_DRAWABLE_RES_ID";
    public static final String DIALOG_BACKGROUND_COLOR = "DIALOG_BACKGROUND_COLOR";
    public static final String CONTENT = "CONTENT";
    public static final String CONTENT_TEXT_COLOR = "CONTENT_TEXT_COLOR";
    public static final String POSITIVE_TEXT = "POSITIVE_TEXT";
    public static final String POSITIVE_TEXT_COLOR = "POSITIVE_TEXT_COLOR";
    public static final String POSITIVE_BACKGROUND_COLOR = "POSITIVE_BACKGROUND_COLOR";
    public static final String NEGATIVE_TEXT = "NEGATIVE_TEXT";
    public static final String NEGATIVE_TEXT_COLOR = "NEGATIVE_TEXT_COLOR";
    public static final String NEGATIVE_BACKGROUND_COLOR = "NEGATIVE_BACKGROUND_COLOR";
    private static final String DIALOG_WIDTH = "DIALOG_WIDTH";

    // UI ID
    private static final int ROOT_VIEW = 326;
    private static final int IMG_WARNING = 711;
    private static final int TV_CONTENT = 674;
    private static final int ET_EDIT = 174;
    private static final int CL_EDIT_WARNING_ROOT = 822;
    private static final int IMG_EDIT_WARNING_1 = 113;
    private static final int TV_EDIT_WARNING_1 = 581;
    private static final int IMG_EDIT_WARNING_2 = 55;
    private static final int TV_EDIT_WARNING_2 = 321;
    private static final int CL_PROGRESS_ROOT = 508;
    private static final int PB_PROGRESS = 404;
    private static final int TV_PROGRESS = 37;
    private static final int BTN_POSITIVE = 384;
    private static final int BTN_NEGATIVE = 127;
    private OnCancelListener cancelListener;
    private ProgressBar mProgressBar;
    private TextView mTVProgress;
    private EditText mETEdit;
    private ConstraintLayout mCLEditWarning;
    private TextView mTVEditDynamicWarning;
    private ImageView mImgEditDynamicWarning;

    public interface OnClickListener {
        void onClick();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public interface OnEditTextChangListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }

    public Dialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cancel = getArguments().getBoolean(CANCEL, true);
        warning = getArguments().getBoolean(WARNING, false);
        edit = getArguments().getBoolean(EDIT, false);
        editHint = getArguments().getString(EDIT_HINT, null);
        editWarningText = getArguments().getString(EDIT_WARNING_TEXT);
        editWarningResId = getArguments().getInt(EDIT_WARNING_ICON, R.drawable.ic_warning);
        progress = getArguments().getBoolean(PROGRESS, false);
        progressStyle = getArguments().getInt(PROGRESS_BAR_STYLE, 0);
        progressTextColor = getArguments().getInt(PROGRESS_TEXT_COLOR, Color.BLACK);
        warningResId = getArguments().getInt(WARNING_DRAWABLE_RES_ID, R.drawable.ic_warning);
        backgroundColor = getArguments().getInt(DIALOG_BACKGROUND_COLOR, Utils.getDefaultBackgroundColor(getActivity()));
        content = getArguments().getString(CONTENT, null);
        contentColor = getArguments().getInt(CONTENT_TEXT_COLOR, Color.BLACK);
        positiveText = getArguments().getString(POSITIVE_TEXT, null);
        positiveTextColor = getArguments().getInt(POSITIVE_TEXT_COLOR, Color.WHITE);
        positiveBackgroundColor = getArguments().getInt(POSITIVE_BACKGROUND_COLOR, Color.BLACK);
        negativeText = getArguments().getString(NEGATIVE_TEXT, null);
        negativeTextColor = getArguments().getInt(NEGATIVE_TEXT_COLOR, Color.WHITE);
        negativeBackgroundColor = getArguments().getInt(NEGATIVE_BACKGROUND_COLOR, Color.BLACK);
        dialogWidth = getArguments().getInt(DIALOG_WIDTH, 0);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Context mContext = getActivity();
        ConstraintSet mConstraintSet;
        MARGIN = Utils.dpToPx(mContext, 8);
        ConstraintLayout mRootView = new ConstraintLayout(mContext);
        mRootView.setId(ROOT_VIEW);
        mRootView.setBackgroundColor(backgroundColor);
        getDialog().setCanceledOnTouchOutside(cancel);

        if (warning) {
            int marginTop = MARGIN * 2;
            ImageView mImgWarning = new ImageView(mContext);
            mImgWarning.setId(IMG_WARNING);
            mImgWarning.setImageResource(warningResId);
            mRootView.addView(mImgWarning);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(IMG_WARNING, WRAP_CONTENT);
            mConstraintSet.constrainHeight(IMG_WARNING, WRAP_CONTENT);
            mConstraintSet.connect(IMG_WARNING, TOP,
                    ROOT_VIEW, TOP, marginTop);
            mConstraintSet.connect(IMG_WARNING, START,
                    ROOT_VIEW, START);
            mConstraintSet.connect(IMG_WARNING, END,
                    ROOT_VIEW, END);
            mConstraintSet.applyTo(mRootView);
        }

        if (content != null) {
            int warningMarginTop = MARGIN * 2;
            int marginTop = MARGIN * 3, marginStart = MARGIN * 3, marginEnd = MARGIN * 3, marginBottom = MARGIN * 3;
            JustifyTextView mTVContent = new JustifyTextView(mContext);
            mTVContent.setId(TV_CONTENT);
            mTVContent.setText(content);
            mTVContent.setTextColor(contentColor);
            mTVContent.setTextSize(18);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(mTVContent, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(mTVContent,
                    18, 36, 2,
                    TypedValue.COMPLEX_UNIT_SP);
            mRootView.addView(mTVContent);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(TV_CONTENT, MATCH_CONSTRAINT);
            mConstraintSet.constrainHeight(TV_CONTENT, WRAP_CONTENT);
            if (warning) {
                mConstraintSet.connect(TV_CONTENT, TOP,
                        IMG_WARNING, BOTTOM, warningMarginTop);
            } else {
                mConstraintSet.connect(TV_CONTENT, TOP,
                        ROOT_VIEW, TOP, marginTop);
            }
            mConstraintSet.connect(TV_CONTENT, START,
                    ROOT_VIEW, START, marginStart);
            mConstraintSet.connect(TV_CONTENT, END,
                    ROOT_VIEW, END, marginEnd);
            if (positiveClickListener == null && negativeClickListener == null && !progress) {
                mConstraintSet.connect(TV_CONTENT, BOTTOM,
                        ROOT_VIEW, BOTTOM, marginBottom);
            }
            mConstraintSet.applyTo(mRootView);
        }

        if (edit) {
            int contentMarginTop = MARGIN * 2;
            int marginTop = MARGIN * 3, marginStart = MARGIN * 3, marginEnd = MARGIN * 3, marginBottom = MARGIN * 3;
            mETEdit = new EditText(mContext);
            mETEdit.setId(ET_EDIT);
            mETEdit.setHint(editHint);
            mETEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mETEdit.setImeOptions(EditorInfo.IME_ACTION_GO);
            mETEdit.addTextChangedListener(this);
            mRootView.addView(mETEdit);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(ET_EDIT, MATCH_CONSTRAINT);
            mConstraintSet.constrainHeight(ET_EDIT, WRAP_CONTENT);
            if (content != null) {
                mConstraintSet.connect(ET_EDIT, TOP,
                        TV_CONTENT, BOTTOM, contentMarginTop);
            } else {
                mConstraintSet.connect(ET_EDIT, TOP,
                        ROOT_VIEW, TOP, marginTop);
            }
            mConstraintSet.connect(ET_EDIT, START,
                    ROOT_VIEW, START, marginStart);
            mConstraintSet.connect(ET_EDIT, END,
                    ROOT_VIEW, END, marginEnd);
            mConstraintSet.applyTo(mRootView);
        }

        if (editWarningText != null) {
            int marginTop = MARGIN * 2, marginStart = MARGIN * 3, marginEnd = MARGIN * 3, marginBottom = MARGIN * 3;
            mCLEditWarning = new ConstraintLayout(mContext);
            mCLEditWarning.setId(CL_EDIT_WARNING_ROOT);
            mRootView.addView(mCLEditWarning);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(CL_EDIT_WARNING_ROOT, MATCH_CONSTRAINT);
            mConstraintSet.constrainHeight(CL_EDIT_WARNING_ROOT, WRAP_CONTENT);
            mConstraintSet.connect(CL_EDIT_WARNING_ROOT, TOP,
                    ET_EDIT, BOTTOM, marginTop);
            mConstraintSet.connect(CL_EDIT_WARNING_ROOT, START,
                    ROOT_VIEW, START, marginStart);
            mConstraintSet.connect(CL_EDIT_WARNING_ROOT, END,
                    ROOT_VIEW, END, marginEnd);
            mConstraintSet.applyTo(mRootView);
            TextView mTVEditWarning = new TextView(mContext);
            mTVEditWarning.setId(TV_EDIT_WARNING_1);
            mTVEditWarning.setText(editWarningText);
            mTVEditWarning.setTextColor(Color.RED);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(mTVEditWarning, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(mTVEditWarning,
                    16, 32, 2,
                    TypedValue.COMPLEX_UNIT_SP);
            mCLEditWarning.addView(mTVEditWarning);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(TV_EDIT_WARNING_1, MATCH_CONSTRAINT);
            mConstraintSet.constrainHeight(TV_EDIT_WARNING_1, WRAP_CONTENT);
            mConstraintSet.connect(TV_EDIT_WARNING_1, TOP,
                    CL_EDIT_WARNING_ROOT, TOP);
            mConstraintSet.connect(TV_EDIT_WARNING_1, END,
                    CL_EDIT_WARNING_ROOT, END);
            mConstraintSet.connect(TV_EDIT_WARNING_1, START,
                    IMG_EDIT_WARNING_1, END, MARGIN);
            mConstraintSet.applyTo(mCLEditWarning);

            ImageView mImgEditWarning = new ImageView(mContext);
            mImgEditWarning.setId(IMG_EDIT_WARNING_1);
            mImgEditWarning.setImageDrawable(ContextCompat.getDrawable(mContext, editWarningResId));
            mCLEditWarning.addView(mImgEditWarning);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(IMG_EDIT_WARNING_1, Utils.dpToPx(mContext, 20));
            mConstraintSet.constrainHeight(IMG_EDIT_WARNING_1, Utils.dpToPx(mContext, 20));
            mConstraintSet.connect(IMG_EDIT_WARNING_1, TOP,
                    TV_EDIT_WARNING_1, TOP);
            mConstraintSet.connect(IMG_EDIT_WARNING_1, START,
                    CL_EDIT_WARNING_ROOT, START);
            mConstraintSet.connect(IMG_EDIT_WARNING_1, END,
                    TV_EDIT_WARNING_1, START);
            mConstraintSet.applyTo(mCLEditWarning);
        }

        if (progress) {
            int marginTop = MARGIN * 2, marginStart = MARGIN * 3, marginEnd = MARGIN * 3, marginBottom = MARGIN * 3;
            ConstraintLayout mCLProgress = new ConstraintLayout(mContext);
            mCLProgress.setId(CL_PROGRESS_ROOT);
            mRootView.addView(mCLProgress);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(CL_PROGRESS_ROOT, MATCH_CONSTRAINT);
            mConstraintSet.constrainHeight(CL_PROGRESS_ROOT, WRAP_CONTENT);
            mConstraintSet.connect(CL_PROGRESS_ROOT, TOP,
                    TV_CONTENT, BOTTOM, marginTop);
            mConstraintSet.connect(CL_PROGRESS_ROOT, START,
                    ROOT_VIEW, START, marginStart);
            mConstraintSet.connect(CL_PROGRESS_ROOT, END,
                    ROOT_VIEW, END, marginEnd);
            if (positiveClickListener == null && negativeClickListener == null)
                mConstraintSet.connect(CL_PROGRESS_ROOT, BOTTOM,
                        ROOT_VIEW, BOTTOM, marginBottom);
            mConstraintSet.applyTo(mRootView);

            mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
            mProgressBar.setId(PB_PROGRESS);
            if (progressStyle > 0)
                mProgressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, progressStyle));
            mProgressBar.setProgress(0);
            mProgressBar.setMax(100);
            mCLProgress.addView(mProgressBar);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(PB_PROGRESS, MATCH_CONSTRAINT);
            mConstraintSet.constrainHeight(PB_PROGRESS, WRAP_CONTENT);
            mConstraintSet.setHorizontalWeight(PB_PROGRESS, 0.8f);
            mConstraintSet.connect(PB_PROGRESS, TOP,
                    CL_PROGRESS_ROOT, TOP);
            mConstraintSet.connect(PB_PROGRESS, START,
                    CL_PROGRESS_ROOT, START);
            mConstraintSet.connect(PB_PROGRESS, END,
                    TV_PROGRESS, START);
            mConstraintSet.connect(PB_PROGRESS, BOTTOM,
                    CL_PROGRESS_ROOT, BOTTOM);
            mConstraintSet.applyTo(mCLProgress);

            mTVProgress = new TextView(mContext);
            mTVProgress.setId(TV_PROGRESS);
            mTVProgress.setText("0%");
            mTVProgress.setTextSize(16);
            mTVProgress.setTextColor(progressTextColor);
            mCLProgress.addView(mTVProgress);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(TV_PROGRESS, MATCH_CONSTRAINT);
            mConstraintSet.constrainHeight(TV_PROGRESS, WRAP_CONTENT);
            mConstraintSet.setHorizontalWeight(TV_PROGRESS, 0.2f);
            mConstraintSet.connect(TV_PROGRESS, TOP,
                    PB_PROGRESS, TOP);
            mConstraintSet.connect(TV_PROGRESS, START,
                    PB_PROGRESS, END, MARGIN);
            mConstraintSet.connect(TV_PROGRESS, END,
                    CL_PROGRESS_ROOT, END);
            mConstraintSet.connect(TV_PROGRESS, BOTTOM,
                    PB_PROGRESS, BOTTOM);
            mConstraintSet.applyTo(mCLProgress);
        }

        if (positiveClickListener != null) {
            int marginTop = MARGIN * 3, marginBottom = MARGIN * 3;
            Button mBtnPositive = new Button(mContext);
            mBtnPositive.setId(BTN_POSITIVE);
            mBtnPositive.setText(positiveText);
            mBtnPositive.setTextColor(positiveTextColor);
            mBtnPositive.setIncludeFontPadding(false);
            mBtnPositive.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_background));
            Utils.setDrawableBackgroundColor(mBtnPositive, positiveBackgroundColor);
            Utils.setDrawableCornerRadius(mBtnPositive, Utils.dpToPx(mContext, 36) / 2);
            mBtnPositive.setOnClickListener(this);
            mRootView.addView(mBtnPositive);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(BTN_POSITIVE, Utils.dpToPx(mContext, 100));
            mConstraintSet.constrainHeight(BTN_POSITIVE, Utils.dpToPx(mContext, 36));
            if (editWarningText != null) {
                mConstraintSet.connect(BTN_POSITIVE, TOP,
                        CL_EDIT_WARNING_ROOT, BOTTOM, marginTop);
            } else if (edit) {
                mConstraintSet.connect(BTN_POSITIVE, TOP,
                        ET_EDIT, BOTTOM, marginTop);
            } else if (progress) {
                mConstraintSet.connect(BTN_POSITIVE, TOP,
                        CL_PROGRESS_ROOT, BOTTOM, marginTop);
            } else {
                mConstraintSet.connect(BTN_POSITIVE, TOP,
                        TV_CONTENT, BOTTOM, marginTop);
            }
            if (negativeClickListener != null) {
                mConstraintSet.connect(BTN_POSITIVE, START,
                        BTN_NEGATIVE, END);
            } else {
                mConstraintSet.connect(BTN_POSITIVE, START,
                        ROOT_VIEW, START);
            }
            mConstraintSet.connect(BTN_POSITIVE, END,
                    ROOT_VIEW, END);
            mConstraintSet.connect(BTN_POSITIVE, BOTTOM,
                    ROOT_VIEW, BOTTOM, marginBottom);
            mConstraintSet.applyTo(mRootView);
        }

        if (negativeClickListener != null) {
            int marginTop = MARGIN * 3, marginBottom = MARGIN * 3;
            Button mBtnNegative = new Button(mContext);
            mBtnNegative.setId(BTN_NEGATIVE);
            mBtnNegative.setText(negativeText);
            mBtnNegative.setTextColor(negativeTextColor);
            mBtnNegative.setIncludeFontPadding(false);
            mBtnNegative.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_background));
            Utils.setDrawableBackgroundColor(mBtnNegative, negativeBackgroundColor);
            Utils.setDrawableCornerRadius(mBtnNegative, Utils.dpToPx(mContext, 36) / 2);
            mBtnNegative.setOnClickListener(this);
            mRootView.addView(mBtnNegative);
            mConstraintSet = new ConstraintSet();
            mConstraintSet.constrainWidth(BTN_NEGATIVE, Utils.dpToPx(mContext, 100));
            mConstraintSet.constrainHeight(BTN_NEGATIVE, Utils.dpToPx(mContext, 36));
            if (editWarningText != null) {
                mConstraintSet.connect(BTN_NEGATIVE, TOP,
                        CL_EDIT_WARNING_ROOT, BOTTOM, marginTop);
            } else if (edit) {
                mConstraintSet.connect(BTN_NEGATIVE, TOP,
                        ET_EDIT, BOTTOM, marginTop);
            } else if (progress) {
                mConstraintSet.connect(BTN_NEGATIVE, TOP,
                        CL_PROGRESS_ROOT, BOTTOM, marginTop);
            } else {
                mConstraintSet.connect(BTN_NEGATIVE, TOP,
                        TV_CONTENT, BOTTOM, marginTop);
            }
            mConstraintSet.connect(BTN_NEGATIVE, START,
                    ROOT_VIEW, START);
            if (positiveClickListener != null) {
                mConstraintSet.connect(BTN_NEGATIVE, END,
                        BTN_POSITIVE, START);
            } else {
                mConstraintSet.connect(BTN_NEGATIVE, END,
                        ROOT_VIEW, END);
            }
            mConstraintSet.connect(BTN_NEGATIVE, BOTTOM,
                    ROOT_VIEW, BOTTOM, marginBottom);
            mConstraintSet.applyTo(mRootView);
        }

        return mRootView;
    }

    @Override
    public void onResume() {
        // Full Screen
        Window window = getDialog().getWindow();
        ViewGroup.LayoutParams params = window.getAttributes();
        if (dialogWidth != 0)
            params.width = dialogWidth;
        else
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel();
        }
    }

    public String getEditText() {
        if (mETEdit == null)
            return "";
        return mETEdit.getText().toString();
    }

    public void setEditWarningText(String text) {
        if (mTVEditDynamicWarning == null) {
            mTVEditDynamicWarning = new TextView(getActivity());
            mTVEditDynamicWarning.setId(TV_EDIT_WARNING_2);
            mTVEditDynamicWarning.setTextColor(Color.RED);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(mTVEditDynamicWarning,
                    TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(mTVEditDynamicWarning,
                    16, 32, 2,
                    TypedValue.COMPLEX_UNIT_SP);
            mCLEditWarning.addView(mTVEditDynamicWarning);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.constrainWidth(TV_EDIT_WARNING_2, MATCH_CONSTRAINT);
            constraintSet.constrainHeight(TV_EDIT_WARNING_2, WRAP_CONTENT);
            if (editWarningText != null) {
                constraintSet.connect(TV_EDIT_WARNING_2, TOP,
                        TV_EDIT_WARNING_1, BOTTOM, MARGIN);
            } else {
                constraintSet.connect(TV_EDIT_WARNING_2, TOP,
                        CL_EDIT_WARNING_ROOT, TOP);
            }
            constraintSet.connect(TV_EDIT_WARNING_2, END,
                    CL_EDIT_WARNING_ROOT, END);
            constraintSet.connect(TV_EDIT_WARNING_2, START,
                    IMG_EDIT_WARNING_2, END, MARGIN);
            constraintSet.applyTo(mCLEditWarning);

            mImgEditDynamicWarning = new ImageView(getActivity());
            mImgEditDynamicWarning.setId(IMG_EDIT_WARNING_2);
            mImgEditDynamicWarning.setImageDrawable(ContextCompat.getDrawable(getActivity(), editWarningResId));
            mCLEditWarning.addView(mImgEditDynamicWarning);
            constraintSet = new ConstraintSet();
            constraintSet.constrainWidth(IMG_EDIT_WARNING_2, Utils.dpToPx(getActivity(), 20));
            constraintSet.constrainHeight(IMG_EDIT_WARNING_2, Utils.dpToPx(getActivity(), 20));
            constraintSet.connect(IMG_EDIT_WARNING_2, TOP,
                    TV_EDIT_WARNING_2, TOP);
            constraintSet.connect(IMG_EDIT_WARNING_2, START,
                    CL_EDIT_WARNING_ROOT, START);
            constraintSet.connect(IMG_EDIT_WARNING_2, END,
                    TV_EDIT_WARNING_2, START);
            constraintSet.applyTo(mCLEditWarning);
        }
        mTVEditDynamicWarning.setText(text);
    }

    public void removeEditWarningText() {
        if (mTVEditDynamicWarning == null)
            return;
        mCLEditWarning.removeView(mTVEditDynamicWarning);
        mCLEditWarning.removeView(mImgEditDynamicWarning);
        mTVEditDynamicWarning = null;
        mImgEditDynamicWarning = null;
    }

    public void setProgress(int progress) {
        if (mProgressBar == null)
            return;
        mProgressBar.setProgress(progress);
        mTVProgress.setText(String.valueOf(progress) + "%");
    }

    public void setPositiveClickListener(OnClickListener positiveClickListener) {
        this.positiveClickListener = positiveClickListener;
    }

    public void setNegativeClickListener(OnClickListener negativeClickListener) {
        this.negativeClickListener = negativeClickListener;
    }

    public void setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setEditTextChangListener(OnEditTextChangListener editTextChangListener) {
        this.editTextChangListener = editTextChangListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case BTN_POSITIVE:
                if (mTVEditDynamicWarning != null)
                    return;
                positiveClickListener.onClick();
                this.dismiss();
                break;
            case BTN_NEGATIVE:
                negativeClickListener.onClick();
                this.dismiss();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (editTextChangListener != null) {
            editTextChangListener.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public static class Builder {
        private final Bundle arguments;
        private OnClickListener positiveClickListener;
        private OnClickListener negativeClickListener;
        private OnCancelListener cancelListener;
        private OnEditTextChangListener editTextChangListener;

        public Builder() {
            arguments = new Bundle();
        }

        public Builder setCanceledOnTouchOutside(boolean cancel) {
            arguments.putBoolean(CANCEL, cancel);
            return this;
        }

        public Builder setWarning(boolean warning) {
            arguments.putBoolean(WARNING, warning);
            return this;
        }

        public Builder setWarningIcon(int drawableResId) {
            arguments.putInt(WARNING_DRAWABLE_RES_ID, drawableResId);
            return this;
        }

        public Builder setEdit(boolean edit) {
            arguments.putBoolean(EDIT, edit);
            return this;
        }

        public Builder setEditHint(String s) {
            arguments.putString(EDIT_HINT, s);
            return this;
        }

        public Builder setEditDefaultWarningTexts(String warningText) {
            arguments.putString(EDIT_WARNING_TEXT, warningText);
            return this;
        }

        public Builder setEditWarningIcon(int drawableResId) {
            arguments.putInt(EDIT_WARNING_ICON, drawableResId);
            return this;
        }

        public Builder setEditTextChangListener(OnEditTextChangListener onEditTextChangListener) {
            editTextChangListener = onEditTextChangListener;
            return this;
        }

        public Builder setProgress(boolean progress) {
            arguments.putBoolean(PROGRESS, progress);
            return this;
        }

        public Builder setProgressStyle(int progressStyle) {
            arguments.putInt(PROGRESS_BAR_STYLE, progressStyle);
            return this;
        }

        public Builder setProgressTextColor(int color) {
            arguments.putInt(PROGRESS_TEXT_COLOR, color);
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            arguments.putInt(DIALOG_BACKGROUND_COLOR, backgroundColor);
            return this;
        }

        public Builder setContent(String text) {
            arguments.putString(CONTENT, text);
            return this;
        }

        public Builder setContentColor(int textColor) {
            arguments.putInt(CONTENT_TEXT_COLOR, textColor);
            return this;
        }

        public Builder setPositiveText(String text) {
            arguments.putString(POSITIVE_TEXT, text);
            return this;
        }

        public Builder setPositiveTextColor(int textColor) {
            arguments.putInt(POSITIVE_TEXT_COLOR, textColor);
            return this;
        }

        public Builder setPositiveBackgroundColor(int backgroundColor) {
            arguments.putInt(POSITIVE_BACKGROUND_COLOR, backgroundColor);
            return this;
        }

        public Builder setPositiveClickListener(OnClickListener onClickListener) {
            positiveClickListener = onClickListener;
            return this;
        }

        public Builder setNegativeText(String text) {
            arguments.putString(NEGATIVE_TEXT, text);
            return this;
        }

        public Builder setNegativeTextColor(int textColor) {
            arguments.putInt(NEGATIVE_TEXT_COLOR, textColor);
            return this;
        }

        public Builder setNegativeBackgroundColor(int backgroundColor) {
            arguments.putInt(NEGATIVE_BACKGROUND_COLOR, backgroundColor);
            return this;
        }

        public Builder setNegativeClickListener(OnClickListener onClickListener) {
            negativeClickListener = onClickListener;
            return this;
        }

        public Builder setCancelListener(OnCancelListener onCancelListener) {
            cancelListener = onCancelListener;
            return this;
        }

        public Builder setDialogWidth(int width) {
            arguments.putInt(DIALOG_WIDTH, width);
            return this;
        }

        public Dialog build() {
            Dialog fragment = new Dialog();
            fragment.setArguments(arguments);
            if (positiveClickListener != null) {
                fragment.setPositiveClickListener(positiveClickListener);
            }
            if (negativeClickListener != null) {
                fragment.setNegativeClickListener(negativeClickListener);
            }
            if (cancelListener != null) {
                fragment.setCancelListener(cancelListener);
            }
            if (editTextChangListener != null) {
                fragment.setEditTextChangListener(editTextChangListener);
            }
            return fragment;
        }
    }
}
