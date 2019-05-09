package worth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import worth.constants.Constant;
import worth.model.Outcode;
import worth.model.OutcodeDropdown;
import worth.service.impl.OutcodeServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/outcode")
public class OutcodeController {

    private final OutcodeServiceImpl outcodeService;

    public OutcodeController(OutcodeServiceImpl outcodeService) {
        this.outcodeService = outcodeService;
    }

    @GetMapping("/getOutcode/{id}")
    public Outcode getOutcodes(@PathVariable(value = "id") Long id) {
        return outcodeService.getOutcode(id);
    }


    @GetMapping("/getAllOutcodes")
    public List<OutcodeDropdown> getAllOutcodes() {
        return outcodeService.getAllOutcodes();
    }


    @PutMapping("/updateOutcode")
    public String updateOutcodes(@RequestBody Outcode outcode) {
        return outcodeService.updateOutcode(outcode);
    }


    @DeleteMapping("/deleteOutcode/{id}")
    public String deleteOutcode(@PathVariable(value = "id") Long id) {
        return outcodeService.deleteOutcode(id);
    }


    @GetMapping("/calculateDistance/{firstOutcode}/{secondOutcode}")
    public String calculateDistance(@PathVariable(value = "firstOutcode") String firstOutcode,
                                        @PathVariable(value = "secondOutcode") String secondOutcode) {
        try {
            return outcodeService.calculateDistance(firstOutcode, secondOutcode);
        } catch (EmptyResultDataAccessException e) {
            log.info("Result is empty: " + e.getMessage());
            return Constant.POSTCODE_NOT_FOUND;
        }
    }
}
