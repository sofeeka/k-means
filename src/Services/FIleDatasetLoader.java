package Services;

import Geometry.DataPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FIleDatasetLoader implements IDatasetLoader
{
    private final String fileName;

    public FIleDatasetLoader(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public ArrayList<DataPoint> load()
    {
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                dataPoints.add(getDataPoint(line));

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return dataPoints;
    }

    private DataPoint getDataPoint(String line)
    {
        String[] split = line.split(",");
        double[] attributes = new double[split.length - 1];

        for (int i = 0; i < split.length - 1; i++)
            attributes[i] = Double.parseDouble(split[i].trim());

        return new DataPoint(attributes);
    }
}
