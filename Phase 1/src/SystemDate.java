public class SystemDate extends Day {
    private static SystemDate instance;
    private static String s_day;
    private SystemDate(String sDay) { super(sDay); }
    public static SystemDate getInstance(){ 
        return instance;
    }

    public static String getSDay() {
        return s_day;
    }

    public static void createTheInstance(String sDay ) {
        instance = new SystemDate(sDay);
        s_day = sDay;
    }
}