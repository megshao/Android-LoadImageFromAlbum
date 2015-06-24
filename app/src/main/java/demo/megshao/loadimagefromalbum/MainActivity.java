package demo.megshao.loadimagefromalbum;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;


public class MainActivity extends Activity {

    private ImageView iv_ShowSelectedImage;
    private Button btn_SelectImage;

    private final String IMAGE_TYPE = "image/*";
    private final int IMAGE_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btn_SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }

    private void init(){
        iv_ShowSelectedImage = (ImageView) this.findViewById(R.id.iv_ShowSelectedImage);
        btn_SelectImage = (Button) this.findViewById(R.id.btn_SelectImage);
    }

    private void getImage(){
        Intent getImageFromAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getImageFromAlbum.setType(IMAGE_TYPE);
        startActivityForResult(getImageFromAlbum, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if(resultCode != RESULT_OK){
            Log.e("resultCode", "not RESULT_OK");
        }

        ContentResolver contentResolver = this.getContentResolver();

        if(requestCode == IMAGE_REQUEST_CODE){
            try{
                Uri uri = data.getData();

                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                Bitmap minbitmap = ThumbnailUtils.extractThumbnail(bitmap, 640,640);
                iv_ShowSelectedImage.setImageBitmap(minbitmap);

            }catch (IOException e){
                Log.e("IOException",e.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

