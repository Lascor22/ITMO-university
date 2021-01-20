import org.jetbrains.annotations.NotNull;

/**
 * В теле класса решения разрешено использовать только финальные переменные типа RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 *
 * @author :TODO: Karimov Azat
 */
public class Solution implements MonotonicClock {
    private final RegularInt c1 = new RegularInt(0);
    private final RegularInt c2 = new RegularInt(0);
    private final RegularInt c3 = new RegularInt(0);
    private final RegularInt a1 = new RegularInt(0);
    private final RegularInt a2 = new RegularInt(0);


    @Override
    public void write(@NotNull Time time) {
        c1.setValue(time.getD1());
        c2.setValue(time.getD2());
        c3.setValue(time.getD3());
        a2.setValue(time.getD2());
        a1.setValue(time.getD1());
    }

    @NotNull
    @Override
    public Time read() {
        int a1V = a1.getValue();
        int a2V = a2.getValue();
        int c3V = c3.getValue();
        int c2V = c2.getValue();
        int c1V = c1.getValue();
        int d1 = c1V;
        int d2 = a1V == c1V ? c2V : 0;
        int d3 = a1V == c1V && a2V == c2V ? c3V : 0;
        return new Time(d1, d2, d3);
    }
}
