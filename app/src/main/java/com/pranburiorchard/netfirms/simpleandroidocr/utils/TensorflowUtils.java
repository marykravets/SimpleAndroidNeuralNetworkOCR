package com.pranburiorchard.netfirms.simpleandroidocr.utils;

import android.content.Context;
import android.widget.Toast;

import com.pranburiorchard.netfirms.simpleandroidocr.tensorflow.Classification;
import com.pranburiorchard.netfirms.simpleandroidocr.tensorflow.Classifier;
import com.pranburiorchard.netfirms.simpleandroidocr.tensorflow.ColorConverter;

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
