import Services.FIleDatasetLoader;
import Geometry.DataPoint;
import Services.KPickerFromUser;

import java.util.ArrayList;

public class Main
{
    public static final String dataSet = "iris.data";
    public static void main(String[] args)
    {
        // pick k
        int k = new KPickerFromUser().pickK();

        // train
        ArrayList<DataPoint> dataPoints = new FIleDatasetLoader(dataSet).load();
        ClusterManager clusterManager = new ClusterManager(dataPoints, k);

        while(!clusterManager.runIteration())
            continue;

        // test
    }
}