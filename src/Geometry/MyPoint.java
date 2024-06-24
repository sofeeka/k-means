import Exceptions.WrongNumberOfCoordinates;

public class MyPoint
{
    private double[] coordinates;

    public double findDistanceTo(MyPoint point)
    {
        if(point.coordinates.length != this.coordinates.length)
            throw new WrongNumberOfCoordinates(point.coordinates.length + " and " + this.coordinates.length + " should be equal");

        double len = 0;
        for (int i = 0; i < coordinates.length; i++)
            len += Math.pow(this.coordinates[i] + point.coordinates[i], 2);

        return len;
    }

    public double[] getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates)
    {
        this.coordinates = coordinates;
    }
}
