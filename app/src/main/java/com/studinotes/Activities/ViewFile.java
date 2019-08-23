package com.studinotes.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.studinotes.AdapterClass.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cn.hzw.doodle.DoodleActivity;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodleView;

import static cn.hzw.doodle.DoodleActivity.KEY_PARAMS;


public class ViewFile extends AppCompatActivity {
    String file_url,file_type_inner;
    String TAG="brochure";
    ImageView back;
    WebView view;
    VideoView videoView;
    ProgressDialog pd;
    MediaController ctrl;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    MediaPlayer  mediaPlayer = new MediaPlayer();
    public static final int REQ_CODE_DOODLE = 101;
    Bitmap bitmap=null;
    URL url =null;
    Bitmap image=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_file);
        exoPlayerView =  findViewById(R.id.exo_player_view);

        String frameVideo = "<html><body>Youtube video .. <br> <iframe width=\"320\" height=\"315\" src=\"https://www.youtube.com/embed/lY2H2ZP56K4\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        file_url = getIntent().getStringExtra("file_url");
        file_type_inner = getIntent().getStringExtra("file_type_inner");
        String extension = file_url.substring(file_url.lastIndexOf(".") + 1);
        Log.d(TAG, "brochure_file: " + file_url);
        Log.d(TAG, "extension: " + extension);
        view = findViewById(R.id.webView);
       // videoView = findViewById(R.id.videoView);
        view.setWebViewClient(new AppWebViewClients());
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setJavaScriptEnabled(true);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            url = new URL(file_url);
            image   = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(file_type_inner.equals("DrawingPad")){
            DoodleParams params = new DoodleParams();
            params.mIsFullScreen = true;
            // 图片路径
            params.mImagePath = saveBitmapAndGetPath(image);
            // 初始画笔大小
            params.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
            // 画笔颜色
            params.mPaintColor = Color.RED;
            // 是否支持缩放item
            params.mSupportScaleItem = true;

            Intent intent1 = new Intent(ViewFile.this, DoodleActivity.class);
            intent1.putExtra(KEY_PARAMS, params);
            intent1.putExtra("from", "edit_file");

            //startActivity(intent1);
            startActivityForResult(intent1, REQ_CODE_DOODLE);
        }
        else {


            if (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png") || extension.equals("tiff")) {
                String imgSrcHtml = "<html><img src='" + file_url + "' /></html>";
                view.loadData(imgSrcHtml, "text/html", "UTF-8");
            } else if (extension.equals("pdf")) {
                view.loadUrl("https://docs.google.com/gview?embedded=true&url=" + file_url);
            } else if (extension.equals("doc") || extension.equals("docx")) {
                view.loadData(frameVideo, "text/html", "UTF-8");
                view.loadUrl("http://docs.google.com/gview?embedded=true&url=" + file_url);

            } else if (extension.equals("xls") || extension.equals("xlsx")) {
                view.loadData(frameVideo, "text/html", "UTF-8");
                view.loadUrl("http://docs.google.com/gview?embedded=true&url=" + file_url);

            } else if (extension.equals("ppt") || extension.equals("pptx")) {
                view.loadData(frameVideo, "text/html", "UTF-8");
                view.loadUrl("http://docs.google.com/gview?embedded=true&url=" + file_url);

            } else if (extension.equals("mp4")) {
                view.setVisibility(View.GONE);
                exoPlayerView.setVisibility(View.VISIBLE);

                try {


                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                    Uri videoURI = Uri.parse(file_url);

                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                    exoPlayerView.setPlayer(exoPlayer);
                    exoPlayer.prepare(mediaSource);
                    exoPlayer.setPlayWhenReady(true);
                } catch (Exception e) {
                    Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                }


            } else if ((extension.equals("mp3")) || (extension.equals("wav"))) {
                view.setVisibility(View.GONE);
                exoPlayerView.setVisibility(View.VISIBLE);

                try {


                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                    Uri videoURI = Uri.parse(file_url);

                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                    exoPlayerView.setPlayer(exoPlayer);
                    exoPlayer.prepare(mediaSource);
                    exoPlayer.setPlayWhenReady(true);
                } catch (Exception e) {
                    Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                }

            }
        }
        back=findViewById(R.id.toolbar_back);


       //

        view.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if(exoPlayer!= null){
                    exoPlayer.stop();
                    exoPlayer.release();
                    exoPlayer = null;
                }
                finish();
            }
        });
    }

    private String saveBitmapAndGetPath(Bitmap bmp) {

        String path = null;

        try {

            // imgMain.setImageBitmap(bmp);
            File f = new File(get_HBHG_Directory() + "myimg" + ".jpg");
            FileOutputStream fos = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            Log.d("TAGGG", "real path = "+f.getAbsolutePath());

            path = f.getAbsolutePath();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return path;
    }
    public String get_HBHG_Directory() {
        String FILE = Make_Directory().toString();

        FILE = FILE + "/";


        Log.d("TAG", "dir = "+FILE);

        //  return mContext.getApplicationContext().getFilesDir().getAbsolutePath();
        return FILE;
    }
    public File Make_Directory(){

        File dbDirectory = new File(getPathOfAppInternalStorage() + "/" + "DoodleSha");
        if(!dbDirectory.exists()) {
            dbDirectory.mkdir();
        }

        return dbDirectory;
    }
    public String getPathOfAppInternalStorage() {

        String FILE = Environment.getExternalStorageDirectory().getAbsolutePath().toString();

        //  return mContext.getApplicationContext().getFilesDir().getAbsolutePath();
        return FILE;
    }

    public class AppWebViewClients extends WebViewClient {



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

        }
    }
}