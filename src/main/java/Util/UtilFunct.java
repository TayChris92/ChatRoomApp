package Util;

import java.awt.*;
import java.util.Random;

public class UtilFunct{

    public static String generateColorCode(){

        Random rand = new Random();

        float r = rand.nextFloat() / 2f + 0.5f;
        float g = rand.nextFloat() / 2f + 0.5f;
        float b = rand.nextFloat() / 2f + 0.5f;

        Color color = new Color(r,g,b);

        return String.valueOf(color.getRGB());
    }

}
