package hr.fer.zemris.neurofuzzy;

public class Demo {

    public static void main(String[] args) {
        ANFIS anfis = new ANFIS(0.001, 3, 100000);
        anfis.fit(true, true);
        System.out.println("isus");
    }
}
