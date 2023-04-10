package com.bank_accounts.service;

import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.enums.ErrorMessage;
import com.bank_accounts.domain.exceptions.HolderAlreadyExistsException;
import com.bank_accounts.domain.exceptions.HolderDoesNotExistException;
import com.bank_accounts.domain.mappers.HolderDTOMapper;
import com.bank_accounts.domain.repositories.HolderRepository;
import com.bank_accounts.domain.entities.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HolderServiceImpl implements HolderService {

    private final HolderRepository holderRepository;
    private final HolderDTOMapper holderDTOMapper;


    @Autowired
    public HolderServiceImpl(HolderRepository holderRepository,
                             HolderDTOMapper holderDTOMapper) {
        this.holderRepository = holderRepository;
        this.holderDTOMapper = holderDTOMapper;
    }

    @Override
    @Transactional
    public void createHolder(HolderDTO holderDTO) {
        holderRepository.findBySsn(holderDTO.ssn()).ifPresent(s -> {
            throw new HolderAlreadyExistsException(String.format(
                    ErrorMessage.ERROR_001_ACCOUNT_ALREADY_EXISTS.getMessage(), holderDTO.ssn()
            ));
        });

        Holder holder = new Holder();

        holder.setFirstname(holderDTO.firstname());
        holder.setLastname(holderDTO.lastname());
        holder.setSsn(holderDTO.ssn());

        holderRepository.save(holder);
    }

    @Override
    public HolderDTO getHolderBySsn(String ssn) {
        Holder holder = holderRepository.findBySsn(ssn).orElseThrow(() ->
                new HolderDoesNotExistException(String.format(
                        ErrorMessage.ERROR_006_HOLDER_DOES_NOT_EXIST.getMessage(), ssn
        )));

        return holderDTOMapper.apply(holder);
    }

    @Override
    public HolderDTO getHolderById(Long id) {
        Holder holder = holderRepository.findById(id).orElseThrow(() ->
                new HolderDoesNotExistException(String.format(
                        ErrorMessage.ERROR_006_HOLDER_DOES_NOT_EXIST.getMessage(), id
        )));

        return holderDTOMapper.apply(holder);
    }

    @Override
    public Page<HolderDTO> getAllHolders(Pageable pageable) {

        return holderRepository.findAll(pageable).map(holderDTOMapper);
    }

    @Override
    public List<HolderDTO> fetchAllHolders() {

        return holderRepository.findAll().stream().map(holderDTOMapper).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateHolder(Long id, HolderDTO holderDTO) {
        Holder holder = holderRepository.findById(id).orElseThrow(() ->
                new HolderDoesNotExistException(String.format(
                        ErrorMessage.ERROR_006_HOLDER_DOES_NOT_EXIST.getMessage(), id
        )));

        holder.setFirstname(holderDTO.firstname());
        holder.setLastname(holderDTO.lastname());
        holder.setSsn(holderDTO.ssn());


        holderRepository.save(holder);
    }

    @Override
    @Transactional
    public void deleteHolder(Long id) {

        holderRepository.deleteById(id);
    }
}
