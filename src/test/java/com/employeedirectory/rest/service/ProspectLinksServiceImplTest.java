package com.employeedirectory.rest.service;

import com.employeedirectory.rest.entity.Prospect;
import com.employeedirectory.rest.entity.ProspectLinks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProspectLinksServiceImplTest {

    @Mock
    @Autowired
    private ProspectLinksService prospectLinksServiceMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProspectLinks() {

        ProspectLinks links = new ProspectLinks("instagram.com", "facebook.com", "linkedin.com");

        when(prospectLinksServiceMock.getProspectLinks(0L)).thenReturn(links);

        ProspectLinks actualLinks = prospectLinksServiceMock.getProspectLinks(0L);

        assertEquals(links, actualLinks);

    }

    @Test
    void testSaveProspectLinks() {

        ProspectLinks links = new ProspectLinks("instagram.com", "facebook.com", "linkedin.com");

        prospectLinksServiceMock.saveProspectLinks(links);

        verify(prospectLinksServiceMock).saveProspectLinks(links);

    }

    @Test
    void testDeleteProspectLinks() {

        prospectLinksServiceMock.deleteProspectLinks(0L);

        verify(prospectLinksServiceMock).deleteProspectLinks(0L);

    }
}