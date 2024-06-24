import Geometry.DataPoint;
import Services.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ClusterManager
{
    private final ArrayList<Cluster> clusters;
    private final ArrayList<DataPoint> dataPoints;
    private final Random random = new Random(1);
    private final int k;

    private int iteration;

    public ClusterManager(ArrayList<DataPoint> dataPoints, int k)
    {
        iteration = 0;
        this.k = k;
        this.dataPoints = dataPoints;
        this.clusters = new ArrayList<>();
        createInitialClusters();
    }

    private void createInitialClusters()
    {
        addRandomClusters();
//        addHardcodedClusters();

        for (Cluster cluster : clusters)
        {
            Logger.logNoNewLine("Cluster " + cluster + ": ");
            Logger.log(Arrays.toString(cluster.getCentroid().getCoordinates()));
        }
    }

    private void addRandomClusters()
    {
        for (int i = 0; i < k; i++)
            clusters.add(getRandomCluster());
    }

    private void addHardcodedClusters()
    {
        double[] c1 = {5.0,3.3,1.4,0.2};
        double[] c2 = {5.7,2.8,4.1,1.3};
        double[] c3 = {5.9,3.0,5.1,1.8};
        clusters.add(new Cluster(new DataPoint(c1)));
        clusters.add(new Cluster(new DataPoint(c2)));
        clusters.add(new Cluster(new DataPoint(c3)));
    }

    private Cluster getRandomCluster()
    {
        return new Cluster(new DataPoint(getRandomCoordinates()));
    }

    private double[] getRandomCoordinates()
    {
        double[] max = getMaxCoordinates();
        double[] min = getMinCoordinates(max);

        Logger.log("MIN: " + Arrays.toString(min));
        Logger.log("MAX: " + Arrays.toString(max));

        double[] res = new double[max.length];
        for (int i = 0; i < res.length; i++)
            res[i] = min[i] + (max[i] - min[i]) * random.nextDouble();

        return res;
    }

    private double[] getMinCoordinates(double[] max)
    {
        double[] min = new double[max.length];
        System.arraycopy(max, 0, min, 0, min.length);
        for (DataPoint dataPoint : dataPoints)
        {
            double[] coordinates = dataPoint.getCoordinates();
            for (int i = 0; i < min.length; i++)
                min[i] = Math.min(coordinates[i], min[i]);
        }
        return min;
    }

    private double[] getMaxCoordinates()
    {
        int len = dataPoints.get(0).getCoordinates().length;
        double[] max = new double[len];
        for (DataPoint dataPoint : dataPoints)
        {
            double[] coordinates = dataPoint.getCoordinates();
            for (int i = 0; i < max.length; i++)
                max[i] = Math.max(coordinates[i], max[i]);
        }
        return max;
    }

    public boolean runIteration()
    {
        iteration++;
        assignDataPointsToClusters();
        boolean res = recalculateClusters();
        logIteration();
        return res;
    }

    private void assignDataPointsToClusters()
    {
        clearClusters();
        for (DataPoint dataPoint : dataPoints)
        {
            Cluster cluster = clusters.get(0);
            double d = dataPoint.findDistanceTo(cluster.getCentroid());

            for (Cluster c : clusters)
            {
                double temp = dataPoint.findDistanceTo(c.getCentroid());
                if (temp < d)
                {
                    d = temp;
                    cluster = c;
                }
            }
            cluster.addDataPoint(dataPoint);
        }
    }

    private void clearClusters()
    {
        for (Cluster cluster : clusters)
            cluster.setDataPoints(new ArrayList<>());
    }

    private boolean recalculateClusters()
    {
        boolean finished = true;
        for (Cluster cluster : clusters)
        {
            DataPoint centroid = DataPoint.getMeanCoordinates(cluster.getDataPoints());
            if (centroid == null)
                continue;
            finished &= centroid.equals(cluster.getCentroid());
            cluster.setCentroid(centroid);
        }
        return finished;
    }

    private void logIteration()
    {
        Logger.logNoNewLine("Iteration " + iteration + "\t");
        Logger.log(calculateSumOfDistances() + "");
    }

    private double calculateSumOfDistances()
    {
        double d = 0;
        for (Cluster cluster : clusters)
            for (DataPoint dataPoint : cluster.getDataPoints())
                d += dataPoint.findDistanceTo(cluster.getCentroid());
        return d;
    }
}
