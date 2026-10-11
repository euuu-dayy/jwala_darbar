package com.jwalanavdurga.service;

import com.jwalanavdurga.entity.Donation;
import com.jwalanavdurga.entity.DonationReceipt;
import com.jwalanavdurga.repository.DonationReceiptRepository;
import com.jwalanavdurga.repository.DonationRepository;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.exception.BusinessException;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;

import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class DonationReceiptPdfService {

    private final DonationRepository donationRepository;
    private final DonationReceiptRepository receiptRepository;

    public DonationReceiptPdfService(
            DonationRepository donationRepository,
            DonationReceiptRepository receiptRepository) {

        this.donationRepository = donationRepository;
        this.receiptRepository = receiptRepository;
    }

    public byte[] generateReceiptPdf(Long donationId) {

        Donation donation = donationRepository
                .findById(donationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Donation not found."
                        )
                );

        if (!"SUCCESS".equalsIgnoreCase(
                donation.getDonationStatus())) {

            throw new BusinessException(
                    "Receipt PDF can be generated only for a successful donation."
            );
        }

        DonationReceipt receipt =
                receiptRepository
                        .findByDonationId(donationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Donation receipt not found."
                                )
                        );

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            // -----------------------------
            // Header
            // -----------------------------

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            20
                    );

            Paragraph title =
                    new Paragraph(
                            "JWALA NAVDURGA UTSAV MANDAL",
                            titleFont
                    );

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            Font subtitleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            12
                    );

            Paragraph subtitle =
                    new Paragraph(
                            "Donation Receipt",
                            subtitleFont
                    );

            subtitle.setAlignment(Element.ALIGN_CENTER);

            document.add(subtitle);

            document.add(new Paragraph(" "));

            // -----------------------------
            // Receipt information
            // -----------------------------

            PdfPTable receiptTable =
                    new PdfPTable(2);

            receiptTable.setWidthPercentage(100);

            addRow(
                    receiptTable,
                    "Receipt Number",
                    receipt.getReceiptNumber()
            );

            addRow(
                    receiptTable,
                    "Receipt Date",
                    receipt.getReceiptDate().toString()
            );

            addRow(
                    receiptTable,
                    "Donation ID",
                    donation.getId().toString()
            );

            addRow(
                    receiptTable,
                    "Donation Type",
                    donation.getDonationType()
            );

            addRow(
                    receiptTable,
                    "Payment Status",
                    donation.getDonationStatus()
            );

            addRow(
                    receiptTable,
                    "Currency",
                    "INR"
            );

            addRow(
                    receiptTable,
                    "Amount",
                    "₹ " + donation.getAmount()
            );

            document.add(receiptTable);

            document.add(new Paragraph(" "));

            // -----------------------------
            // Thank you message
            // -----------------------------

            Paragraph thankYou =
                    new Paragraph(
                            "Thank you for supporting "
                            + "Jwala Navdurga Utsav Mandal."
                    );

            thankYou.setAlignment(Element.ALIGN_CENTER);

            document.add(thankYou);

            document.add(new Paragraph(" "));

            Paragraph note =
                    new Paragraph(
                            "This receipt is generated electronically "
                            + "and does not require a signature."
                    );

            note.setAlignment(Element.ALIGN_CENTER);

            document.add(note);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to generate donation receipt PDF.",
                    ex
            );
        }
    }

    private void addRow(
            PdfPTable table,
            String label,
            String value) {

        Font boldFont =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        10
                );

        Font normalFont =
                FontFactory.getFont(
                        FontFactory.HELVETICA,
                        10
                );

        PdfPCell labelCell =
                new PdfPCell(
                        new Phrase(label, boldFont)
                );

        PdfPCell valueCell =
                new PdfPCell(
                        new Phrase(value, normalFont)
                );

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}