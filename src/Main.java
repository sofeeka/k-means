import Geometry.Cluster;
import Services.ClusterManager;
import Services.FIleDatasetLoader;
import Geometry.DataPoint;
import Services.KPickerFromUser;
import Services.Logger;

import java.io.FileWriter;
import java.io.IOException;
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

        boolean hasChanges = true;
        while (hasChanges)
            hasChanges = clusterManager.runIteration();

        Logger.log("");
        for (Cluster cluster : clusterManager.getClusters())
            Logger.log("Number of elements in " + cluster + ": " + cluster.getDataPoints().size());

        // test
        testKMeans(clusterManager);
        writeResultsToCSV(clusterManager);
    }

    private static void testKMeans(ClusterManager clusterManager)
    {
        double sumOfDistances = clusterManager.calculateSumOfDistances();
        System.out.println("Total Sum of Distances: " + sumOfDistances);
    }

    private static void writeResultsToCSV(ClusterManager clusterManager)
    {
        try (FileWriter writer = new FileWriter("cluster_results.csv"))
        {
            writer.write("x1,x2,x3,x4,cluster\n");
            for (int i = 0; i < clusterManager.getClusters().size(); i++)
            {
                Cluster cluster = clusterManager.getClusters().get(i);
                for (DataPoint dp : cluster.getDataPoints())
                {
                    double[] coords = dp.getCoordinates();
                    writer.write(coords[0] + "," + coords[1] + "," + coords[2] + "," + coords[3] + "," + (i + 1) + "\n");
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}