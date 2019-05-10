package worth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import worth.constants.Constant;
import worth.model.CalculatedJson;
import worth.model.FullPostcode;
import worth.model.PostalcodeDegree;
import worth.repository.impl.FullPostcodeRepositoryImpl;
import worth.repository.impl.LogRepositoryImpl;
import worth.service.FullPostcodeService;
import worth.util.ConvertToDegree;
import worth.util.Haversine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FullPostcodeServiceImpl implements FullPostcodeService {

    private final static String DISTANCE_FOUND = "Distance between two post offices is %.3f km";

    // maximum is number of decimal places in database - right now is 3
    private final static int DECIMAL_PLACES_NUMBER = 3;

    private final FullPostcodeRepositoryImpl fullPostcodeRepository;
    private final LogRepositoryImpl logRepository;

    public FullPostcodeServiceImpl(FullPostcodeRepositoryImpl fullPostcodeRepository,
                                   LogRepositoryImpl logRepository) {
        this.fullPostcodeRepository = fullPostcodeRepository;
        this.logRepository = logRepository;
    }

    @Override
    public FullPostcode getFullPostcode(String postcode) throws EmptyResultDataAccessException {
        return fullPostcodeRepository.getFullPostcode(postcode);
    }

    @Override
    public String updateFullPostcode(FullPostcode fullPostcode) {
        return fullPostcodeRepository.updateFullPostcode(fullPostcode);
    }

    @Override
    public String deleteFullPostcode(Long id) {
        return fullPostcodeRepository.deleteFullPostcode(id);
    }

    @Override
    @Transactional
    public String calculateDistance(String firstFullPostcode, String secondFullPostcode) throws EmptyResultDataAccessException {

        List<FullPostcode> fullPostcodeList = getBothFullPostcode(firstFullPostcode, secondFullPostcode);
        double distance = ditanceFromTwoPostal(fullPostcodeList).doubleValue();

        logRepository.insertLog(firstFullPostcode, secondFullPostcode, distance);

        return String.format(Locale.UK, DISTANCE_FOUND, distance);
    }

    @Override
    @Transactional
    public CalculatedJson calculateJsonDocument(String firstFullPostcode, String secondFullPostcode) {

        try {
            List<FullPostcode> fullPostcodeList = getBothFullPostcode(firstFullPostcode, secondFullPostcode);
            return CalculatedJson.builder()
                    .postalcodeList(fullPostcodeList.stream()
                            .map(fullPostcode -> {
                                return PostalcodeDegree.builder()
                                        .postalcode(fullPostcode.getPostcode())
                                        .degree(ConvertToDegree.convert(fullPostcode.getLatitude(), fullPostcode.getLongitude()))
                                        .build();
                            })
                            .collect(Collectors.toList()))
                    .distance(ditanceFromTwoPostal(fullPostcodeList))
                    .build();

        } catch (EmptyResultDataAccessException e) {
            log.info("Postcode not found in DB: " + e.getMessage());
            return CalculatedJson.builder()
                    .error(Constant.POSTCODE_NOT_FOUND)
                    .build();
        }
    }

    private List<FullPostcode> getBothFullPostcode(String firstFullPostcode, String secondFullPostcode) throws EmptyResultDataAccessException {

        List<FullPostcode> fullPostcodeList = new ArrayList<>();

        fullPostcodeList.add(fullPostcodeRepository.getFullPostcode(firstFullPostcode));
        fullPostcodeList.add(fullPostcodeRepository.getFullPostcode(secondFullPostcode));

        return fullPostcodeList;
    }

    private BigDecimal ditanceFromTwoPostal(List<FullPostcode> fullPostcodeList) {
        return new BigDecimal(Haversine.distance(fullPostcodeList.get(0).getLatitude(), fullPostcodeList.get(0).getLongitude(),
                fullPostcodeList.get(1).getLatitude(), fullPostcodeList.get(1).getLongitude())).setScale(DECIMAL_PLACES_NUMBER, RoundingMode.HALF_UP);
    }

}
