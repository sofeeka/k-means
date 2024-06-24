package Exceptions;

public class WrongNumberOfCoordinates extends RuntimeException
{
    public WrongNumberOfCoordinates(String message)
    {
        super(message);
    }
}
