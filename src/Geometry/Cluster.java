package Geometry;

import Geometry.DataPoint;

import java.util.ArrayList;

public class Cluster
{
    private static int GLOBAL_ID = 1;
    private final int id;
    private DataPoint centroid;
    private ArrayList<DataPoint> dataPoints;

    public Cluster()
    {
        this.dataPoints = new ArrayList<>();
        this.id = GLOBAL_ID++;
    }

    public Cluster(DataPoint centroid)
    {
        this();
        this.centroid = centroid;
    }

    public DataPoint getCentroid()
    {
        return centroid;
    }

    public void setCentroid(DataPoint centroid)
    {
        this.centroid = centroid;
    }

    public ArrayList<DataPoint> getDataPoints()
    {
        return dataPoints;
    }

    public void setDataPoints(ArrayList<DataPoint> dataPoints)
    {
        this.dataPoints = dataPoints;
    }

    public void addDataPoint(DataPoint dataPoint)
    {
        this.dataPoints.add(dataPoint);
    }

    @Override
    public String toString()
    {
        return "Cluster " + id;
    }
}
