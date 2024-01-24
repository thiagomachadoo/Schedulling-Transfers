package com.agendamentotransferencias.desafio.repository;

import com.agendamentotransferencias.desafio.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

   Transfer findTransferById(Long idTransfer);

}
