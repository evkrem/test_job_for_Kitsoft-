package kitsoft;

import org.junit.Test;

import java.util.Formatter;

public class Temp {
    @Test
    public void testTask() {
        Formatter f = new Formatter();
        String z = "bookk";
        f.format("This %s is about ", z);
        System.out.print(f);

    }
}
