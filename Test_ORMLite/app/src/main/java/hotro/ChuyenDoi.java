package hotro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.ByteArrayOutputStream;

/**
 * Created by ngoch on 19/07/2016.
 */
public class ChuyenDoi
{
    public static class HinhAnh
    {
        public static Bitmap layHinhAnhTuTenTep(String tentep)
        {
            return BitmapFactory.decodeFile(tentep);
        }

        public static byte[] AnhSangMangByte(Bitmap bitmap, Bitmap.CompressFormat dinhdang)
        {
            if(bitmap == null)
                return null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            bitmap.compress(dinhdang, 0, outputStream);
            return outputStream.toByteArray();
        }

        public static Bitmap MangByteSangAnh(byte[] data)
        {
            if(data == null)
                return null;
            BitmapFactory.Options k = new BitmapFactory.Options();

            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
    }
}
