package worth.util;


public class ConvertToDegree {

    public static String convert(double latitude, double longitude) {

        int latSeconds = (int) Math.round(latitude * 3600);
        int latDegrees = latSeconds / 3600;
        latSeconds = Math.abs(latSeconds % 3600);
        int latMinutes = latSeconds / 60;
        latSeconds %= 60;

        int lngSeconds = (int) Math.round(longitude * 3600);
        int lngDegrees = lngSeconds / 3600;
        lngSeconds = Math.abs(lngSeconds % 3600);
        int lngMinutes = lngSeconds / 60;
        lngSeconds %= 60;

        String latDegree = latDegrees >= 0 ? "N" : "S";
        String lngDegree = lngDegrees >= 0 ? "E" : "W";

        return  Math.abs(latDegrees) + "°" + latMinutes + "'" + latSeconds
                + "\"" + latDegree +" "+ Math.abs(lngDegrees) + "°" + lngMinutes
                + "'" + lngSeconds + "\"" + lngDegree;
    }
}
