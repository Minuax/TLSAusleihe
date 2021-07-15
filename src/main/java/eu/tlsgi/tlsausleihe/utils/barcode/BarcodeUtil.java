package eu.tlsgi.tlsausleihe.utils.barcode;

import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;

public class BarcodeUtil {

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(100, BufferedImage.TYPE_INT_RGB, false, 0);

        barcodeGenerator.setBarHeight(6);
        barcodeGenerator.setModuleWidth(0.45);
        barcodeGenerator.setPattern("test");
        barcodeGenerator.setFontName("Arial");

        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

}
