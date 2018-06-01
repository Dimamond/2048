import java.awt.*;
import java.math.BigInteger;

public class Tile {
    int value;

    public Tile() {
        this.value = 0;
    }

    public Tile(int value) {
        this.value = value;
    }

    public boolean isEmpty(){
        if(value == 0)return true;
        return false;
    }

    public Color getFontColor(){
        if(value < 16){
            BigInteger bigInteger = new BigInteger("776e65", 16);
            return new Color(Integer.parseInt(bigInteger.toString(10)));
        }else {
            BigInteger bigInteger = new BigInteger("f9f6f2", 16);
            return new Color(Integer.parseInt(bigInteger.toString(10)));
        }
    }

    public Color getTileColor(){
        Color color;
        BigInteger bigInteger;

        switch (value){
            case 0:
                bigInteger = new BigInteger("cdc1b4", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 2:
                bigInteger = new BigInteger("eee4da", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 4:
                bigInteger = new BigInteger("ede0c8", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;
            case 8:
                bigInteger = new BigInteger("f2b179", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 16:
                bigInteger = new BigInteger("f59563", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 32:
                bigInteger = new BigInteger("f67c5f", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 64:
                bigInteger = new BigInteger("f65e3b", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 128:
                bigInteger = new BigInteger("edcf72", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 256:
                bigInteger = new BigInteger("edcc61", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 512:
                bigInteger = new BigInteger("edc850", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 1024:
                bigInteger = new BigInteger("edc53f", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;

            case 2048:
                bigInteger = new BigInteger("edc22e", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;
            default:
                bigInteger = new BigInteger("ff0000", 16);
                color = new Color(Integer.parseInt(bigInteger.toString(10)));
                break;
        }
        return color;

    }


}
