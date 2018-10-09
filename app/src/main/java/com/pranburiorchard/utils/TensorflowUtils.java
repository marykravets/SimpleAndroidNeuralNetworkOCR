package com.pranburiorchard.utils;

import android.content.Context;
import android.widget.Toast;

import com.pranburiorchard.tensorflow.Classification;
import com.pranburiorchard.tensorflow.Classifier;
import com.pranburiorchard.tensorflow.ColorConverter;

public class TensorflowUtils {

    public static float[] createInputPixels(int[] pixels) {
        return ColorConverter.convertToTfFormat(pixels);
    }

    public static void classifyData(Classifier classifier, Context ctx, float[] retPixels) {
        Classification classification = classifier.recognize(retPixels);
        String result = String.format("TensorFlow: It's a %s with confidence: %f", classification.getLabel(), classification.getConf());
        Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
    }
}
