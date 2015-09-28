package com.example.toshiba.pcv_project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends LogoActivity {

    private static final int PICK_IMAGE = 0;
    private ImageView imageView;

    Button btnLine1;
    Button btnLine2;
    Button btnLine3;
    Button btnCal;
    LineTouchView lineTouchView;

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

    public void cal(View view) {
        float l1 = lineTouchView.line1.p;
        float l2 = lineTouchView.line2.p;
        float l3 = lineTouchView.line3.p;
        Log.d("l1", String.valueOf(l1));
        Log.d("l2", String.valueOf(l2));
        Log.d("l3", String.valueOf(l3));

        float t = (l3 - l1) * 100 / (l2 - l1);
        Log.d("t", String.valueOf(t));
        btnCal.setText(String.valueOf(t));

        lineTouchView.drawValue(t);
    }

}
