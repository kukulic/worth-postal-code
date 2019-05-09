package worth.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import worth.model.Outcode;
import worth.model.OutcodeDropdown;
import worth.repository.impl.LogRepositoryImpl;
import worth.repository.impl.OutcodeRepositoryImpl;
import worth.service.OutcodeService;
import worth.util.Haversine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OutcodeServiceImpl implements OutcodeService {

    // maximum is number of decimal places in database - right now is 3
    private final static int DECIMAL_PLACES_NUMBER = 3;

    private final OutcodeRepositoryImpl outcodeRepository;
    private final LogRepositoryImpl logRepository;

    public OutcodeServiceImpl(OutcodeRepositoryImpl outcodeRepository,
                              LogRepositoryImpl logRepository) {
        this.outcodeRepository = outcodeRepository;
        this.logRepository = logRepository;
    }

    @Override
    public Outcode getOutcode(Long id) {
        return outcodeRepository.getOutcode(id);
    }

    @Override
    public List<OutcodeDropdown> getAllOutcodes() {
        return outcodeRepository.getAllOutcodes();
    }

    @Override
    public String updateOutcode(Outcode outcode) {
        return outcodeRepository.updateOutcode(outcode);
    }

    @Override
    public String deleteOutcode(Long id) {
        return outcodeRepository.deleteOutcode(id);
    }

    @Override
    @Transactional
    public String calculateDistance(String firstOutcode, String secondOutcode) throws EmptyResultDataAccessException {

        double distance = ditanceFromTwoOutcode(outcodeRepository.getOutcode(firstOutcode), outcodeRepository.getOutcode(secondOutcode)).doubleValue();

        logRepository.insertLog(firstOutcode, secondOutcode, distance);

        return String.format("%.3f", distance);
    }

    private BigDecimal ditanceFromTwoOutcode(Outcode first, Outcode second) {
        return new BigDecimal(Haversine.distance(first.getLatitude(), first.getLongitude(),
                second.getLatitude(), second.getLongitude())).setScale(DECIMAL_PLACES_NUMBER, RoundingMode.HALF_UP);

    }
}
