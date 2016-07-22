package hotro;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ohia.test_ormlite.R;

/**
 * Created by ngoch on 20/07/2016.
 */
public class Constant
{
    public static final String EXTRA_THONG_TIN_MON_AN = "ThongTinMonAn";
    public static final String EXTRA_THONG_TIN_MON_AN_DA_SUA = "ThongTinMonAnDaSua";
    public static final String DIR_PICTURES = ".Pictures/Ohia/Test_ORMLite";
    public static final Bitmap BITMAP_NO_IMG = BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.no_image);
}
