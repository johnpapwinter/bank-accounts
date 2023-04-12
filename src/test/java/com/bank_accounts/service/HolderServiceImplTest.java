package com.bank_accounts.service;

import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.entities.Holder;
import com.bank_accounts.domain.exceptions.HolderDoesNotExistException;
import com.bank_accounts.domain.mappers.HolderDTOMapper;
import com.bank_accounts.domain.repositories.HolderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HolderServiceImplTest {

    @Mock
    private HolderRepository holderRepository;

    @Mock
    private HolderDTOMapper holderDTOMapper;

    @InjectMocks
    private HolderServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Should create a Holder entity")
    void shouldCreateHolder() {
        // given
        HolderDTO holderDTO = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");

        when(holderRepository.findBySsn(holderDTO.ssn())).thenReturn(Optional.empty());
        ArgumentCaptor<Holder> captor = ArgumentCaptor.forClass(Holder.class);

        // when
        serviceUnderTest.createHolder(holderDTO);
        Mockito.verify(holderRepository, times(1)).save(captor.capture());
        Holder actual = captor.getValue();

        // then
        assertEquals(holderDTO.firstname(), actual.getFirstname());
        assertEquals(holderDTO.lastname(), actual.getLastname());
        assertEquals(holderDTO.ssn(), actual.getSsn());
    }

    @Test
    @DisplayName("Should find a Holder by SSN")
    void shouldGetHolderBySsn() {
        // given
        Holder holder = new Holder();
        holder.setFirstname("Obi-Wan");
        holder.setLastname("Kenobi");
        holder.setSsn("AA-BB");

        HolderDTO holderDTO = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));
        when(holderDTOMapper.apply(holder)).thenReturn(holderDTO);

        // when
        HolderDTO result = serviceUnderTest.getHolderBySsn(holder.getSsn());

        // then
        assertEquals(holder.getFirstname(), result.firstname());
        assertEquals(holder.getLastname(), result.lastname());
        assertEquals(holder.getSsn(), result.ssn());
    }

    @Test
    @DisplayName("Should throw exception when SSN does not exist")
    void shouldThrowExceptionOnFindBySsn() {
        // given
        String ssn = "AA-BB";

        when(holderRepository.findBySsn(ssn)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(HolderDoesNotExistException.class, () -> serviceUnderTest.getHolderBySsn(ssn));
    }

    @Test
    @DisplayName("Should throw exception when ID does not exist")
    void shouldThrowExceptionOnFindById() {
        Long id = 1L;

        when(holderRepository.findById(id)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(HolderDoesNotExistException.class, () -> serviceUnderTest.getHolderById(id));
    }

    @Test
    @DisplayName("Should find Holder by ID")
    void shouldGetHolderById() {
        // given
        Holder holder = new Holder();
        holder.setId(1L);
        holder.setFirstname("Obi-Wan");
        holder.setLastname("Kenobi");
        holder.setSsn("AA-BB");

        HolderDTO holderDTO = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");

        when(holderRepository.findById(holder.getId())).thenReturn(Optional.of(holder));
        when(holderDTOMapper.apply(holder)).thenReturn(holderDTO);

        // when
        HolderDTO result = serviceUnderTest.getHolderById(holder.getId());

        // then
        assertEquals(holder.getFirstname(), result.firstname());
        assertEquals(holder.getLastname(), result.lastname());
        assertEquals(holder.getSsn(), result.ssn());
    }

    @Test
    @DisplayName("Should return paginated results of Holders")
    void shouldGetAllHolders() {
        // given
        Holder holder1 = new Holder();
        holder1.setId(1L);
        holder1.setFirstname("Obi-Wan");
        holder1.setLastname("Kenobi");
        holder1.setSsn("AA-BB");

        Holder holder2 = new Holder();
        holder2.setId(2L);
        holder2.setFirstname("Anakin");
        holder2.setLastname("Skywalker");
        holder2.setSsn("CC-DD");

        Holder holder3 = new Holder();
        holder3.setId(3L);
        holder3.setFirstname("Ahsoka");
        holder3.setLastname("Tano");
        holder3.setSsn("EE-FF");

        List<Holder> holderList = new ArrayList<>();
        holderList.add(holder1);
        holderList.add(holder2);
        holderList.add(holder3);

        HolderDTO holderDTO1 = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");
        HolderDTO holderDTO2 = new HolderDTO("Anakin", "Skywalker", "CC-DD");
        HolderDTO holderDTO3 = new HolderDTO("Ahsoka", "Tano", "EE-FF");

        Pageable pageable = PageRequest.of(0, 3);
        Page<Holder> holderPage = new PageImpl<>(holderList);

        when(holderRepository.findAll(pageable)).thenReturn(holderPage);
        when(holderDTOMapper.apply(holder1)).thenReturn(holderDTO1);
        when(holderDTOMapper.apply(holder2)).thenReturn(holderDTO2);
        when(holderDTOMapper.apply(holder3)).thenReturn(holderDTO3);

        // when
        Page<HolderDTO> results = serviceUnderTest.getAllHolders(pageable);

        // then
        assertEquals(3, results.getSize());
    }

    @Test
    @DisplayName("Should return a list with all Holders")
    void shouldFetchAllHolders() {
        // given
        Holder holder1 = new Holder();
        holder1.setId(1L);
        holder1.setFirstname("Obi-Wan");
        holder1.setLastname("Kenobi");
        holder1.setSsn("AA-BB");

        Holder holder2 = new Holder();
        holder2.setId(2L);
        holder2.setFirstname("Anakin");
        holder2.setLastname("Skywalker");
        holder2.setSsn("CC-DD");

        Holder holder3 = new Holder();
        holder3.setId(3L);
        holder3.setFirstname("Ahsoka");
        holder3.setLastname("Tano");
        holder3.setSsn("EE-FF");

        List<Holder> holderList = new ArrayList<>();
        holderList.add(holder1);
        holderList.add(holder2);
        holderList.add(holder3);

        HolderDTO holderDTO1 = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");
        HolderDTO holderDTO2 = new HolderDTO("Anakin", "Skywalker", "CC-DD");
        HolderDTO holderDTO3 = new HolderDTO("Ahsoka", "Tano", "EE-FF");

        when(holderRepository.findAll()).thenReturn(holderList);
        when(holderDTOMapper.apply(holder1)).thenReturn(holderDTO1);
        when(holderDTOMapper.apply(holder2)).thenReturn(holderDTO2);
        when(holderDTOMapper.apply(holder3)).thenReturn(holderDTO3);

        // when
        List<HolderDTO> results = serviceUnderTest.fetchAllHolders();

        // then
        assertEquals(holderList.size(), results.size());
    }

    @Test
    @DisplayName("Should update a Holder entity")
    void shouldUpdateHolder() {
        // given
        HolderDTO holderDTO = new HolderDTO("Ben", "Kenobi", "AA-CC");
        Holder holder = new Holder();
        holder.setId(1L);
        holder.setFirstname("Obi-Wan");
        holder.setLastname("Kenobi");
        holder.setSsn("AA-BB");

        when(holderRepository.findById(holder.getId())).thenReturn(Optional.of(holder));
        ArgumentCaptor<Holder> captor = ArgumentCaptor.forClass(Holder.class);

        // when
        serviceUnderTest.updateHolder(holder.getId(), holderDTO);
        Mockito.verify(holderRepository, times(1)).save(captor.capture());
        Holder actual = captor.getValue();

        // then
        assertEquals(holderDTO.firstname(), actual.getFirstname());
        assertEquals(holderDTO.lastname(), actual.getLastname());
        assertEquals(holderDTO.ssn(), actual.getSsn());
    }

    @Test
    @DisplayName("Should delete Holder entity")
    void shouldDeleteHolder() {
        // given
        Holder holder = new Holder();
        holder.setId(1L);

        // when
        serviceUnderTest.deleteHolder(holder.getId());

        // then
        Mockito.verify(holderRepository, times(1)).deleteById(holder.getId());
    }
}