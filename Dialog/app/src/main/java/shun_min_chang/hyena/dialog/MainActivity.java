package shun_min_chang.hyena.dialog;

import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import shun_min_chang.hyena.dialog.ui.Dialog;
import shun_min_chang.hyena.dialog.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ConstraintLayout mRootView;
    private ConstraintSet mConstraintSet;
    private int MARGIN;
    private int progress;
    private Handler handler;
    private Dialog basicDialog, warningDialog, downloadDialog, updateDialog, editDialog;
    private Runnable progressRunnable;

    // UI ID
    private static final int ROOT_VIEW = 141;
    private static final int BTN_BASIC_DIALOG = 220;
    private static final int BTN_WARNING_DIALOG = 532;
    private static final int BTN_DOWNLOAD_DIALOG = 493;
    private static final int BTN_UPDATE_DIALOG = 305;
    private static final int BTN_EDIT_DIALOG = 340;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 螢幕不休眠
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mRootView = new ConstraintLayout(this);
        mRootView.setId(ROOT_VIEW);
        setContentView(mRootView);
        MARGIN = Utils.dpToPx(this, 8);
        handler = new Handler();
        initButton();
    }

    private void initButton() {
        int marginTop = MARGIN, marginStart = MARGIN, marginEnd = MARGIN;
        Button mBtnBasicDialog = new Button(this);
        mBtnBasicDialog.setId(BTN_BASIC_DIALOG);
        mBtnBasicDialog.setText("Basic Dialog");
        mBtnBasicDialog.setOnClickListener(this);
        mRootView.addView(mBtnBasicDialog);
        mConstraintSet = new ConstraintSet();
        mConstraintSet.constrainWidth(BTN_BASIC_DIALOG, ConstraintSet.MATCH_CONSTRAINT);
        mConstraintSet.constrainHeight(BTN_BASIC_DIALOG, ConstraintSet.WRAP_CONTENT);
        mConstraintSet.connect(BTN_BASIC_DIALOG, ConstraintSet.TOP,
                ROOT_VIEW, ConstraintSet.TOP, marginTop);
        mConstraintSet.connect(BTN_BASIC_DIALOG, ConstraintSet.START,
                ROOT_VIEW, ConstraintSet.START, marginStart);
        mConstraintSet.connect(BTN_BASIC_DIALOG, ConstraintSet.END,
                ROOT_VIEW, ConstraintSet.END, marginEnd);
        mConstraintSet.applyTo(mRootView);

        Button mBtnWarningDialog = new Button(this);
        mBtnWarningDialog.setId(BTN_WARNING_DIALOG);
        mBtnWarningDialog.setText("Warning Dialog");
        mBtnWarningDialog.setOnClickListener(this);
        mRootView.addView(mBtnWarningDialog);
        mConstraintSet = new ConstraintSet();
        mConstraintSet.constrainWidth(BTN_WARNING_DIALOG, ConstraintSet.MATCH_CONSTRAINT);
        mConstraintSet.constrainHeight(BTN_WARNING_DIALOG, ConstraintSet.WRAP_CONTENT);
        mConstraintSet.connect(BTN_WARNING_DIALOG, ConstraintSet.TOP,
                BTN_BASIC_DIALOG, ConstraintSet.BOTTOM, marginTop);
        mConstraintSet.connect(BTN_WARNING_DIALOG, ConstraintSet.START,
                ROOT_VIEW, ConstraintSet.START, marginStart);
        mConstraintSet.connect(BTN_WARNING_DIALOG, ConstraintSet.END,
                ROOT_VIEW, ConstraintSet.END, marginEnd);
        mConstraintSet.applyTo(mRootView);

        Button mBtnDownloadDialog = new Button(this);
        mBtnDownloadDialog.setId(BTN_DOWNLOAD_DIALOG);
        mBtnDownloadDialog.setText("Download Dialog");
        mBtnDownloadDialog.setOnClickListener(this);
        mRootView.addView(mBtnDownloadDialog);
        mConstraintSet = new ConstraintSet();
        mConstraintSet.constrainWidth(BTN_DOWNLOAD_DIALOG, ConstraintSet.MATCH_CONSTRAINT);
        mConstraintSet.constrainHeight(BTN_DOWNLOAD_DIALOG, ConstraintSet.WRAP_CONTENT);
        mConstraintSet.connect(BTN_DOWNLOAD_DIALOG, ConstraintSet.TOP,
                BTN_WARNING_DIALOG, ConstraintSet.BOTTOM, marginTop);
        mConstraintSet.connect(BTN_DOWNLOAD_DIALOG, ConstraintSet.START,
                ROOT_VIEW, ConstraintSet.START, marginStart);
        mConstraintSet.connect(BTN_DOWNLOAD_DIALOG, ConstraintSet.END,
                ROOT_VIEW, ConstraintSet.END, marginEnd);
        mConstraintSet.applyTo(mRootView);

        Button mBtnUpdateDialog = new Button(this);
        mBtnUpdateDialog.setId(BTN_UPDATE_DIALOG);
        mBtnUpdateDialog.setText("Update Dialog");
        mBtnUpdateDialog.setOnClickListener(this);
        mRootView.addView(mBtnUpdateDialog);
        mConstraintSet = new ConstraintSet();
        mConstraintSet.constrainWidth(BTN_UPDATE_DIALOG, ConstraintSet.MATCH_CONSTRAINT);
        mConstraintSet.constrainHeight(BTN_UPDATE_DIALOG, ConstraintSet.WRAP_CONTENT);
        mConstraintSet.connect(BTN_UPDATE_DIALOG, ConstraintSet.TOP,
                BTN_DOWNLOAD_DIALOG, ConstraintSet.BOTTOM, marginTop);
        mConstraintSet.connect(BTN_UPDATE_DIALOG, ConstraintSet.START,
                ROOT_VIEW, ConstraintSet.START, marginStart);
        mConstraintSet.connect(BTN_UPDATE_DIALOG, ConstraintSet.END,
                ROOT_VIEW, ConstraintSet.END, marginEnd);
        mConstraintSet.applyTo(mRootView);

        Button mBtnEditDialog = new Button(this);
        mBtnEditDialog.setId(BTN_EDIT_DIALOG);
        mBtnEditDialog.setText("Edit Dialog");
        mBtnEditDialog.setOnClickListener(this);
        mRootView.addView(mBtnEditDialog);
        mConstraintSet = new ConstraintSet();
        mConstraintSet.constrainWidth(BTN_EDIT_DIALOG, ConstraintSet.MATCH_CONSTRAINT);
        mConstraintSet.constrainHeight(BTN_EDIT_DIALOG, ConstraintSet.WRAP_CONTENT);
        mConstraintSet.connect(BTN_EDIT_DIALOG, ConstraintSet.TOP,
                BTN_UPDATE_DIALOG, ConstraintSet.BOTTOM, marginTop);
        mConstraintSet.connect(BTN_EDIT_DIALOG, ConstraintSet.START,
                ROOT_VIEW, ConstraintSet.START, marginStart);
        mConstraintSet.connect(BTN_EDIT_DIALOG, ConstraintSet.END,
                ROOT_VIEW, ConstraintSet.END, marginEnd);
        mConstraintSet.applyTo(mRootView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case BTN_BASIC_DIALOG:
                basicDialog = new Dialog.Builder()
                        .setDialogWidth((int) (mRootView.getWidth() * 0.9))
                        .setCanceledOnTouchOutside(false)
                        .setContent("Your trip data will be deleted and restart the record.")
                        .setPositiveText("OK")
                        .setPositiveClickListener(new Dialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "onPositive", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeText("Cancel")
                        .setNegativeClickListener(new Dialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "onNegative", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                getFragmentManager().beginTransaction().add(basicDialog, "").commit();
                break;
            case BTN_WARNING_DIALOG:
                warningDialog = new Dialog.Builder()
                        .setDialogWidth((int) (mRootView.getWidth() * 0.9))
                        .setWarning(true)
                        .setContent("Please do not shut down both bike and APP during updating.")
                        .setContentColor(Color.RED)
                        .setCancelListener(new Dialog.OnCancelListener() {
                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                getFragmentManager().beginTransaction().add(warningDialog, "").commit();
                break;
            case BTN_DOWNLOAD_DIALOG:
                progress = 0;
                progressRunnable = new Runnable() {
                    @Override
                    public void run() {
                        progress++;
                        downloadDialog.setProgress(progress);
                        if (progress < 100) {
                            handler.postDelayed(this, 100);
                        } else {
                            downloadDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Finish Download", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                downloadDialog = new Dialog.Builder()
                        .setDialogWidth((int) (mRootView.getWidth() * 0.9))
                        .setCanceledOnTouchOutside(false)
                        .setProgress(true)
                        .setProgressStyle(R.drawable.custom_progress_bar)
                        .setProgressTextColor(Color.parseColor("#97A136"))
                        .setContent("Downloading files...")
                        .setNegativeText("Cancel")
                        .setNegativeClickListener(new Dialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                handler.removeCallbacks(progressRunnable);
                                Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                getFragmentManager().beginTransaction().add(downloadDialog, "").commit();
                // auto add progress
                handler.post(progressRunnable);
                break;
            case BTN_UPDATE_DIALOG:
                progress = 0;
                updateDialog = new Dialog.Builder()
                        .setDialogWidth((int) (mRootView.getWidth() * 0.9))
                        .setCanceledOnTouchOutside(false)
                        .setProgress(true)
                        .setProgressStyle(R.drawable.custom_progress_bar)
                        .setProgressTextColor(Color.parseColor("#97A136"))
                        .setContent("Updating...")
                        .build();
                getFragmentManager().beginTransaction().add(updateDialog, "").commit();
                // auto add progress
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progress++;
                        updateDialog.setProgress(progress);
                        if (progress < 100) {
                            handler.postDelayed(this, 100);
                        } else {
                            updateDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Finish Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case BTN_EDIT_DIALOG:
                editDialog = new Dialog.Builder()
                        .setDialogWidth((int) (mRootView.getWidth() * 0.9))
                        .setCanceledOnTouchOutside(false)
                        .setEdit(true)
                        .setEditHint("Bike Name")
                        .setEditDefaultWarningTexts("Please input with a-z, A-Z and 0-9.")
                        .setEditTextChangListener(new Dialog.OnEditTextChangListener() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                Log.d(TAG, "onTextChanged: " + s.length());
                                if (s.length() > 15) {
                                    editDialog.setEditWarningText("Up to fifteen characters.");
                                } else {
                                    editDialog.removeEditWarningText();
                                }
                            }
                        })
                        .setPositiveText("Confirm")
                        .setPositiveClickListener(new Dialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, editDialog.getEditText(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeText("Cancel")
                        .setNegativeClickListener(new Dialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                getFragmentManager().beginTransaction().add(editDialog, "").commit();
                break;
        }
    }
}
