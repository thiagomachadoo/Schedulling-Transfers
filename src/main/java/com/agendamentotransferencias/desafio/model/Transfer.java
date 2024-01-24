package com.agendamentotransferencias.desafio.model;


import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Transfer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Description("Conta de destino")
  @Column
  private int accountToTransfer;

  @Description("Valor da transferência")
  @Column
  private double valueForTransfer;

  @Description("Data que será realizada a transferência")
  @Column
  private LocalDate dateToTransfer;

  @Description("Data de agendamento")
  @Column
  private LocalDate schedulingDate;
}
