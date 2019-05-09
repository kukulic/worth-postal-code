package worth.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import worth.model.CalculatedJson;
import worth.model.Outcode;
import worth.model.OutcodeDropdown;
import worth.repository.impl.LogRepositoryImpl;
import worth.repository.impl.OutcodeRepositoryImpl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class OutcodeServiceImplTest {
    private OutcodeRepositoryImpl outcodeRepository;
    private OutcodeServiceImpl outcodeService;
    private Outcode outcodeFirst;
    private Outcode outcodeSecond;
    private List<Outcode> outcodeList;
    private String outcodeNameFirst;
    private String outcodeNameSecond;
    private List<OutcodeDropdown> dropdownOutcodeList;
    private static final String SUCCESSFULLY_UPDATED = "Update sucessfully";
    private static final String SUCCESSFULLY_DELETED = "Delete sucessfully";

    @Before
    public void setUp() {
        LogRepositoryImpl logRepository;

        outcodeRepository = Mockito.mock(OutcodeRepositoryImpl.class);
        logRepository = Mockito.mock(LogRepositoryImpl.class);
        outcodeService = new OutcodeServiceImpl(outcodeRepository, logRepository);

        outcodeNameFirst = "AB10";
        outcodeFirst = Outcode.builder()
                .outcode(outcodeNameFirst)
                .latitude(57.13514)
                .longitude(-2.11731)
                .id(1L)
                .build();

        outcodeNameSecond = "AL10";
        outcodeSecond= Outcode.builder()
                .outcode(outcodeNameSecond)
                .latitude(51.75958)
                .longitude(-0.22920)
                .id(35L)
                .build();

        outcodeList = new ArrayList<>();
        outcodeList.add(outcodeFirst);
        outcodeList.add(outcodeSecond);

        dropdownOutcodeList = new ArrayList<>();
        dropdownOutcodeList.add(OutcodeDropdown.builder()
                .label("AB10")
                .value("1")
                .build());

        dropdownOutcodeList.add(OutcodeDropdown.builder()
                .label("AL10")
                .value("35")
                .build());
    }

    @Test
    public void testGetOutcodes() {

        Mockito.when(outcodeRepository.getOutcode(outcodeFirst.getId())).thenReturn(outcodeFirst);

        Outcode retrivedOutcode = outcodeService.getOutcode(outcodeFirst.getId());

        Assert.assertEquals(outcodeFirst, retrivedOutcode);
    }

    @Test
    public void testGetAllOutcodes() {

        Mockito.when(outcodeRepository.getAllOutcodes()).thenReturn(dropdownOutcodeList);

        List<OutcodeDropdown> retrivedDropdownList = outcodeService.getAllOutcodes();

        Assert.assertEquals(dropdownOutcodeList, retrivedDropdownList);

    }

    @Test
    public void testUpdateOutcode() {

        Mockito.when(outcodeRepository.updateOutcode(outcodeFirst)).thenReturn(SUCCESSFULLY_UPDATED);

        String retrivedUpdateMessage = outcodeService.updateOutcode(outcodeFirst);

        Assert.assertEquals(SUCCESSFULLY_UPDATED, retrivedUpdateMessage);
    }

    @Test
    public void testDeleteOutcode() {

        Mockito.when(outcodeRepository.deleteOutcode(outcodeFirst.getId())).thenReturn(SUCCESSFULLY_DELETED);

        String retrivedDeleteMessage = outcodeService.deleteOutcode(outcodeFirst.getId());

        Assert.assertEquals(SUCCESSFULLY_DELETED, retrivedDeleteMessage);
    }

    @Test
    public void testCalculateDistance() {

        Mockito.when(outcodeRepository.getOutcode(outcodeNameFirst)).thenReturn(outcodeFirst);
        Mockito.when(outcodeRepository.getOutcode(outcodeNameSecond)).thenReturn(outcodeSecond);

        String retrivedDistance = outcodeService.calculateDistance(outcodeNameFirst, outcodeNameSecond);

        Assert.assertEquals("610.011", retrivedDistance);
    }

    @Test
    public void testDitanceFromTwoOutcode() throws Exception {

        Method method = outcodeService.getClass().getDeclaredMethod("ditanceFromTwoOutcode", Outcode.class, Outcode.class);
        method.setAccessible(true);

        BigDecimal retrivedDistance = (BigDecimal) method.invoke(
                outcodeService, outcodeFirst, outcodeSecond);

        Assert.assertEquals(new BigDecimal(610.011).setScale(3, RoundingMode.HALF_UP), retrivedDistance);

    }
}