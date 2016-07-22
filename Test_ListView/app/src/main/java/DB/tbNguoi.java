package DB;

import java.io.Serializable;

/**
 * Created by ngoch on 17/07/2016.
 */
public class tbNguoi implements Serializable, IEntity
{
    public int Id;
    public String HoTen;
    public String QueQuan;
    public boolean DaChon;
    public tbNguoi(String ht, String qq)
    {
        this.HoTen = ht;
        this.QueQuan = qq;
    }

    @Override
    public tbNguoi clone()
    {
        tbNguoi nguoi = new tbNguoi(this.HoTen, this.QueQuan);
        nguoi.DaChon = this.DaChon;
        nguoi.Id = this.Id;
        return nguoi;
    }

    public static void clone(tbNguoi source, tbNguoi des)
    {
        if(des == null)
            des = new tbNguoi(source.HoTen, source.QueQuan);
        des.HoTen = source.HoTen;
        des.QueQuan = source.QueQuan;
        des.DaChon  = source.DaChon;
        des.Id = source.Id;
    }
}
