package Services;

import Geometry.Cluster;
import Geometry.DataPoint;

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

        Logger.log("");
        for (Cluster cluster : clusters)
        {
            Logger.logNoNewLine(cluster + ": ");
            Logger.log(Arrays.toString(cluster.getCentroid().getCoordinates()));
        }
        Logger.log("");
    }

    private void addRandomClusters()
    {
        for (int i = 0; i < k; i++)
            clusters.add(getRandomCluster());
    }

    private Cluster getRandomCluster()
    {
        return new Cluster(new DataPoint(getRandomCoordinates()));
    }

    private double[] getRandomCoordinates()
    {
        double[] max = getMaxCoordinates();
        double[] min = getMinCoordinates(max);

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

        clearClusters();
        assignDataPointsToClusters();
        boolean hasChanges = recalculateClusters();

        logIteration();
        return hasChanges;
    }

    private void assignDataPointsToClusters()
    {
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
        boolean hasChanges = false;
        for (Cluster cluster : clusters)
        {
            DataPoint centroid = DataPoint.getMeanCoordinates(cluster.getDataPoints());
            if (centroid == null)
                continue;
            hasChanges |= !centroid.equals(cluster.getCentroid());
            cluster.setCentroid(centroid);
        }
        return hasChanges;
    }

    private void logIteration()
    {
        Logger.logNoNewLine("Iteration " + iteration + "\t");
        Logger.log(calculateSumOfDistances() + "");
    }

    public double calculateSumOfDistances()
    {
        double d = 0;
        for (Cluster cluster : clusters)
            for (DataPoint dataPoint : cluster.getDataPoints())
                d += dataPoint.findDistanceTo(cluster.getCentroid());
        return d;
    }

    public ArrayList<Cluster> getClusters()
    {
        return clusters;
    }
}
