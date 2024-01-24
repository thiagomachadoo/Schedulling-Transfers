package com.agendamentotransferencias.desafio.service;

import com.agendamentotransferencias.desafio.model.Transfer;
import com.agendamentotransferencias.desafio.utils.ConstantsDaysToTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransferService {
    private static final Map<Range, Double> TAX_MAP = new HashMap<>();

    static {
        TAX_MAP.put(new Range(ConstantsDaysToTransfer.DAY_ZERO, ConstantsDaysToTransfer.DAY_ZERO), 2.5);
        TAX_MAP.put(new Range(ConstantsDaysToTransfer.DAY_ONE, ConstantsDaysToTransfer.DAY_TEN), 0.0);
        TAX_MAP.put(new Range(ConstantsDaysToTransfer.DAY_ELEVEN, ConstantsDaysToTransfer.DAY_TWENTY), 8.2);
        TAX_MAP.put(new Range(ConstantsDaysToTransfer.DAY_TWENTY_ONE, ConstantsDaysToTransfer.DAY_THIRTY), 6.9);
        TAX_MAP.put(new Range(ConstantsDaysToTransfer.DAY_THIRTY_ONE, ConstantsDaysToTransfer.DAY_FORTY), 4.7);
        TAX_MAP.put(new Range(ConstantsDaysToTransfer.DAY_FORTY_ONE, ConstantsDaysToTransfer.DAY_FIFTY), 1.7);
    }

    public Transfer scheduledTransfer(Transfer dataToTransfer) {
        return validateTransfer(dataToTransfer);
    }


    private Transfer validateTransfer(Transfer dataToTransfer) {
        LocalDate dataAtual = LocalDate.now();
        var countDays = ChronoUnit.DAYS.between(dataToTransfer.getSchedulingDate(), dataAtual);

        double tax = findApplicableTax(countDays);
        setTaxValue(dataToTransfer, tax);

        return dataToTransfer;
    }

    private double findApplicableTax(long countDays) {
        return TAX_MAP.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isInRange(countDays))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(0.0);
    }

    private void setTaxValue(Transfer dataToTransfer, double tax) {
        if (tax <= 0.0) {
            throw new IllegalStateException("Não será possível realizar a transferência");
        }
        var valueWithoutTax = dataToTransfer.getValueForTransfer() - (dataToTransfer.getValueForTransfer() * (tax / 100));
        dataToTransfer.setValueForTransfer(valueWithoutTax);
    }

    private static class Range {
        private final long start;
        private final long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public boolean isInRange(long value) {
            return value >= start && value <= end;
        }
    }
}
