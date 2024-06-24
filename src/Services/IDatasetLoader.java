package DataLoaders;
import Geometry.DataPoint;

import java.util.ArrayList;

public interface IDatasetLoader
{
    public ArrayList<DataPoint> load();
}
