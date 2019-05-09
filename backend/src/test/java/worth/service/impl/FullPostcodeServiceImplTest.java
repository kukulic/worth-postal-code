package worth.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import worth.constants.Constant;
import worth.model.CalculatedJson;
import worth.model.FullPostcode;
import worth.model.PostalcodeDegree;
import worth.repository.impl.FullPostcodeRepositoryImpl;
import worth.repository.impl.LogRepositoryImpl;
import worth.util.ConvertToDegree;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class FullPostcodeServiceImplTest {
    private static final String SUCCESSFULLY_UPDATED = "Update sucessfully";
    private static final String SUCCESSFULLY_DELETED = "Delete sucessfully";
    private static final String POSTCAODE_NOT_EXIST = "NO_EXIST";

    private FullPostcodeRepositoryImpl fullPostcodeRepository;
    private FullPostcodeServiceImpl fullPostcodeService;
    private FullPostcode fullPostcodeFirst;
    private FullPostcode fullPostcodeSecond;
    private List<FullPostcode> fullPostcodeList;
    private String postcodeNameFirst;
    private String postcodeNameSecond;
    private CalculatedJson calculatedJson;
    private CalculatedJson calculatedJsonError;

    @Before
    public void setUp() {
        LogRepositoryImpl logRepository;
        List<PostalcodeDegree> postalcodeDegreeList;

        fullPostcodeRepository = Mockito.mock(FullPostcodeRepositoryImpl.class);
        logRepository = Mockito.mock(LogRepositoryImpl.class);
        fullPostcodeService = new FullPostcodeServiceImpl(fullPostcodeRepository, logRepository);

        postcodeNameFirst = "AB10 1XG";
        fullPostcodeFirst = FullPostcode.builder()
                .postcode(postcodeNameFirst)
                .latitude(57.144165160000000)
                .longitude(-2.114847768000000)
                .id(1L)
                .build();

        postcodeNameSecond = "AB10 6RN";
        fullPostcodeSecond= FullPostcode.builder()
                .postcode(postcodeNameSecond)
                .latitude(57.137879760000000)
                .longitude(-2.121486688000000)
                .id(2L)
                .build();

        fullPostcodeList = new ArrayList<>();
        fullPostcodeList.add(fullPostcodeFirst);
        fullPostcodeList.add(fullPostcodeSecond);


        postalcodeDegreeList = new ArrayList<>();
        postalcodeDegreeList.add(PostalcodeDegree.builder()
                .postalcode(fullPostcodeFirst.getPostcode())
                .degree(ConvertToDegree.convert(fullPostcodeFirst.getLatitude(), fullPostcodeFirst.getLongitude()))
                .build());

        postalcodeDegreeList.add(PostalcodeDegree.builder()
                .postalcode(fullPostcodeSecond.getPostcode())
                .degree(ConvertToDegree.convert(fullPostcodeSecond.getLatitude(), fullPostcodeSecond.getLongitude()))
                .build());

        calculatedJson = CalculatedJson.builder()
                .distance(new BigDecimal(0.806).setScale(3, RoundingMode.HALF_UP))
                .postalcodeList(postalcodeDegreeList)
                .build();

        calculatedJsonError = CalculatedJson.builder()
                .error(Constant.POSTCODE_NOT_FOUND)
                .build();
    }

    @Test
    public void testGetFullPostcode() {

        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameFirst)).thenReturn(fullPostcodeFirst);

        FullPostcode retrivedFullPostcode = fullPostcodeService.getFullPostcode(postcodeNameFirst);

        Assert.assertEquals(fullPostcodeFirst, retrivedFullPostcode);
    }

    @Test
    public void testUpdateFullPostcode() {

        Mockito.when(fullPostcodeRepository.updateFullPostcode(fullPostcodeFirst)).thenReturn(SUCCESSFULLY_UPDATED);

        String retrivedUpdateMessage = fullPostcodeService.updateFullPostcode(fullPostcodeFirst);

        Assert.assertEquals(SUCCESSFULLY_UPDATED, retrivedUpdateMessage);
    }

    @Test
    public void testDeleteFullPostcode() {

        Mockito.when(fullPostcodeRepository.deleteFullPostcode(fullPostcodeFirst.getId())).thenReturn(SUCCESSFULLY_DELETED);

        String retrivedDeleteMessage = fullPostcodeService.deleteFullPostcode(fullPostcodeFirst.getId());

        Assert.assertEquals(SUCCESSFULLY_DELETED, retrivedDeleteMessage);
    }

    @Test
    public void testGetBothFullPostcode() throws Exception {

        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameFirst)).thenReturn(fullPostcodeFirst);
        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameSecond)).thenReturn(fullPostcodeSecond);

        Method method = fullPostcodeService.getClass().getDeclaredMethod("getBothFullPostcode", String.class, String.class);
        method.setAccessible(true);

        List<FullPostcode> retrivedPostcodeList = (List<FullPostcode>) method.invoke(
                fullPostcodeService, postcodeNameFirst, postcodeNameSecond);

        Assert.assertEquals(fullPostcodeList, retrivedPostcodeList);
    }

    @Test
    public void testDitanceFromTwoPostal() throws Exception {

        Method method = fullPostcodeService.getClass().getDeclaredMethod("ditanceFromTwoPostal", List.class);
        method.setAccessible(true);

        BigDecimal retrivedDistance = (BigDecimal) method.invoke(
                fullPostcodeService, fullPostcodeList);

        // distance taken from - https://www.freemaptools.com/distance-between-uk-postcodes.htm
        Assert.assertEquals(new BigDecimal(0.806).setScale(3, RoundingMode.HALF_UP), retrivedDistance);
    }

    @Test
    public void testCalculateDistance() {

        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameFirst)).thenReturn(fullPostcodeFirst);
        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameSecond)).thenReturn(fullPostcodeSecond);

        String retrivedDistance = fullPostcodeService.calculateDistance(postcodeNameFirst, postcodeNameSecond);

        // distance taken from - https://www.freemaptools.com/distance-between-uk-postcodes.htm
        Assert.assertEquals("Distance between two post offices is 0.806 km", retrivedDistance);
    }

    @Test
    public void testCalculateJsonDocument() {

        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameFirst)).thenReturn(fullPostcodeFirst);
        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameSecond)).thenReturn(fullPostcodeSecond);

        CalculatedJson retrivedCalculatedJson = fullPostcodeService.calculateJsonDocument(postcodeNameFirst, postcodeNameSecond);

        // distance taken from - https://www.freemaptools.com/distance-between-uk-postcodes.htm
        Assert.assertEquals(calculatedJson, retrivedCalculatedJson);
    }

    @Test
    public void testCalculateJsonDocumentError() {

        Mockito.when(fullPostcodeRepository.getFullPostcode(postcodeNameFirst)).thenReturn(fullPostcodeFirst);
        Mockito.when(fullPostcodeRepository.getFullPostcode(POSTCAODE_NOT_EXIST)).thenThrow(EmptyResultDataAccessException.class);

        CalculatedJson retrivedCalculatedJson = fullPostcodeService.calculateJsonDocument(postcodeNameFirst, POSTCAODE_NOT_EXIST);

        Assert.assertEquals(calculatedJsonError, retrivedCalculatedJson);
    }
}