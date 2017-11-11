package hr.fer.zemris.fuzzy;

import java.util.Arrays;

public class DomainElement {

    private int[] values;

    public DomainElement(int[] values) {
        this.values = Arrays.copyOf(values, values.length);
    }

    public int getNumberOfComponents(){
        return values.length;
    }

    public int getComponentValue(int index){
        return values[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainElement that = (DomainElement) o;

        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        if (values.length == 0) return "";
        if (values.length == 1) return Integer.toString(values[0]);
        else {
            StringBuilder sb = new StringBuilder();
            sb.append('(');
            for (int i = 0; i < values.length; ++i) {
                sb.append(values[i]);
                sb.append(", ");
            }
            sb.setLength(sb.length()-2);
            sb.append(')');
            return sb.toString();
        }
    }

    public static DomainElement of(int... values) {
        return new DomainElement(values);
    }

}
