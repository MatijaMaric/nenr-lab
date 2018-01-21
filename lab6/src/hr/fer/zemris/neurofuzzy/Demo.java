package hr.fer.zemris.neurofuzzy;

public class Demo {

    public static void main(String[] args) {
        ANFIS anfis = new ANFIS(0.001, 10, 1000000);
        anfis.fit(false, true);
    }
}
