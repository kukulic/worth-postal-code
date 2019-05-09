package worth.repository;

public interface LogRepository {

    int insertLog(String firstPostCode, String secondPostCode, double distance);

}
