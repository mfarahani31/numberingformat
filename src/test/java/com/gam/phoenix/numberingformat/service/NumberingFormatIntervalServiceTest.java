package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import com.gam.phoenix.spring.commons.dal.DalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@ExtendWith(SpringExtension.class)
class NumberingFormatIntervalServiceTest {

    @InjectMocks
    NumberingFormatIntervalService numberingFormatIntervalService;
    @Mock
    NumberingFormatRepository numberingFormatRepository;
    @Mock
    NumberingFormatIntervalRepository numberingFormatIntervalRepository;

    NumberingFormat anyNumberingFormat;
    NumberingFormat anyValidNumberingFormatStartSerialFrom600;
    NumberingFormatInterval anyNumberingFormatIntervalBetween300And400;
    NumberingFormatInterval anyNumberingFormatIntervalBetween1000And1200;
    NumberingFormatInterval anyValidNumberingFormatIntervalWithNumberingFormat;
    IncreaseRequestModel anyValidIncreaseRequestModelWithReturnTypeSerial;
    IncreaseRequestModel anyValidIncreaseRequestModelWithReturnTypeFull;
    IncreaseRequestModel anyValidIncreaseRequestModelWithNullValue;
    IncreaseRequestModel anyValidIncreaseRequestModelWithInvalidValue;
    Optional<NumberingFormat> anyNumberingFormatOptional;
    Optional<NumberingFormatInterval> anyNumberingFormatIntervalOptional;

    @BeforeEach
    void setup() {
        anyValidNumberingFormatIntervalWithNumberingFormat = MotherObject.getAnyValidNumberingFormatIntervalWithNumberingFormat();
        anyNumberingFormat = MotherObject.getAnyValidNumberingFormat();
        anyNumberingFormatOptional = MotherObject.getAnyOptionalOfValidNumberingFormat();
        anyNumberingFormatIntervalOptional = MotherObject.getAnyOptionalOfValidNumberingFormatInterval();
        anyValidNumberingFormatStartSerialFrom600 = MotherObject.getAnyValidNumberingFormatStartSerialFrom600();
        anyNumberingFormatIntervalBetween300And400 = MotherObject.getAnyValidNumberingFormatIntervalBetween300And400();
        anyValidIncreaseRequestModelWithReturnTypeSerial = MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeSerial();
        anyValidIncreaseRequestModelWithReturnTypeFull = MotherObject.getAnyValidIncreaseRequestModelWithReturnTypeFull();
        anyValidIncreaseRequestModelWithNullValue = MotherObject.getAnyValidIncreaseRequestModelWithNullValue();
        anyValidIncreaseRequestModelWithInvalidValue = MotherObject.getAnyValidIncreaseRequestModelWithInvalidValue();
        anyNumberingFormatIntervalBetween1000And1200 = MotherObject.getAnyValidNumberingFormatIntervalBetween1000And1200();
    }

    @Test
    void testContext() {
        assertNotNull(numberingFormatRepository);
        assertNotNull(numberingFormatIntervalRepository);
        assertNotNull(numberingFormatIntervalService);
    }

