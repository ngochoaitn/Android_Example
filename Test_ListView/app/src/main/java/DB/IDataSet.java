package DB;

/**
 * Created by ngoch on 17/07/2016.
 */
public interface IDataSet
{
    int add(IEntity entity);
    int delete(IEntity entity);
    int update(IEntity source, IEntity des);
}
