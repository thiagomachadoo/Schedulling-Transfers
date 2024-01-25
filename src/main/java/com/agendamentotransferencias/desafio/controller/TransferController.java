package com.agendamentotransferencias.desafio.controller;


import com.agendamentotransferencias.desafio.model.*;
import com.agendamentotransferencias.desafio.repository.*;
import com.agendamentotransferencias.desafio.service.TransferService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "transfer-actions")
public class TransferController {

  private final TransferRepository transferRepository;
  private final TransferService transferService;

  @Description("Busca da lista de todas as transferencias agendadas")
  @GetMapping("/scheduled-transfers")
  public List<Transfer> findAllTransfers() {

    return transferRepository.findAll();
  }

  @Description("Busca de Transferencia agendada por ID")
  @GetMapping("/scheduled-transfers/{id}")
  public ResponseEntity<Transfer> findTransferById(@PathVariable("id") Long id) {

    var subject = transferRepository.findTransferById(id);

    return ResponseEntity.ok(subject);
  }

  @PostMapping(path = "/create-transfer",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createTransfer(@RequestBody Transfer transferDTO) {

    var object = transferService.scheduledTransfer(transferDTO);

    if (object.getValueForTransfer() != 0.0) {
      transferRepository.save(transferDTO);
      return new ResponseEntity<>("Transferência agendada com sucesso!\n", HttpStatus.CREATED);
    }

    return new ResponseEntity<>("Não foi possivel agendar a transferência, aumente o range de data da transferência!\n", HttpStatus.BAD_REQUEST);
  }
}
