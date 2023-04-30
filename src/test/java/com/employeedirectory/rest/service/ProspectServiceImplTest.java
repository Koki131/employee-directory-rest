package com.employeedirectory.rest.service;

import com.employeedirectory.rest.entity.Prospect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProspectServiceImplTest {

    @Mock
    @Autowired
    private ProspectService prospectServiceMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProspect() {

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");

        when(prospectServiceMock.getProspect(0L)).thenReturn(prospect);

        Prospect actualProspect = prospectServiceMock.getProspect(0L);

        assertEquals(prospect, actualProspect);

    }

    @Test
    void testSaveProspect() {

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");

        prospectServiceMock.saveProspect(prospect);

        verify(prospectServiceMock).saveProspect(prospect);

    }

    @Test
    void testDeleteProspect() {

        prospectServiceMock.deleteProspect(0L);

        verify(prospectServiceMock).deleteProspect(0L);

    }

    @Test
    void testFindAllProspectsByKeyword() {

        List<Prospect> prospects = List.of(new Prospect("John Smith", "test123@gmail.com"));

        when(prospectServiceMock.findAllProspectsByKeyword(0, "John")).thenReturn(prospects);

        List<Prospect> actualProspects = prospectServiceMock.findAllProspectsByKeyword(0, "John");

        assertEquals(prospects, actualProspects);

    }

    @Test
    void testFindAll() {

        List<Prospect> prospects = List.of(new Prospect("John Smith", "test123@gmail.com"));

        when(prospectServiceMock.findAll()).thenReturn(prospects);

        List<Prospect> actualProspects = prospectServiceMock.findAll();

        assertEquals(prospects, actualProspects);

    }
}