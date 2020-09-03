package com.ua.foxminded.university.services;

import com.ua.foxminded.university.dao.impl.LectorDaoImplDao;
import com.ua.foxminded.university.exceptions.ServiceException;
import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LectorServicesTest {
    @Mock
    private LectorDaoImplDao lectorDao;

    @InjectMocks
    private ValidatorEntity<Lector> validator;

    @InjectMocks
    private LectorServices lectorServices;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(lectorServices, "validator", validator);
    }

    @Test
    public void getAllShouldGetAllLectors() {
        List<Lector> initialLectors = new ArrayList<>();
        Lector testLector1 = new Lector(1L, 1L, "Andrey", "Borisov");
        Lector testLector2 = new Lector(2L, 2L, "Boris", "Andeyes");
        Lector testLector3 = new Lector(2L, 2L, "Andress", "Larensov");

        initialLectors.add(testLector1);
        initialLectors.add(testLector2);
        initialLectors.add(testLector3);

        when(lectorDao.getAll()).thenReturn(initialLectors);

        List<Lector> lectors = lectorServices.getAllLight();

        assertEquals(3, lectors.size());
        verify(lectorDao, times(1)).getAll();
    }

    @Test
    public void getByIdShouldGetByIdLector() {
        when(lectorDao.getById(1L)).thenReturn(new Lector(1L, 1L, "Andrey", "Borisov"));

        Lector lector = lectorServices.getByIdLight(1L);

        assertEquals("Andrey", lector.getFirstName());
        assertEquals("Borisov", lector.getLastName());
        assertEquals(1, lector.getLectorId());
    }

    @Test
    public void createShouldCreateLector() {
        Lector lector = (new Lector(1L, 1L, "Andrey", "Borisov"));

        when(lectorDao.create(eq(lector))).thenReturn(Boolean.TRUE);

        boolean isCreated = lectorServices.create(lector);

        verify(lectorDao, times(1)).create(lector);
        assertTrue(isCreated);
    }

    @Test
    public void deleteShouldDeleteLector() {

        when(lectorDao.delete(eq(1L))).thenReturn(Boolean.TRUE);

        boolean isDeleted = lectorServices.delete(1L);

        assertTrue(isDeleted);
        verify(lectorDao, times(1)).delete(1L);
    }

    @Test
    public void updateShouldUpdateLector() {
        Lector lector = (new Lector(1L, 1L, "Andrey", "Borisov"));
        when(lectorDao.update(eq(lector))).thenReturn(Boolean.TRUE);

        boolean isUpdated = lectorServices.update(lector);

        verify(lectorDao, times(1)).update(lector);
        assertTrue(isUpdated);
    }

    @Test
    public void createShouldThrowServiceExceptionWhenNameNull() {
        Lector lector = new Lector(1L, null, "Barabas");

        assertThrows(ServiceException.class, () -> lectorServices.create(lector));
    }

    @Test
    public void createShouldThrowServiceExceptionWhenSurnameNull() {
        Lector lector = new Lector(1L, "Ivan", null);

        assertThrows(ServiceException.class, () -> lectorServices.create(lector));
    }

    @Test
    public void createShouldThrowServiceExceptionWhenNameTooShort() {
        Lector lector = new Lector(1L, "V", "Vasiko");

        assertThrows(ServiceException.class, () -> lectorServices.create(lector));
    }

    @Test
    public void createShouldThrowServiceExceptionWhenSurnameTooShort() {
        Lector lector = new Lector(1L, "Saas", "V");

        assertThrows(ServiceException.class, () -> lectorServices.create(lector));
    }

    @Test
    public void updateShouldThrowServiceExceptionWhenIdZero() {
        Lector lector = new Lector(0L, "Saass", "Vaaas");
        assertThrows(ServiceException.class, () -> lectorServices.update(lector));
    }

    @Test
    public void updateShouldThrowServiceExceptionWhenNameHaveForbiddenSymbol() {
        Lector lector = new Lector(0L, "Saass_", "Vaaas");
        assertThrows(ServiceException.class, () -> lectorServices.update(lector));
    }

    @Test
    public void updateShouldThrowServiceExceptionWhenSurnameNameHaveForbiddenSymbol() {
        Lector lector = new Lector(0L, "Saass", "Vaa*as");
        assertThrows(ServiceException.class, () -> lectorServices.update(lector));
    }

    @Test
    public void updateShouldThrowServiceExceptionWhenNameIsTooLong() {
        Lector lector = new Lector(0L, "Saasssssssssssssssssssssssssssssssssssssssssssssss", "Vaaass");
        assertThrows(ServiceException.class, () -> lectorServices.update(lector));
    }

    @Test
    public void updateShouldThrowServiceExceptionWhenSurnameIsTooLong() {
        Lector lector = new Lector(0L, "Saass", "Vaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaas");
        assertThrows(ServiceException.class, () -> lectorServices.update(lector));
    }
}