    @Test
    @DisplayName("given findByNumberUsageAndNumberFormat when usage and format are valid then returns numberingFormat")
    void given_findByUsageAndFormat_when_usage_and_format_are_valid_then_returns_numberingFormat() throws DalException {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(anyNumberingFormatIntervalBetween300And400);

        //given
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findByNumberingFormatId(anyLong());

        //when
        List<NumberingFormatInterval> numberingFormatIntervals = this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(anyNumberingFormat.getId(), false, 1L);

        //then
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedStart(), numberingFormatIntervals.get(0).getReservedStart());
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedEnd(), numberingFormatIntervals.get(0).getReservedEnd());
        assertEquals(expectedNumberingFormatIntervals.get(0).getNumberingFormat(), numberingFormatIntervals.get(0).getNumberingFormat());

    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when usage and format are valid then returns numberingFormatIntervals")
    void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatId_is_valid_and_justApplicableIntervals_is_true_then_returns_numberingFormatIntervals() throws DalException {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(anyNumberingFormatIntervalBetween300And400);

        //given
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //when
        List<NumberingFormatInterval> numberingFormatIntervals = this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(anyNumberingFormat.getId(), true, 1L);

        //then
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedStart(), numberingFormatIntervals.get(0).getReservedStart());
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedEnd(), numberingFormatIntervals.get(0).getReservedEnd());
        assertEquals(expectedNumberingFormatIntervals.get(0).getNumberingFormat(), numberingFormatIntervals.get(0).getNumberingFormat());
    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when usage and format are valid then returns numberingFormatIntervals")
    void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatId_is_valid_and_serial_is_null_then_returns_numberingFormatIntervals() throws DalException {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
        //List<NumberingFormatInterval> numberingFormatIntervals = new ArrayList<>();
        //given
        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());

        //when
        List<NumberingFormatInterval> numberingFormatIntervals = this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(anyNumberingFormat.getId(), true, null);

        //then
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedStart(), numberingFormatIntervals.get(0).getReservedStart());
        assertEquals(expectedNumberingFormatIntervals.get(0).getReservedEnd(), numberingFormatIntervals.get(0).getReservedEnd());
        assertEquals(expectedNumberingFormatIntervals.get(0).getNumberingFormat(), numberingFormatIntervals.get(0).getNumberingFormat());
    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when usage and format are valid then returns numberingFormatIntervals")
    void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatId_is_valid_and_serial_is_null_then_return_404() {
        List<NumberingFormatInterval> expectedNumberingFormatIntervals = new ArrayList<>();

        doReturn(expectedNumberingFormatIntervals).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());
        doReturn(null).when(numberingFormatIntervalRepository).findByNumberingFormatId(anyLong());

        assertThrows(DalException.class, () -> numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(anyNumberingFormat.getId(), false, 1L));

    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when usage and format are valid then returns numberingFormatIntervals")
    void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatIntervals_is_empty_then_throws_exception() {
        //given
        doReturn(null).when(numberingFormatIntervalRepository).findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(anyLong(), anyLong());
        doReturn(null).when(numberingFormatIntervalRepository).findByNumberingFormatId(anyLong());
        //then
        assertThrows(DalException.class, () -> numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(anyNumberingFormat.getId(), false, 1L));
    }

    @Test
    @DisplayName("given saveNumberingFormatInterval when numberingFormatId and numberingFormatInterval are valid then returns numberingFormatInterval")
    void given_saveNumberingFormatInterval_when_numberingFormatId_and_numberingFormatInterval_are_valid_then_returns_numberingFormatInterval() throws DalException {
        //given
        doReturn(anyNumberingFormatOptional).when(numberingFormatRepository).findById(anyLong());
        doReturn(anyNumberingFormatIntervalBetween300And400).when(numberingFormatIntervalRepository).save(any(NumberingFormatInterval.class));
        //when
        NumberingFormatInterval numberingFormatInterval = this.numberingFormatIntervalService.saveNumberingFormatInterval(anyNumberingFormat.getId(), anyValidNumberingFormatIntervalWithNumberingFormat);
        //then
        assertEquals(anyNumberingFormatIntervalBetween300And400, numberingFormatInterval);
    }

    @Test
    @DisplayName("given saveNumberingFormatInterval when numberingFormatId and numberingFormatInterval are valid then returns numberingFormatInterval")
    void given_saveNumberingFormatInterval_when_numberingFormatId_is_invalid_then_throws_404() {
        //given
        doThrow(RecordNotFoundException.class).when(numberingFormatRepository).findById(anyLong());
        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalRepository).save(any(NumberingFormatInterval.class));
        //then
        assertThrows(InvalidUseOfMatchersException.class, () -> numberingFormatIntervalService.saveNumberingFormatInterval(anyLong(), any(NumberingFormatInterval.class)));
    }

    @Test
    @DisplayName("given deleteNumberingFormatInterval when numberingFormatId and numberingFormatInterval are valid then delete numberingFormatInterval")
    void given_deleteNumberingFormatInterval_when_numberingFormatId_and_reservedIntervalId_are_valid_then_delete_numberingFormatInterval() throws DalException {
        //given
        doReturn(anyNumberingFormatIntervalOptional).when(numberingFormatIntervalRepository).findByIdAndNumberingFormatId(anyLong(), anyLong());
        doNothing().when(numberingFormatIntervalRepository).delete(any(NumberingFormatInterval.class));
        //when
        NumberingFormatInterval numberingFormatInterval = this.numberingFormatIntervalService.deleteNumberingFormatInterval(anyNumberingFormat.getId(), anyValidNumberingFormatIntervalWithNumberingFormat.getId());
        //then
        assertEquals(anyNumberingFormatIntervalBetween300And400, numberingFormatInterval);
    }

}