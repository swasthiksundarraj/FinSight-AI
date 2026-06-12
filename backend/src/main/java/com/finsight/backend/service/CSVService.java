package com.finsight.backend.service;

import com.finsight.backend.entity.Transaction;
import com.finsight.backend.repository.TransactionRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;

@Service
public class CSVService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransactions(MultipartFile file) {

        try (
                Reader reader =
                        new InputStreamReader(file.getInputStream())
        ) {

            Iterable<CSVRecord> records =
                    CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .parse(reader);

            for (CSVRecord record : records) {

                Transaction transaction = new Transaction();

                transaction.setTransactionDate(
                        LocalDate.parse(record.get("Date"))
                );

                transaction.setDescription(
                        record.get("Description")
                );

                double amount =
                        Double.parseDouble(record.get("Amount"));

                transaction.setAmount(Math.abs(amount));

                if (amount >= 0) {
                    transaction.setType("INCOME");
                } else {
                    transaction.setType("EXPENSE");
                }

                String description =
                        record.get("Description").toLowerCase();

                if (description.contains("swiggy")) {
                    transaction.setCategory("Food");
                } else if (description.contains("uber")) {
                    transaction.setCategory("Travel");
                } else if (description.contains("amazon")) {
                    transaction.setCategory("Shopping");
                } else {
                    transaction.setCategory("Others");
                }

                transactionRepository.save(transaction);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error processing CSV file",
                    e
            );
        }
    }
}