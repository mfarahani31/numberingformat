package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@SpringBootTest
class NumberingFormatIntervalServiceTest {

    @InjectMocks
    NumberingFormatIntervalService numberingFormatIntervalService;
    @Mock
    NumberingFormatRepository numberingFormatRepository;
    @Mock
    NumberingFormatIntervalRepository numberingFormatIntervalRepository;


    @Test
    public void testContext() {
        assertNotNull(numberingFormatRepository);
        assertNotNull(numberingFormatIntervalRepository);
        assertNotNull(numberingFormatIntervalService);
    }

    @Test
    @DisplayName("given findByNumberUsageAndNumberFormat when usage and format are valid then returns numberingFormat")
    public void given_findByUsageAndFormat_when_usage_and_format_are_valid_then_returns_numberingFormat() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatInterval());

        //given
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findByNumberingFormatId(anyLong());

        //when
        List<NumberingFormatInterval> numberingFormatIntervals = this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(MotherObject.getAnyValidNumberingFormat().getId(), false, 1L);

        //then
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedStart(), numberingFormatIntervals.get(0).getReservedStart());
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedEnd(), numberingFormatIntervals.get(0).getReservedEnd());
        assertEquals(expectedNumberingFormatIntervals.get(0).getNumberingFormat(), numberingFormatIntervals.get(0).getNumberingFormat());

    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when usage and format are valid then returns numberingFormatIntervals")
    public void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatId_is_valid_and_justApplicableIntervals_is_true_then_returns_numberingFormatIntervals() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatInterval());

        //given
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //when
        List<NumberingFormatInterval> numberingFormatIntervals = this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(MotherObject.getAnyValidNumberingFormat().getId(), true, 1L);

        //then
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedStart(), numberingFormatIntervals.get(0).getReservedStart());
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedEnd(), numberingFormatIntervals.get(0).getReservedEnd());
        assertEquals(expectedNumberingFormatIntervals.get(0).getNumberingFormat(), numberingFormatIntervals.get(0).getNumberingFormat());
    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when usage and format are valid then returns numberingFormatIntervals")
    public void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatId_is_valid_and_serial_is_null_then_returns_numberingFormatIntervals() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatInterval());

        //given
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //when
        List<NumberingFormatInterval> numberingFormatIntervals = this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(MotherObject.getAnyValidNumberingFormat().getId(), true, null);

        //then
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedStart(), numberingFormatIntervals.get(0).getReservedStart());
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedEnd(), numberingFormatIntervals.get(0).getReservedEnd());
        assertEquals(expectedNumberingFormatIntervals.get(0).getNumberingFormat(), numberingFormatIntervals.get(0).getNumberingFormat());
    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when usage and format are valid then returns numberingFormatIntervals")
    public void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatIntervals_is_empty_then_throws_exception() {
        //given
        doReturn(null).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());
        doReturn(null).when(numberingFormatIntervalRepository).findByNumberingFormatId(anyLong());
        //then
        assertThrows(RecordNotFoundException.class, () -> numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(MotherObject.getAnyValidNumberingFormat().getId(), false, 1L));
    }

    @Test
    @DisplayName("given saveNumberingFormatInterval when numberingFormatId and numberingFormatInterval are valid then returns numberingFormatInterval")
    public void given_saveNumberingFormatInterval_when_numberingFormatId_and_numberingFormatInterval_are_valid_then_returns_numberingFormatInterval() {
        //given
        doReturn(MotherObject.getAnyOptionalOfValidNumberingFormat()).when(numberingFormatRepository).findById(anyLong());
        doReturn(MotherObject.getAnyValidNumberingFormatInterval()).when(numberingFormatIntervalRepository).save(any(NumberingFormatInterval.class));
        //when
        NumberingFormatInterval numberingFormatInterval = this.numberingFormatIntervalService.saveNumberingFormatInterval(MotherObject.getAnyValidNumberingFormat().getId(), MotherObject.getAnyValidNumberingFormatIntervalWithNumberinFormat());
        //then
        assertEquals(MotherObject.getAnyValidNumberingFormatInterval(), numberingFormatInterval);
    }

    @Test
    @DisplayName("given saveNumberingFormatInterval when numberingFormatId and numberingFormatInterval are valid then returns numberingFormatInterval")
    public void given_saveNumberingFormatInterval_when_numberingFormatId_is_invalid_then_throws_404() {
        //given
        doThrow(RecordNotFoundException.class).when(numberingFormatRepository).findById(anyLong());
        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalRepository).save(any(NumberingFormatInterval.class));
        //then
        assertThrows(InvalidUseOfMatchersException.class, () -> numberingFormatIntervalService.saveNumberingFormatInterval(anyLong(), any(NumberingFormatInterval.class)));
    }

    @Test
    @DisplayName("given deleteNumberingFormatInterval when numberingFormatId and numberingFormatInterval are valid then delete numberingFormatInterval")
    public void given_deleteNumberingFormatInterval_when_numberingFormatId_and_reservedIntervalId_are_valid_then_delete_numberingFormatInterval() {
        //given
        doReturn(MotherObject.getAnyOptionalOfValidNumberingFormatInterval()).when(numberingFormatIntervalRepository).findByIdAndNumberingFormatId(anyLong(), anyLong());
        doNothing().when(numberingFormatIntervalRepository).delete(any(NumberingFormatInterval.class));
        //when
        NumberingFormatInterval numberingFormatInterval = this.numberingFormatIntervalService.deleteNumberingFormatInterval(MotherObject.getAnyValidNumberingFormat().getId(), MotherObject.getAnyValidNumberingFormatIntervalWithNumberinFormat().getId());
        //then
        assertEquals(MotherObject.getAnyValidNumberingFormatInterval(), numberingFormatInterval);
    }

}