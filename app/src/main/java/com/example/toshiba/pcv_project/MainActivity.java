package com.example.toshiba.pcv_project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends LogoActivity {

    private static final int PICK_IMAGE = 0;
    private ImageView imageView;

    Button btnLine1;
    Button btnLine2;
    Button btnLine3;
    Button btnCal;
    LineTouchView lineTouchView;

    String name;
    Paint paint;
    boolean isActive;
    boolean isShowValue;
    boolean isRealValue;
    float y;
    float p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCapture = (Button) findViewById(R.id.button_open);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pick your image"), PICK_IMAGE);
            }
        });

        Button btnSave = (Button) findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                View v = (ImageView) findViewById(R.id.imageView);
                v.setDrawingCacheEnabled(true);
                Bitmap bm = imageView.getDrawingCache().copy(Bitmap.Config.ARGB_8888,true);
                v.setDrawingCacheEnabled(false);

                try {
                    Date d = new Date();
                    String filename  = (String) DateFormat.format("kkmmss-MMddyyyy"
                            , d.getTime());
                    File dir = new File(Environment.getExternalStorageDirectory()
                            , "/Pictures/" + filename + ".jpg");
                    FileOutputStream out = new FileOutputStream(dir);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    Canvas mainCanvas = lineTouchView.canvas;
                    Canvas c = new Canvas(bm);
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(60);
                    String text = String.format("%.2f%s",lineTouchView.actualValue,"%");
                    c.drawText(text, 10, c.getHeight() - 60, paint);

                    Canvas lineCanvas = lineTouchView.canvas;
                    Canvas b = new Canvas(bm);
                    paint.setColor(Color.YELLOW);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(5);

                    paint.setAntiAlias(true);
                    b.drawLine(0, lineTouchView.line1.y, b.getWidth(), lineTouchView.line1.y, paint);
                    b.drawLine(0, lineTouchView.line2.y, b.getWidth(), lineTouchView.line2.y, paint);
                    b.drawLine(0, lineTouchView.line3.y, b.getWidth(), lineTouchView.line3.y, paint);

                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(40);
                    b.drawText("Line 1", 0, lineTouchView.line1.y, paint);
                    b.drawText("Line 2", 0, lineTouchView.line2.y, paint);
                    b.drawText("Line 3", 0, lineTouchView.line3.y, paint);

                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    out.write(bos.toByteArray());
                    Toast.makeText(getApplicationContext(), "Save card!"
                            , Toast.LENGTH_SHORT).show();

                    galleryAddPic(Uri.fromFile(dir));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        initInstances();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent returnedIntent){
        imageView = (ImageView)findViewById(R.id.imageView);
        super.onActivityResult(requestCode, resultCode, returnedIntent);

        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = returnedIntent.getData();
                    String imagePath = findPath(imageUri);

                    Bitmap imagaData = BitmapFactory.decodeFile(imagePath);
                    Log.d("path", imagePath);
                    imageView.setImageBitmap(imagaData);
                }
        }

    }

    private String findPath(Uri uri) {
        String imagePath;

        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, columns, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            imagePath = cursor.getString(columnIndex);
        }
        else {
            imagePath = uri.getPath();
        }

        return imagePath;
    }

    private void initInstances() {
        btnLine1 = (Button) findViewById(R.id.btnLine1);
        btnLine2 = (Button) findViewById(R.id.btnLine2);
        btnLine3 = (Button) findViewById(R.id.btnLine3);
        btnCal = (Button) findViewById(R.id.btnCal);

        lineTouchView = (LineTouchView) findViewById(R.id.vLineTouchView);
    }

    public void activeLine(View view) {
        switch (view.getId()) {
            case R.id.btnLine1:
                lineTouchView.line1.active();
                lineTouchView.line2.inactive();
                lineTouchView.line3.inactive();
                break;
            case R.id.btnLine2:
                lineTouchView.line1.inactive();
                lineTouchView.line2.active();
                lineTouchView.line3.inactive();
                break;
            case R.id.btnLine3:
                lineTouchView.line1.inactive();
                lineTouchView.line2.inactive();
                lineTouchView.line3.active();
                break;
        }
    }

    private void galleryAddPic(Uri contentUri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void cal(View view) {
        float l1 = lineTouchView.line1.p;
        float l2 = lineTouchView.line2.p;
        float l3 = lineTouchView.line3.p;
        Log.d("l1", String.valueOf(l1));
        Log.d("l2", String.valueOf(l2));
        Log.d("l3", String.valueOf(l3));

        float t = (l3 - l1) * 100 / (l2 - l1);
        Log.d("t", String.valueOf(t));
        btnCal.setText(String.format("%.2f%s", t, "%"));

        lineTouchView.drawValue(t);
    }

}
