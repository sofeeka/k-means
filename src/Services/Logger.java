package Services;

public class Logger
{
    private static final boolean logging = true;

    public static void log(String m)
    {
        if(logging)
            System.out.println(m);
    }

    public static void logNoNewLine(String m)
    {
        if(logging)
            System.out.print(m);
    }
}
