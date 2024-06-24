package Services;

import java.util.Scanner;

public class KPickerFromUser implements IKPicker
{
    @Override
    public int pickK()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter k");
//        return scanner.nextInt();
        return 3;
    }
}
