package com.holiestar.toolkit.component.tool.image;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import jp.wasabeef.fresco.processors.BlurPostprocessor;

/**
 * Created by Suki on 7/24/2017.
 */

public class FrescoHelper {
    private static Context context;
    private static final int DEFAULT_BLUR_RADIUS = 100;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static DraweeView loadBlur(DraweeView draweeView, String uri) {
        return loadBlur(draweeView, Uri.parse(uri), DEFAULT_BLUR_RADIUS);
    }

    public static DraweeView loadBlur(DraweeView draweeView, String uri, int radius) {
        return loadBlur(draweeView, Uri.parse(uri), radius);
    }

    public static DraweeView loadBlur(DraweeView draweeView, Uri uri) {
        return loadBlur(draweeView, uri, DEFAULT_BLUR_RADIUS);
    }

    public static DraweeView loadBlur(DraweeView draweeView, Uri uri, int radius) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).setPostprocessor(new BlurPostprocessor(context, radius)).build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request).setOldController(draweeView.getController()).build();
        draweeView.setController(controller);
        return draweeView;
    }

    public static void loadFirstAvailable(DraweeView draweeView, String... uris) {
        loadBlurFirstAvailable(draweeView, 0, uris);
    }

    public static void loadBlurFirstAvailable(DraweeView draweeView, int radius, String... uris) {
        if (uris == null) return;
        Uri[] _uris = new Uri[uris.length];
        for (int i = 0; i < uris.length; i++) {
            _uris[i] = Uri.parse(uris[i]);
        }
        loadFirstAvailable(draweeView, radius, _uris);
    }

    public static void loadFirstAvailable(DraweeView draweeView, Uri... uris) {
        loadFirstAvailable(draweeView, 0, uris);
    }

    public static void loadBlurFirstAvailable(DraweeView draweeView, int radius, Uri... uris) {
        loadFirstAvailable(draweeView, radius, uris);
    }

    private static void loadFirstAvailable(DraweeView draweeView, int blurRadius, Uri... uris) {
        boolean hasUris = uris != null && uris.length > 0;
        if (!hasUris) return;
        if (draweeView == null) return;
        ImageRequest[] requests = new ImageRequest[uris.length];
        for (int i = 0; i < uris.length; i++) {
            ImageRequest imageRequest = null;
            if (blurRadius > 0) {
                imageRequest = ImageRequestBuilder.newBuilderWithSource(uris[i]).setPostprocessor(new BlurPostprocessor(context, blurRadius)).build();
            } else {
                imageRequest = ImageRequest.fromUri(uris[i]);
            }
            requests[i] = imageRequest;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setFirstAvailableImageRequests(requests)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

}
