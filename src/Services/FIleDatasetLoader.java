package DataLoaders;

import Geometry.DataPoint;

import java.util.ArrayList;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(fileName);

        String line;
        while((line = scanner.nextLine()) != null)
            dataPoints.add(getDataPoint(line));

        return dataPoints;
    }

    private DataPoint getDataPoint(String line)
    {

    }
}
