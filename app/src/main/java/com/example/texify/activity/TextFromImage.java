package com.example.texify.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.texify.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


public class TextFromImage extends AppCompatActivity {

    ImageView img_captured;
    TextView tv_extractedText;
    Bitmap bitmap;
    AppCompatButton btn_tryAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_from_image);
        Intent intent = getIntent();
        bitmap = intent.getParcelableExtra("BitmapImage");

        init();
    }

    public void init() {
        img_captured = findViewById(R.id.img_captured);
        tv_extractedText =findViewById(R.id.tv_extractedText);
        btn_tryAgain = findViewById(R.id.btn_tryAgain);

        if (bitmap != null)
            img_captured.setImageBitmap(bitmap);

        assert bitmap != null;
        InputImage image = InputImage.fromBitmap(bitmap,0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text text) {
                StringBuilder result = new StringBuilder();
                for(Text.TextBlock block: text.getTextBlocks()){
                    String blockText = block.getText();
                    Point[] blockCornerPoint = block.getCornerPoints();
                    Rect blockFrame = block.getBoundingBox();
                    for(Text.Line line : block.getLines()){
                        String lineTxt = line.getText();
                        Point[] lineCornerPoint = line.getCornerPoints();
                        Rect lineRect = line.getBoundingBox();
                        for(Text.Element element : line.getElements()){
                            String elementText = element.getText();
                            result.append(elementText + " ");
                        }
                    }
                    result.append("\n");
                }
                tv_extractedText.setText(result);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TextFromImage.this, "Unable to detect Text", Toast.LENGTH_SHORT).show();
            }
        });
                btn_tryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TextFromImage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }
}