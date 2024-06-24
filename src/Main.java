import Geometry.Cluster;
import Services.ClusterManager;
import Services.FIleDatasetLoader;
import Geometry.DataPoint;
import Services.KPickerFromUser;
import Services.Logger;

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

        Logger.log("");
        for(Cluster cluster : clusterManager.getClusters())
            Logger.log("Number of elements in " + cluster + ": " + cluster.getDataPoints().size());

        // test
    }
}