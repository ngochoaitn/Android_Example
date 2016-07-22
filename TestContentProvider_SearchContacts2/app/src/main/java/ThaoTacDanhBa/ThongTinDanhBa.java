package ThaoTacDanhBa;

/**
 * Created by ngoch on 17/07/2016.
 */
public class ThongTinDanhBa
{
        private int _id;

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public int get_id()
    {

        return _id;
    }

    private String _tenHienThi;
        private String _email;

    public ThongTinDanhBa()
    {
    }

    public ThongTinDanhBa(int _id, String _email, String _tenHienThi)
    {
        this._email = _email;
        this._tenHienThi = _tenHienThi;
        this._id = _id;
    }

    public void set_tenHienThi(String _tenHienThi)
    {
        this._tenHienThi = _tenHienThi;
    }

    public void set_email(String _email)
    {
        this._email = _email;
    }

    public String get_tenHienThi()
    {

        return _tenHienThi;
    }

    public String get_email()
    {
        return _email;
    }
}
