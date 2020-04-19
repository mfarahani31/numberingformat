package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class NumberingFormatServiceTest {

    @InjectMocks
    NumberingFormatService numberingFormatService;
    @Mock
    NumberingFormatRepository numberingFormatRepository;

    @Mock
    NumberingFormatIntervalRepository numberingFormatIntervalRepository;


    @Test
    public void testContext() {
        assertNotNull(numberingFormatRepository);
        assertNotNull(numberingFormatService);
    }

    @Test
    @DisplayName("given findByNumberUsageAndNumberFormat when usage and format are valid then returns numberingFormat")
    public void given_findByUsageAndFormat_when_usage_and_format_are_valid_then_returns_numberingFormat() {

        //given
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());

        //when
        NumberingFormat numberingFormat = numberingFormatService.findByUsageAndFormat("test1", "test1");

        //then
        assertEquals(numberingFormat.getLastAllocatedSerial(), MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial());
        assertEquals(numberingFormat.getStartAt(), MotherObject.getAnyValidNumberingFormat().getStartAt());
        assertEquals(numberingFormat.getNumberFormat(), MotherObject.getAnyValidNumberingFormat().getNumberFormat());
        assertEquals(numberingFormat.getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberUsage());
    }

    @Test
    @DisplayName("given findByNumberUsageAndNumberFormat when usage and format are invalid then throws exception")
    public void given_findByNumberUsageAndNumberFormat_when_usage_and_format_are_invalid_then_throws_exception() {
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());
        assertThrows(BusinessException.class, () -> numberingFormatService.findByUsageAndFormat("invalidUsage", "invalidFormat"));
    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is valid then return numberingFormat")
    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_return_numberingFormat() throws BusinessException {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).save(any(NumberingFormat.class));

        NumberingFormat numberFormat = numberingFormatService.saveNumberingFormat(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.saveNumberingFormat(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), numberFormat.getNumberUsage());
    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is not valid then return error")
    public void given_saveNumberFormat_when_numberingFormat_is_not_valid_then_return_error() {
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatRepository).save(any(NumberingFormat.class));
        assertThrows(BusinessException.class, () -> numberingFormatService.saveNumberingFormat(MotherObject.getAnyValidNumberingFormat()));
    }

    @Test
    @DisplayName("given findAllNumberingFormats when numberingFormat exist then returns numberingFormats")
    public void given_findAllNumberingFormats_when_numberingFormat_exist_then_returns_numberingFormat() {
        //given
        List<NumberingFormat> expectedNumberingFormats = Collections.singletonList(MotherObject.getAnyValidNumberingFormat());
        doReturn(expectedNumberingFormats).when(numberingFormatRepository).findAll();
        // when
        List<NumberingFormat> actualNumberingFormats = numberingFormatService.findAllNumberingFormats();
        //then
        assertEquals(expectedNumberingFormats, actualNumberingFormats);
    }

    @Test
    @DisplayName("given findAllNumberingFormats when numberingFormat not exist then throws exception")
    public void given_findAllNumberingFormats_when_numberingFormat_not_exist_then_throws_exception() {
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatRepository).findAll();
        assertThrows(BusinessException.class, () -> numberingFormatService.findAllNumberingFormats());
    }

    @Test
    @DisplayName("given deleteNumberingFormat when usage and format are valid then delete numberingFormat")
    public void given_deleteNumberingFormat_when_usage_and_format_are_valid_then_delete_numberingFormat() {
        doReturn(MotherObject.getAnyValidNumberingFormat().getId()).when(numberingFormatRepository).deleteNumberingFormatByNumberUsageAndNumberFormat(anyString(), anyString());
        numberingFormatService.deleteNumberingFormat("test1", "test1");
        verify(numberingFormatRepository, times(1)).deleteNumberingFormatByNumberUsageAndNumberFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given deleteNumberingFormat when usage and format are invalid then throws exception")
    public void given_deleteNumberingFormat_when_usage_and_format_are_invalid_then_throws_exception() {
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatRepository).deleteNumberingFormatByNumberUsageAndNumberFormat(anyString(), anyString());
        assertThrows(Exception.class, () -> numberingFormatService.deleteNumberingFormat(anyString(), anyString()));
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are valid then return numberingFormat")
    public void given_increaseLastAllocatedSerialByOne_when_numberingFormat_usage_and_format_are_valid_then_return_numberingFormat() throws BusinessException {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());

        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatInterval());
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        doNothing().when(numberingFormatRepository).updateLastAllocatedSerial(anyLong(), anyString(), anyString());

        String newSerial = numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModel());

        assertNotNull(numberingFormatService.increaseLastAllocatedSerialByOne("test1", "test1", MotherObject.getAnyValidIncreaseRequestModel()));
        assertEquals(String.valueOf(401L), newSerial);
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are invalid then throws exception")
    public void given_increaseLastAllocatedSerialByOne_when_usage_and_format_are_invalid_then_throws_exception() {
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatRepository).updateLastAllocatedSerial(anyLong(), anyString(), anyString());

        assertThrows(Exception.class, () -> numberingFormatService.increaseLastAllocatedSerialByOne(anyString(), anyString(), any(IncreaseRequestModel.class)));
    }

    @Test
    @DisplayName("given getNextAllocatedSerial when numberingFormat is valid then return nextAllocatedSerial")
    public void given_getNextAllocatedSerial_when_numberingFormat_is_valid_then_return_nextAllocatedSerial() {

        Long nextAllocatedSerial = numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1, nextAllocatedSerial);
    }
}