package com.pranburiorchard.netfirms.simpleandroidocr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pranburiorchard.netfirms.simpleandroidocr.customview.WritingView;
import com.pranburiorchard.netfirms.simpleandroidocr.tensorflow.Classifier;
import com.pranburiorchard.netfirms.simpleandroidocr.utils.TensorflowUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private Classifier classifier;

    // tensorflow input and output
    private static final int INPUT_SIZE = 28;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";

    private static final String MODEL_FILE = "file:///android_asset/expert-graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/labels.txt";

    private WritingView wv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadModel();

        setContentView(R.layout.activity_main);
        wv = findViewById(R.id.writingView);
        tv = findViewById(R.id.result_txt);
        initButtons();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1001);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1002);
    }

    private void initButtons() {
        Button clearBtn = findViewById(R.id.game_clear_btn);
        TextView verifyBtn = findViewById(R.id.game_ok_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(getResources().getString(R.string.questionMark));
                wv.clear();
                wv.invalidate();
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(wv.testNN());
                wv.clear();
                wv.invalidate();

                onClassify();
            }
        });
    }

    private void loadModel() {
        executor.execute(() -> {
            try {
                classifier = Classifier.create(getApplicationContext().getAssets(),
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        INPUT_NAME,
                        OUTPUT_NAME);
            } catch (final Exception e) {
                throw new RuntimeException("Error initializing TensorFlow!", e);
            }
        });
    }

    private void onClassify() {
        Bitmap original = wv.getBitmap();
        Bitmap scaled = Bitmap.createScaledBitmap(original, 28, 28, false);

        int width = 28;
        int[] pixels = new int[width * width];

        scaled.getPixels(pixels, 0, width, 0, 0, width, width);
        float[] retPixels = TensorflowUtils.createInputPixels(pixels);

        TensorflowUtils.classifyData( classifier,this, retPixels);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1001:
                // BEGIN_INCLUDE(permission_result)
                // Received permission result for camera permission.
                Log.i(TAG, "Received response for SDCARD permission request.");

                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has been granted, preview can be displayed
                    Log.i(TAG, "SDCARD permission has now been granted. Showing preview.");

                } else {
                    Log.i(TAG, "SDCARD permission was NOT granted.");

                }
                // END_INCLUDE(permission_result)

                break;
            case 1002:
                // BEGIN_INCLUDE(permission_result)
                // Received permission result for camera permission.
                Log.i(TAG, "Received response for SDCARD permission request.");

                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has been granted, preview can be displayed
                    Log.i(TAG, "SDCARD permission has now been granted. Showing preview.");

                } else {
                    Log.i(TAG, "SDCARD permission was NOT granted.");

                }
                // END_INCLUDE(permission_result)

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
