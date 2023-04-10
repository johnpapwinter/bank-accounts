package com.bank_accounts.service;

import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.entities.Holder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HolderService {

    void createHolder(HolderDTO holderDTO);

    HolderDTO getHolderBySsn(String ssn);

    HolderDTO getHolderById(Long id);

    Page<HolderDTO> getAllHolders(Pageable pageable);

    void updateHolder(Long id, HolderDTO holderDTO);

    void deleteHolder(Long id);


}
