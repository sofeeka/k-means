package Geometry;

import Exceptions.WrongNumberOfCoordinates;
import Services.Logger;

import java.util.ArrayList;

public class DataPoint
{
    private double[] coordinates;

    public DataPoint(double[] coordinates)
    {
        this.coordinates = coordinates;
    }

    public double findDistanceTo(DataPoint point)
    {
        if(point.coordinates.length != this.coordinates.length)
            throw new WrongNumberOfCoordinates(point.coordinates.length + " and " + this.coordinates.length + " should be equal");

        double len = 0;
        for (int i = 0; i < coordinates.length; i++)
            len += Math.pow(this.coordinates[i] - point.coordinates[i], 2);

        return Math.sqrt(len);
    }

    public double[] getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates)
    {
        this.coordinates = coordinates;
    }

    public static DataPoint getMeanCoordinates(ArrayList<DataPoint> dataPoints)
    {
        if(dataPoints == null || dataPoints.isEmpty())
        {
            Logger.log("Cluster has 0 data points");
            return null;
        }
        int len = dataPoints.get(0).coordinates.length;
        double[] res = new double[len];

        for(DataPoint dataPoint : dataPoints)
            for (int i = 0; i < len; i++)
                res[i] += dataPoint.coordinates[i];

        for (int i = 0; i < len; i++)
            res[i] = res[i] / dataPoints.size();

        return new DataPoint(res);
    }

    public boolean equals(DataPoint dataPoint)
    {
        boolean equals = true;
        for (int i = 0; i < this.coordinates.length; i++)
            equals &= this.coordinates[i] == dataPoint.coordinates[i];
        return equals;
    }
}
