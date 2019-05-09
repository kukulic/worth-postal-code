package worth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import worth.constants.Constant;
import worth.model.CalculatedJson;
import worth.model.FullPostcode;
import worth.service.impl.FullPostcodeServiceImpl;

@Slf4j
@RestController
@RequestMapping("api/full-postcode")
public class FullPostcodeController {

    private final FullPostcodeServiceImpl fullPostcodeService;

    public FullPostcodeController(FullPostcodeServiceImpl fullPostcodeService) {
        this.fullPostcodeService = fullPostcodeService;
    }

    @GetMapping("/getFullPostcode/{postcode}")
    public FullPostcode getFullPostcode(@PathVariable(value = "postcode") String postcode) {

        try {
            return fullPostcodeService.getFullPostcode(postcode);
        } catch (EmptyResultDataAccessException e) {
            log.info("Result is empty: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Postcode not found", e);
        }
    }

    @PutMapping("/updateFullPostcode")
    public String updateFullPostcode(@RequestBody FullPostcode fullPostcode) {
        return fullPostcodeService.updateFullPostcode(fullPostcode);
    }


    @DeleteMapping("/deleteFullPostcode/{id}")
    public String deleteOutcode(@PathVariable(value = "id") Long id) {
        return fullPostcodeService.deleteFullPostcode(id);
    }


    @GetMapping("/calculateDistance/{firstFullPostcode}/{secondFullPostcode}")
    public String calculateDistance(@PathVariable(value = "firstFullPostcode") String firstFullPostcode,
                                    @PathVariable(value = "secondFullPostcode") String secondFullPostcode) {
        try {
            return fullPostcodeService.calculateDistance(firstFullPostcode, secondFullPostcode);
        } catch (EmptyResultDataAccessException e) {
            log.info("Result is empty: " + e.getMessage());
            return Constant.POSTCODE_NOT_FOUND;
        }
    }

    /*
        ● For both locations, the postal code, latitude and longitude (both in degrees);
        ● The distance between the two locations (in kilometers);
        ● A fixed string 'unit' that has the value "km";
    */
    @GetMapping("/calculateJsonDocument/{firstFullPostcode}/{secondFullPostcode}")
    public CalculatedJson calculateJsonDocument(@PathVariable(value = "firstFullPostcode") String firstFullPostcode,
                                                @PathVariable(value = "secondFullPostcode") String secondFullPostcode) {
        return fullPostcodeService.calculateJsonDocument(firstFullPostcode, secondFullPostcode);
    }


}
