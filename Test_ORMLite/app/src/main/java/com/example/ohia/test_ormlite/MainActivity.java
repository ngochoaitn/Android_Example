package com.example.ohia.test_ormlite;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.ohia.test_ormlite.MonAnAdapter.TbMonAnAdapter;
import com.example.ohia.test_ormlite.activitys.AcChiTietMonAn;
import com.example.ohia.test_ormlite.activitys.AcModifyMonAn;
import com.example.ohia.test_ormlite.db.DbMonAnDatabaseHelper;
import com.example.ohia.test_ormlite.db.TbMonAn;
import com.j256.ormlite.dao.Dao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hotro.Constant;
import hotro.LayDuLieuTuUngDungKhac;
import hotro.ThongBao;

public class MainActivity extends AppCompatActivity
{
    ArrayList<TbMonAn> _monAnData;
    TbMonAnAdapter _monAnAdapter;
    DbMonAnDatabaseHelper db;
    Dao<TbMonAn, Integer> tbMonAnIntegerDao;
    int _posListView = 0;

    ListView _lvDanhSach;

    public static final int REQUEST_CODE_MODIFY_THONG_TIN_MON_AN = 1;
    public static final int REQUEST_CODE_CHI_TIET_THONG_TIN_MON_AN = 2;

    SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.loadControl();

        this.loadData();

