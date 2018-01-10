package com.example.blackwhite;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final String IMAGE_TYPE = "image/*";

    private final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的

    private Button addPic = null;

    private ImageView imgShow = null;
    private ImageView imgShow1 = null;
    private ImageView imgShow2 = null;
    private ImageView imgShow3 = null;

    private TextView imgPath = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub

        addPic = (Button) findViewById(R.id.btn_add);
        imgPath = (TextView) findViewById(R.id.img_path);
        imgShow = (ImageView) findViewById(R.id.imgShow);
        imgShow1 = (ImageView) findViewById(R.id.imgShow1);
        imgShow2 = (ImageView) findViewById(R.id.imgShow2);
        imgShow3 = (ImageView) findViewById(R.id.imgShow3);
        addPic.setOnClickListener(listener);

    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Button btn = (Button) v;
            switch (btn.getId()) {
                case R.id.btn_add:
                    setImage();
                    break;
            }
        }

        private void setImage() {
            // TODO Auto-generated method stub
            // 使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片的地址
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType(IMAGE_TYPE);
            startActivityForResult(getAlbum, IMAGE_CODE);
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // RESULT_OK 是系统自定义得一个常量
        if (resultCode != RESULT_OK) {
            Log.e("onActivityResult", "返回的resultCode出错");
            return;
        }
        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        // 判断接收的Activity是不是选择图片的
        if (requestCode == IMAGE_CODE) {
            try {
                // 获得图片的地址Uri
                Uri originalUri = data.getData();
                // 新建一个字符串数组用于存储图片地址数据。
                String[] proj = {MediaStore.Images.Media.DATA};
                // android系统提供的接口，用于根据uri获取数据
                Cursor cursor = managedQuery(originalUri, proj, null, null,
                        null);
                // 获得用户选择图片的索引值
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将游标移至开头 ，防止引起队列越界
                cursor.moveToFirst();
                // 根据图片的URi生成bitmap
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                // 将图片转化成黑白图片
//                bm = convertToBMW(bm);
                // 显得到bitmap图片
//                Bitmap bitmap = scaleBitmap(bm, 0.1f);
                imgShow.setImageBitmap(map(bm));


//                imgShow.setImageBitmap(convertToBMW((bm)));
//                imgShow1.setImageBitmap(lineGrey(bm));
//                imgShow2.setImageBitmap(bitmap2Gray(bm));
//                imgShow3.setImageBitmap(gray2Binary(bm));

//                imgPath.setText(cursor.getString(column_index));
            } catch (IOException e) {
                Log.e("getImg", e.toString());
            }
        }
    }

    /**
     * 转为二值图像
     *
     * @param bmp 原图bitmap
     * @return
     */
    public static Bitmap convertToBMW(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组
//        for (int i=0;i<pixels.length*0.001;i++)
//        Log.e("pixels", pixels[i]+"");
//        System.out.print(pixels[i]);
        // 设定二值化的域值，默认值为100
        int tmp = 60;
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                // 分离三原色
                alpha = ((grey & 0xFF000000) >> 24);
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                if (red > tmp) {
                    red = 255;
                } else {
                    red = 0;
                }
                if (blue > tmp) {
                    blue = 255;
                } else {
                    blue = 0;
                }
                if (green > tmp) {
                    green = 255;
                } else {
                    green = 0;
                }
                pixels[width * i + j] = alpha << 24 | red << 16 | green << 8
                        | blue;
                if (pixels[width * i + j] == -1) {
                    pixels[width * i + j] = -1;
                } else {
                    pixels[width * i + j] = -16777216;
                }
            }
        }
        // 新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        // 设置图片数据
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, width, height);
        return resizeBmp;
    }

    // 九宫格灰度设计
    public synchronized static Bitmap map(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高

        // 新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        int[] pixels = new int[(width + 3) * (height + 3)]; // 通过位图的大小创建像素点数组
        // 设定二值化的域值，默认值为100
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        int color = 0;
        int p, q, t, tb, iMax;
        int[] index = new int[9];
        for (int i = 0; i < 9; i++) {
            index[i] = i;
        }
        int[] colors = new int[9];
        for (int i = 0; i < height - 2; i += 3) {
            for (int j = 0; j < width - 2; j += 3) {
                //获取九宫格每格像素
                for (int d = 0; d < 9; d++) {
                    p = bmp.getPixel(j + d % 3, i + d / 3);
                    colors[d] = p;
                    color += colors[d];

                }
                color /= -15630336;//9 * (16711680 / 10 + 65536)
                int []inColor=testRandom3();
                for (int b = 0; b < 9; b++) {
                    if (inColor[b] > color) {
                        newBmp.setPixel(j + b % 3, i + b / 3, Color.WHITE);
                    } else {
                        newBmp.setPixel(j + b % 3, i + b / 3, Color.BLACK);
                    }

                }
            }
        }

        // 设置图片数据
//        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, width, height);
        return resizeBmp;
    }

    //在一定范围内生成不重复的随机数
    //在testRandom2中生成的随机数可能会重复.
    //在此处避免该问题
    private static int[] testRandom3(){
        HashSet integerHashSet=new HashSet();
        int[] randomArr=new int[9];
        int j=0;
        Random random=new Random();
        for (int i = 0; i <20; i++) {
            int randomInt=random.nextInt(9);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                randomArr[j]=randomInt;
                j++;
            }
        }
        return randomArr;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
//        if (newBM.equals(origin)) {
//            return newBM;
//        }
//        origin.recycle();
        return newBM;
    }


    //对图像进行线性灰度变化
    public static Bitmap lineGrey(Bitmap image) {
        //得到图像的宽度和长度
        int width = image.getWidth();
        int height = image.getHeight();
        //创建线性拉升灰度图像
        Bitmap linegray = null;
        linegray = image.copy(Config.ARGB_8888, true);
        //依次循环对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到每点的像素值
                int col = image.getPixel(i, j);
                int alpha = col & 0xFF000000;
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 增加了图像的亮度
                red = (int) (1.1 * red + 30);
                green = (int) (1.1 * green + 30);
                blue = (int) (1.1 * blue + 30);
                //对图像像素越界进行处理
                if (red >= 255) {
                    red = 255;
                }

                if (green >= 255) {
                    green = 255;
                }

                if (blue >= 255) {
                    blue = 255;
                }
                // 新的ARGB
                int newColor = alpha | (red << 16) | (green << 8) | blue;
                //设置新图像的RGB值
                linegray.setPixel(i, j, newColor);
            }
        }
        return linegray;
    }

    //图像灰度化
    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    // 该函数实现对图像进行二值化处理
    public static Bitmap gray2Binary(Bitmap graymap) {
        //得到图形的宽度和长度
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        //创建二值化图像
        Bitmap binarymap = null;
        binarymap = graymap.copy(Config.ARGB_8888, true);
        //依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到当前像素的值
                int col = binarymap.getPixel(i, j);
                //得到alpha通道的值
                int alpha = col & 0xFF000000;
                //得到图像的像素RGB的值
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //对图像进行二值化处理
                if (gray <= 95) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 新的ARGB
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                //设置新图像的当前像素值
                binarymap.setPixel(i, j, newColor);
            }
        }
        return binarymap;
    }
}