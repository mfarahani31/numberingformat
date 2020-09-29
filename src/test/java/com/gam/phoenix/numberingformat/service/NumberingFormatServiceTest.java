package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import com.gam.phoenix.spring.commons.dal.DalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class NumberingFormatServiceTest {

    @InjectMocks
    NumberingFormatService numberingFormatService;
    @Mock
    NumberingFormatRepository numberingFormatRepository;
    @Mock
    NumberingFormatIntervalRepository numberingFormatIntervalRepository;

    NumberingFormat anyNumberingFormat;
    NumberingFormat anyValidNumberingFormatStartSerialFrom600;
    NumberingFormatInterval anyNumberingFormatIntervalBetween300And400;
    NumberingFormatInterval anyNumberingFormatIntervalBetween1000And1200;
    IncreaseRequestModel anyValidIncreaseRequestModelWithReturnTypeSerial;
    IncreaseRequestModel anyValidIncreaseRequestModelWithReturnTypeFull;
    IncreaseRequestModel anyValidIncreaseRequestModelWithNullValue;
    IncreaseRequestModel anyValidIncreaseRequestModelWithInvalidValue;


    @Test
    public void testContext() {
        assertNotNull(numberingFormatRepository);
        assertNotNull(numberingFormatIntervalRepository);
        assertNotNull(numberingFormatService);
    }

    @Test
    @DisplayName("given findByNumberUsageAndNumberFormat when usage and format are valid then returns numberingFormat")
    public void given_findByUsageAndFormat_when_usage_and_format_are_valid_then_returns_numberingFormat() throws DalException {

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
        doThrow(RecordNotFoundException.class).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());
        assertThrows(DalException.class, () -> numberingFormatService.findByUsageAndFormat(anyNumberingFormat.getNumberUsage(),
                anyNumberingFormat.getNumberFormat()));
    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is valid then return numberingFormat")
    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_return_numberingFormat() throws DalException {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).save(any(NumberingFormat.class));

        NumberingFormat numberFormat = numberingFormatService.saveNumberingFormat(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.saveNumberingFormat(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), numberFormat.getNumberUsage());
        assertEquals(MotherObject.getAnyValidNumberingFormat().getNumberFormat(), numberFormat.getNumberFormat());
    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is not valid then return error")
    public void given_saveNumberFormat_when_numberingFormat_is_not_valid_then_return_error() {
        doThrow(DataIntegrityViolationException.class).when(numberingFormatRepository).save(any(NumberingFormat.class));
        assertThrows(DalException.class, () -> numberingFormatService.saveNumberingFormat(MotherObject.getAnyValidNumberingFormat()));
    }

    @Test
    @DisplayName("given findAllNumberingFormats when numberingFormat exist then returns numberingFormats")
    public void given_findAllNumberingFormats_when_numberingFormat_exist_then_returns_numberingFormat() throws DalException {
        //given
        List<NumberingFormat> expectedNumberingFormats = Collections.singletonList(MotherObject.getAnyValidNumberingFormat());
        doReturn(expectedNumberingFormats).when(numberingFormatRepository).findAll();
        // when
        List<NumberingFormat> actualNumberingFormats = numberingFormatService.findAllNumberingFormats();
        //then
        assertEquals(expectedNumberingFormats, actualNumberingFormats);
    }

    @Test
    @DisplayName("given findAllNumberingFormats when not exist then return 500")
    public void given_findAllNumberingFormats_when_numberingFormat_is_not_valid_then_return_error() {
        doThrow(RecordNotFoundException.class).when(numberingFormatRepository).findAll();
        assertThrows(DalException.class, () -> numberingFormatService.findAllNumberingFormats());
    }

    @Test
    @DisplayName("given deleteNumberingFormat when usage and format are valid then delete numberingFormat")
    public void given_deleteNumberingFormat_when_usage_and_format_are_valid_then_delete_numberingFormat() throws DalException {
        doReturn(MotherObject.getAnyValidNumberingFormat().getId()).when(numberingFormatRepository).deleteNumberingFormatByNumberUsageAndNumberFormat(anyString(), anyString());
        numberingFormatService.deleteNumberingFormat("test1", "test1");
        verify(numberingFormatRepository, times(1)).deleteNumberingFormatByNumberUsageAndNumberFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given deleteNumberingFormat when usage and format are invalid then throws exception")
    public void given_deleteNumberingFormat_when_usage_and_format_are_invalid_then_throws_exception() {
        doReturn(0L).when(numberingFormatRepository).deleteNumberingFormatByNumberUsageAndNumberFormat(anyString(), anyString());
        assertThrows(DalException.class, () -> numberingFormatService.deleteNumberingFormat(anyString(), anyString()));
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are valid then return numberingFormat")
    public void given_increaseLastAllocatedSerialByOne_when_numberingFormat_usage_and_format_are_valid_then_return_numberingFormat() throws DalException {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());

        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //doNothing().when(numberingFormatRepository).save(any(NumberingFormat.class));

        String newSerial = numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeSerial());

        assertNotNull(numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeSerial()));
        assertEquals(String.valueOf(401L), newSerial);
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are valid then return serial with format")
    public void given_increaseLastAllocatedSerialByOne_when_numberingFormat_usage_and_format_are_valid_then_return_serial_with_format() throws DalException {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());

        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //doReturn(1).when(numberingFormatRepository).updateLastAllocatedSerial(anyLong(), anyString(), anyString());

        String newSerial = numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeFull());

        assertNotNull(numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeFull()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getNumberFormat() + (MotherObject.getAnyValidNumberingFormatIntervalBetween300And400().getReservedEnd() + 1L), newSerial);
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are valid then return serial with format")
    public void given_increaseLastAllocatedSerialByOne_when_numberingFormat_usage_and_format_are_valid_and_increaseRequestModel_is_null_then_return_serial_with_format() throws DalException {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());

        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //doReturn(1).when(numberingFormatRepository).updateLastAllocatedSerial(anyLong(), anyString(), anyString());

        String newSerial = numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithNullValue());

        assertNotNull(numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeFull()));
        assertEquals(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400().getReservedEnd() + 1L, Long.valueOf(newSerial));
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are valid and update did not work then throws exception")
    public void given_increaseLastAllocatedSerialByOne_when_numberingFormat_usage_and_format_are_valid_and_update_did_not_work_then_throws_exception() {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());

        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        doThrow(DataIntegrityViolationException.class).when(numberingFormatRepository).save(any(NumberingFormat.class));
        //doReturn(0).when(numberingFormatRepository).updateLastAllocatedSerial(anyLong(), anyString(), anyString());

        assertThrows(DalException.class, () -> numberingFormatService.updateLastAllocatedSerial(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), MotherObject.getAnyValidNumberingFormat()));
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are new then save in db and return serial in serial format")
    public void given_increaseLastAllocatedSerialByOne_when_usage_and_format_are_new_then_save_in_db_and_return_serial() throws DalException {
        doReturn(null).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).save(any(NumberingFormat.class));

        String newSerial = numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeSerial());

        assertEquals("1", newSerial);
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are new then save in db and return serial in full format")
    public void given_increaseLastAllocatedSerialByOne_when_usage_and_format_are_new_then_save_in_db_and_return_serial_in_full_format() throws DalException {
        doReturn(null).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).save(any(NumberingFormat.class));

        String newSerial = numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeFull());

        assertEquals("test1001", newSerial);
    }

    @Test
    @DisplayName("given increaseLastAllocatedSerialByOne when usage and format are invalid then throws exception")
    public void given_increaseLastAllocatedSerialByOne_when_increaseRequestValue_is_invalid_then_return_404() throws DalException {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatRepository).findByNumberUsageAndNumberFormat(anyString(), anyString());

        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //doReturn(1).when(numberingFormatRepository).updateLastAllocatedSerial(anyLong(), anyString(), anyString());

        String newSerial = numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithInvalidValue());

        assertNotNull(numberingFormatService.increaseLastAllocatedSerialByOne(MotherObject.getAnyValidNumberingFormat().getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberFormat(), MotherObject.getAnyValidIncreaseRequestModelWithInvalidValue()));
        assertEquals(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400().getReservedEnd() + 1L, Long.valueOf(newSerial));
    }

    @Test
    @DisplayName("given getNextAllocatedSerial when numberingFormat is valid then return nextAllocatedSerial")
    public void given_getNextAllocatedSerial_when_numberingFormat_is_valid_then_return_nextAllocatedSerial() {

        Long nextAllocatedSerial = numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1, nextAllocatedSerial);
    }

    @Test
    @DisplayName("given getNextAllocatedSerial when numberingFormat is valid then return nextAllocatedSerial")
    public void given_getNextAllocatedSerial_when_numberingFormat_is_valid_and_interval_exists_then_return_nextAllocatedSerial() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());

        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(MotherObject.getAnyValidNumberingFormat().getId(), MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1);
        Long nextAllocatedSerial = numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400().getReservedEnd() + 1, nextAllocatedSerial);
    }

    @Test
    @DisplayName("given getNextAllocatedSerial when numberingFormat is valid then return nextAllocatedSerial")
    public void given_getNextAllocatedSerial_when_numberingFormat_is_valid_and_is_not_in_range_then_return_nextAllocatedSerial() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween1000And1200());

        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(MotherObject.getAnyValidNumberingFormat().getId(), MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1);
        Long nextAllocatedSerial = numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1, nextAllocatedSerial);
    }

    @Test
    @DisplayName("given getNextAllocatedSerial when numberingFormat is valid then return nextAllocatedSerial")
    public void given_getNextAllocatedSerial_when_numberingFormat_is_valid_and_interval_not_exists_then_return_nextAllocatedSerial() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = new ArrayList<>();

        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(MotherObject.getAnyValidNumberingFormat().getId(), MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1);
        Long nextAllocatedSerial = numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1, nextAllocatedSerial);
    }

    @Test
    @DisplayName("given getNextAllocatedSerial when numberingFormat is valid then return nextAllocatedSerial")
    public void given_getNextAllocatedSerial_when_numberingFormat_is_valid_and_serial_is_greater_than_range_then_return_nextAllocatedSerial() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
        MotherObject.getAnyValidNumberingFormat().setLastAllocatedSerial(350L);
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(MotherObject.getAnyValidNumberingFormat().getId(), MotherObject.getAnyValidNumberingFormatStartSerialFrom600().getLastAllocatedSerial() + 1);
        Long nextAllocatedSerial = numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat());

        assertNotNull(numberingFormatService.getNextValidAllocatedSerial(MotherObject.getAnyValidNumberingFormat()));
        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1, nextAllocatedSerial);
    }
}