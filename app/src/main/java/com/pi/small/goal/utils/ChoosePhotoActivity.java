package com.pi.small.goal.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.pi.small.goal.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class ChoosePhotoActivity extends Activity implements View.OnClickListener {

    private TextView cancel_text;
    private TextView camera_text;
    private TextView gallery_text;
    private TextView own_text;

    private static final String IMAGE_FILE_NAME = "header.png";
    private String path;

    String newPath;
    String cutPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        init();
    }

    private void init() {
        cancel_text = (TextView) findViewById(R.id.cancel_text);
        camera_text = (TextView) findViewById(R.id.camera_text);
        gallery_text = (TextView) findViewById(R.id.gallery_text);
        own_text = (TextView) findViewById(R.id.own_text);

        cancel_text.setOnClickListener(this);
        camera_text.setOnClickListener(this);
        gallery_text.setOnClickListener(this);
        own_text.setOnClickListener(this);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_text:
                finish();
                break;
            case R.id.gallery_text:
                goGallery();
                break;
            case R.id.camera_text:
                goCamera();
                break;
            case R.id.own_text:

                break;
        }
    }

    /**
     * 获取相册
     */
    private void goGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Code.RESULT_GALLERY_CODE);
    }

    /**
     * 拍照获取照片
     */
    private void goCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(cameraIntent, Code.RESULT_CAMERA_CODE);
    }

    /**
     * 获取照片uri
     */
    private Uri getImageUri() {
//        ContentValues contentValues = new ContentValues(1);
//        contentValues.put(MediaStore.Images.Media.DATA, new File(Environment.getExternalStorageDirectory(),
//                IMAGE_FILE_NAME).getAbsolutePath());
//        Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//        return uri;
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent();
        if (requestCode == 2102) {
            if (data != null) {
                String androidId = android.os.Build.VERSION.RELEASE;
                int firstNum = Integer.parseInt(androidId.substring(0, 1));
                int secondNum = Integer.parseInt(androidId.substring(2, 3));
                Uri uri = data.getData();
                if (firstNum == 4) {
                    if (secondNum >= 4) {
                        path = getPath(this, uri);
                    } else {
                        String[] proj = {MediaStore.Images.Media.DATA};
                        CursorLoader loader = new CursorLoader(this, uri,
                                proj, null, null, null);
                        Cursor cursor = loader.loadInBackground();
                        int column_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        path = cursor.getString(column_index);
                    }
                } else if (firstNum > 4) {
                    path = getPath(this, uri);
                } else if (firstNum < 4) {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    CursorLoader loader = new CursorLoader(this, uri, proj,
                            null, null, null);
                    Cursor cursor = loader.loadInBackground();
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                }


                Bitmap bitmap = BitmapFactory.decodeFile(path);

//                int bitmapWidth = bitmap.getWidth();
//                int bitmapHeight = bitmap.getHeight();
                // 缩放图片的尺寸
                Matrix matrix = new Matrix();
                matrix.postRotate(readPictureDegree(path)); /*翻转90度*/
                // 产生缩放后的Bitmap对象
//                Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
                Bitmap resizeBitmap = reduce(bitmap, 256, 256, path, false);

                newPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + System.currentTimeMillis() + ".PNG";
                File newFile = new File(newPath);
                try {
                    newFile.createNewFile();
                    FileOutputStream fOut = null;
                    fOut = new FileOutputStream(newFile);
                    if (resizeBitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut)) {
                        fOut.flush();
                        fOut.close();
                    }
                    if (!resizeBitmap.isRecycled()) {
                        resizeBitmap.recycle();//记得释放资源，否则会内存溢出
                    }

                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();//记得释放资源，否则会内存溢出
                    }
                    CutPhoto(Uri.parse("file://" + newPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (requestCode == 2101) {

            Bitmap bitmap = BitmapFactory.decodeFile(getImageUri().getPath());
            if (bitmap != null) {
//                int bitmapWidth = bitmap.getWidth();
//                int bitmapHeight = bitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.postRotate(readPictureDegree(getImageUri().getPath())); /*翻转90度*/
                // 产生缩放后的Bitmap对象
//                Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
                Bitmap resizeBitmap = reduce(bitmap, 256, 256, getImageUri().getPath(), false);
                newPath = getImageUri().getPath().substring(0, getImageUri().getPath().lastIndexOf("/")) + "/" + System.currentTimeMillis() + ".PNG";
                File newFile = new File(newPath);
                try {
                    newFile.createNewFile();
                    FileOutputStream fOut = null;
                    fOut = new FileOutputStream(newFile);
                    if (resizeBitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut)) {
                        fOut.flush();
                        fOut.close();
                    }
                    if (!resizeBitmap.isRecycled()) {
                        resizeBitmap.recycle();//记得释放资源，否则会内存溢出
                    }
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();//记得释放资源，否则会内存溢出
                    }
                    CutPhoto(Uri.parse("file://" + newPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == 2103) {
            intent.putExtra("path", newPath);
            setResult(Code.RESULT_CAMERA_CODE, intent);
            finish();

        } else {
            finish();
        }

    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void CutPhoto(Uri uri) {
        cutPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + System.currentTimeMillis() + ".JPG";
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 0.999);
        intent.putExtra("scale", true);//是否保留比例
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 2103);
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * 压缩图片
     *
     * @param bitmap   源图片
     * @param width    想要的宽度
     * @param height   想要的高度
     * @param isAdjust 是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     */
    public static Bitmap reduce(Bitmap bitmap, int width, int height, String path, boolean isAdjust) {
        // 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            return bitmap;
        }
        if (width == 0 && height == 0) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }

        // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor, int scale, int
        // roundingMode);
        // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
        float sx = new BigDecimal(width).divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal
                .ROUND_DOWN).floatValue();
        float sy = new BigDecimal(height).divide(new BigDecimal(bitmap.getHeight()), 4,
                BigDecimal.ROUND_DOWN).floatValue();
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);
            sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
        matrix.postRotate(readPictureDegree(path)); /*翻转90度*/
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
    }
}