        preferences = getSharedPreferences("com.example.ohia.test_ormlite", MODE_PRIVATE);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//            }
//        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(preferences.getBoolean("firstrun", true))
        {
            preferences.edit().putBoolean("firstrun", false).commit();

            try
            {
                tbMonAnIntegerDao.delete(tbMonAnIntegerDao.queryForAll());
                this.TestORMLite();
                this.loadData();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        super.onActivityResult(requestcode, resultcode, data);

        switch (requestcode)
        {
            case REQUEST_CODE_MODIFY_THONG_TIN_MON_AN:
                if(resultcode == AcModifyMonAn.RESULT_OK)
                {
                    this.loadData();
                }
                break;
            case REQUEST_CODE_CHI_TIET_THONG_TIN_MON_AN:
                this.loadData();
                break;
        }

//        if(requestcode == LayDuLieuTuUngDungKhac.REQUEST_CHON_HINH_ANH)
//        {
//            if(resultcode == Activity.RESULT_OK)
//            {
//                if(data != null)
//                {
//                    try
//                    {
//                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
//                        Uri selectedImage = data.getData();
//                        String[] projection = new String[] {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(selectedImage, projection, null, null, null);
//                        cursor.moveToFirst();
//                        String filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
//                        cursor.close();
//
//                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//
//                        int i = 1;
//                        int ii = i  + i;
//                    }
//                    catch (FileNotFoundException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//                else
//                {
//                    int i = 1;
//                }
//            }
//            else
//            {
//
//            }
//        }
    }

    private void loadControl()
    {
        _lvDanhSach = (ListView)findViewById(R.id.lvDanhSach);
    }

    private void loadData()
    {
        try
        {
            db = new DbMonAnDatabaseHelper(this);
            tbMonAnIntegerDao = db.getTbMonAnDao();
            _monAnData = new ArrayList<>();
            //_monAnData.removeAll(tbMonAnIntegerDao.queryForAll());
            _monAnData.addAll(tbMonAnIntegerDao.queryForAll());
            _monAnAdapter = new TbMonAnAdapter(this, _monAnData);

            _lvDanhSach.setAdapter(_monAnAdapter);
            //_lvDanhSach.setScrollY(160);
            //_lvDanhSach.smoothScrollToPosition(_posListView);
            //_lvDanhSach.scrollTo(0, _posListView*100);
            //_lvDanhSach.setVerticalScrollbarPosition(_posListView);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void TestORMLite() throws SQLException
    {
        //Test OrmLite
        DbMonAnDatabaseHelper db = new DbMonAnDatabaseHelper(this);
        Dao<TbMonAn, Integer> tbMonAnIntegerDao = db.getTbMonAnDao();

//        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        getIntent.setType("image/*");
//
//        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickIntent.setType("image/*");
//
//        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
//
//        int PICK_IMAGE = 10;
//        startActivityForResult(chooserIntent, PICK_IMAGE);

        TbMonAn monAnMoi = new TbMonAn();
        monAnMoi.setTenMonAn("MÌ ÁP CHẢO THỊT HEO");
        monAnMoi.setMoTa("Buổi cuối tuần đổi mới thực đơn với món Mì áo chảo thịt heo. Thịt nạc dăm xào mềm vừa chín tới vẫn giữ được độ giòn ngọt, không ngán hoà cùng vị nấm rơm đậm đà và sắc vị của rau củ, hứa hẹn mang đến một món ăn thoả mãn cả về khẩu vị lẫn thị giác của thực khách ở nhà mình đấy!");
        monAnMoi.setMucDo("Dễ");
        monAnMoi.setNguyenLieu("Thịt heo cắt miếng mỏng, ướp với 1M nước tương “Phú Sĩ”" +
                               "Bột ngọt\n" +
                               "Đường\n" +
                               "Muối\n" +
                               "Nấm rơm\n" +
                               "Bắp cải xắt sợi");
        monAnMoi.setCachLam("Bước 1: Mì trứng luộc chín, trộn với 1m dầu mè, xốc đều. Đặt chảo lên bếp, áp chảo đều hai mặt. Trút ra dĩa.\n" +
                            "Bước 2: Phi hành tỏi cho thơm, cho thịt heo vào xào săn, nêm 1M dầu hào, tiếp tục cho nấm rơm, cà chua, bắp cải, cà rốt, cải ngọt vào xào chín. Nêm 2M nước tương “Phú Sĩ”. Cho boa-rô vào, đảo đều với thịt và rau củ.");
        tbMonAnIntegerDao.create(monAnMoi);

        monAnMoi= new TbMonAn();
        monAnMoi.setTenMonAn("CANH GÂN BÒ CẢI CHUA");
        monAnMoi.setMoTa("Canh gân bò cải chua với nguyên liệu dễ chế biến, gân bò sơ chế săn giòn quyện vị chua nhẹ của cải và cà chua, là món ăn tuyệt vời dành cho cả gia đình. Ngoài mùi vị đậm đà đặc trưng, gân bò còn bổ sung lượng collagen có tác dụng giúp cải thiện làn da, giữ dáng. Còn chần chờ gì mà không bắt tay thực hiện món ăn này nào?");
        monAnMoi.setNguyenLieu("– Thịt gân bò\n" +
                "Gừng\n" +
                "Cà chua");
        monAnMoi.setMucDo("Khó");
        monAnMoi.setCachLam("Bước 1: Rửa gân thịt bò với gừng rượu để gân thịt thơm hơn.\n" +
                "Bước 2: Xào phần thịt gân bò thật săn để khi ăn gân bò mềm giòn mà không bị ngán.");
        tbMonAnIntegerDao.create(monAnMoi);


        monAnMoi= new TbMonAn();
        monAnMoi.setTenMonAn("XÔI TÔM CỦ SEN");
        monAnMoi.setMoTa("Củ sen ngọt, giòn, chả tôm dai, thanh vị vừa ăn, cùng với xôi lá cẩm dẻo bùi tạo nên một món ăn không những hài hòa về mùi vị mà còn hút mắt về màu sắc, rất thích hợp làm món khai vị cho các bữa tiệc gia đình.");
        monAnMoi.setNguyenLieu("Tôm đập dập\n" +
                               "Củ sen bào vỏ");
        monAnMoi.setMucDo("Siêu dễ");
        monAnMoi.setCachLam("Bước 1: Nhồi chả tôm vào kín bên trong củ sen, dùng giấy bạc gói kín 2 đầu. Đem hấp khoảng 20 phút cho chín, để nguội, cắt lát chả tôm củ sen dày 1cm.\n" +
                            "Bước 2: Xôi cho vào giấy bạc tạo hình khối tròn có đường kính bằng với củ sen, cắt lát như củ sen, phết mỡ hành lên trên.");
        tbMonAnIntegerDao.create(monAnMoi);

        monAnMoi= new TbMonAn();
        monAnMoi.setTenMonAn("BAO TỬ CÁ KHO TIÊU");
        monAnMoi.setMoTa("Một món kho đơn giản với nguyên liệu đặc biệt sẽ giúp thực đơn hàng ngày trở nên thú vị hơn. Bao tử cá giòn giòn, đậm vị, kết hợp với thịt ba rọi béo mềm cùng vị cay thơm của tiêu sẽ góp phần làm cho bữa cơm gia đình ngày mưa càng thêm ấm cúng.");
        monAnMoi.setNguyenLieu("Bao tử cá\nTiêu");
        monAnMoi.setMucDo("Siêu dễ");
        monAnMoi.setCachLam("Xào thịt ba rọi với 2M dầu điều cho tươm mỡ\n" +
                            "Cho đầu hành, ớt hiểm, tiêu vào xào thơm, thêm bao tử cá xào săn." +
                            "Nêm 4M nước tương “Phú Sĩ”, 1m đường, 1m Bột ngọt AJI-NO-MOTO®, 2/3m nước màu, thêm từ từ nước dừa vào kho cho đến khi bao tử và thịt thấm vị, nước kho sánh sệt" +
                            "Tắt lửa cho hành lá vào.");
        tbMonAnIntegerDao.create(monAnMoi);

        monAnMoi= new TbMonAn();
        monAnMoi.setTenMonAn("GÀ NƯỚNG XIÊN XỐT ĐẬU PHỘNG");
        monAnMoi.setMoTa("Công thức đặc biệt từ Món Ngon Mỗi Ngày cho món gà nướng quen thuộc trở nên độc đáo. Vị chua thanh của me, hương thơm của đậu phộng cùng xốt mayonnaise béo nhẹ sẽ khiến từng miếng thịt gà trở nên đậm đà khác lạ.");
        monAnMoi.setNguyenLieu("Thịt ức gà cắt miếng mỏng\n" +
                               "Que xiên ngâm nước");
        monAnMoi.setMucDo("Siêu dễ");
        monAnMoi.setCachLam("Bước 1: Xốt: Phi thơm hành tím băm, cho nước me với 1M nước, 2M nước mắm, 2M đường vào, đun sôi, khuấy đều cho gia vị tan hết, cho ra chén để nguội. Sau đó, cho 2M xốt Mayonnaise Aji-Mayo® và ngò rí, đậu phộng rang vào trộn đều.\n" +
                            "Bước 2: Cho thịt xiên lên vỉ nướng chín vàng hoặc cho vào áp chảo.");
        tbMonAnIntegerDao.create(monAnMoi);

        monAnMoi= new TbMonAn();
        monAnMoi.setTenMonAn("VỊT NƯỚNG NƯỚC CỐT DỪA");
        monAnMoi.setMoTa("Một món nướng mới với nước cốt dừa, thịt vịt mềm thấm vị cùng mùi thơm của sả, lá chanh  tạo nên vị đậm đà béo nhẹ và thơm rất đặc trưng của nước cốt dừa cùng vị cay cay của ớt. Lại là một món nướng ngon tuyệt vời để chiêu đãi cả nhà khiến ai cũng sẽ phải trầm trồ trước món ăn vừa quen vừa lạ này.");
        monAnMoi.setNguyenLieu("Lá sả cắt đôi\nỚt khô\n");
        monAnMoi.setMucDo("Siêu dễ");
        monAnMoi.setCachLam("Bước 1(Xay xốt)  : Cho ớt khô, hành tím băm, sả băm, riềng băm, 1M dầu điều, nước cốt dừa, 1M mật ong, 1m muối, 1M nước mắm, 1M nước tương “Phú Sĩ”, 1M bột ngọt AJI-NO-MOTO® vào, xay nhuyễn, cho vịt vào, trộn đều, để 20 phút cho thấm.\n" +
                            "Bước2 (Nướng vịt): Làm nóng lò nướng, nhiệt độ 185 độ C, cho lá sả vào khay, rồi xếp thịt vịt lên trên, giữ lại xốt ướp. Cho khay vịt vào lò nướng 30 phút, thỉnh thoảng trở miếng vịt và phết xốt ướp lên, vịt chín lấy ra chặt miếng vừa ăn, xếp lên dĩa rắc lá chanh lên trên khi thịt vịt còn nóng.");
        tbMonAnIntegerDao.create(monAnMoi);

        List<TbMonAn> test = tbMonAnIntegerDao.queryForAll();
        int count = test.size();

        //ThongBao.HopThoaiThongBao(this, "Đã thêm một món ăn");
    }

    public void btnThemMonAnMoi_Click(View view)
    {
        Intent intent = new Intent(this, AcModifyMonAn.class);
        TbMonAn monAn = null;//Đánh dấu là thêm mới
        intent.putExtra(Constant.EXTRA_THONG_TIN_MON_AN, monAn);
        startActivityForResult(intent, REQUEST_CODE_MODIFY_THONG_TIN_MON_AN);
    }

    public void lblTuyChon_Click(View view)
    {
        openOptionsMenu();
    }

    public void itemMonAn_Click(View view)
    {
        //_posListView = _lvDanhSach.getScrollY();
        _posListView = _lvDanhSach.getPositionForView(view);

        Intent i = new Intent(this, AcChiTietMonAn.class);

        i.putExtra(Constant.EXTRA_THONG_TIN_MON_AN, (TbMonAn)view.getTag());
        startActivityForResult(i, REQUEST_CODE_CHI_TIET_THONG_TIN_MON_AN);
    }
};