package com.nnk.springboot.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

public class CurvePointServiceTest {
	
	@Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;
    
    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        curvePoint = new CurvePoint();
        curvePoint.setCurvePointId(1);
        curvePoint.setValue(9d);
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
    }
    
    @Test
    void testConvertOptionalToCurvePointWithNonNullValue() {
        //Arrange
        Optional<CurvePoint> optionalCurvePoint = Optional.of(curvePoint);

        //Act
        CurvePoint result = curvePointService.convertOptionalToCurvePoint(optionalCurvePoint);

        //Assert
        assertEquals(curvePoint, result);
    }

    @Test
    void testConvertOptionalToCurvePointWithNullValue() {
        //Arrange
        Optional<CurvePoint> optionalCurvePoint = Optional.empty();

        //Act
        CurvePoint result = curvePointService.convertOptionalToCurvePoint(optionalCurvePoint);

        //Assert
        assertNull(result);
    }
    
    @Test
    void testGetCurvePointById() {
        //Arrange
        Optional<CurvePoint> optionalCurvePoint = Optional.ofNullable(curvePoint);
    	when(curvePointRepository.findById(anyInt())).thenReturn(optionalCurvePoint);
    	
    	//Act
    	CurvePoint result = curvePointService.getCurvePointById(1);

        //Assert
    	verify(curvePointRepository).findById(1);
    	assertEquals(curvePoint, result);
    	
    }
    
    
    
    @Test
    void testUpdateCurvePoint() {
        //Act
        CurvePoint updatedCurvePoint = curvePointService.updateCurvePoint(curvePoint);

        //Assert
        verify(curvePointRepository).save(curvePoint);
        assertEquals(9d, updatedCurvePoint.getValue());
        
    }

    @Test
    void testDeleteCurvePoint() {
        //Act
    	curvePointService.deleteCurvePoint(1);

        //Assert
    	verify(curvePointRepository).deleteById(1);
    }
    
    @Test
    void testGetAllCurvePoints() {
        // Arrange
        CurvePoint curvePoint1 = new CurvePoint();
        curvePoint1.setCurvePointId(1);
        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint2.setCurvePointId(2);
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint1, curvePoint2);
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        // Act
        List<CurvePoint> result = curvePointService.getAllCurvePoints();

        // Assert
        assertEquals(curvePoints.size(), result.size());
        assertEquals(curvePoints.get(0), result.get(0));
        assertEquals(curvePoints.get(1), result.get(1));
    }

    @Test
    void testAddCurvePoint() {
        // Arrange
        CurvePoint newCurvePoint = new CurvePoint();
        newCurvePoint.setCurvePointId(2);
        newCurvePoint.setCurveId(8);
        newCurvePoint.setTerm(77d);
        newCurvePoint.setValue(88d);
        when(curvePointRepository.save(newCurvePoint)).thenReturn(newCurvePoint);

        // Act
        CurvePoint addedCurvePoint = curvePointService.addCurvePoint(newCurvePoint);

        // Assert
        verify(curvePointRepository).save(newCurvePoint);
        assertEquals(newCurvePoint, addedCurvePoint);
    }
	

}
