package com.example.studentmanagement.utils.File;

import android.content.Context;

import com.example.studentmanagement.dto.CertificateDTO;
import com.example.studentmanagement.dto.StudentDTO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ReadFileCertificate {

    public static List<CertificateDTO> readExcelFile(Context context, InputStream inputStream) throws IOException {
        List<CertificateDTO> certificateDTOList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                List<String> headers = getHeaders(headerRow);

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    CertificateDTO certificateDTO = mapRowToCertificate(row, headers);
                    if (certificateDTO != null) {
                        certificateDTOList.add(certificateDTO);
                    }
                }
            }
        }

        return certificateDTOList;
    }

    private static List<String> getHeaders(Row headerRow) {
        List<String> headers = new ArrayList<>();
        Iterator<Cell> cellIterator = headerRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            headers.add(cell.getStringCellValue().trim());
        }
        return headers;
    }

    private static String generateCustomPushId() {
        return UUID.randomUUID().toString();
    }


    private static CertificateDTO mapRowToCertificate(Row row, List<String> headers) {
        CertificateDTO certificateDTO = new CertificateDTO();
        String id = generateCustomPushId();
        certificateDTO.setId(id);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                switch (headers.get(i)) {
                    case "tên chứng chỉ":
                        certificateDTO.setName(cell.getStringCellValue());
                        break;
                    case "ngày nhận chứng chỉ":
                        certificateDTO.setStartCertificate(cell.getStringCellValue());
                        break;
                    case "ngày hết hạn":
                        certificateDTO.setEndCertificate(cell.getStringCellValue());
                        break;
                    case "overall score":
                        if (cell.getCellType() == CellType.NUMERIC) {
                            certificateDTO.setOveralScore(cell.getNumericCellValue());
                        }
                        break;
                    case "mô tả chứng chỉ":
                        certificateDTO.setDescribe(cell.getStringCellValue());
                        break;
                    case "link":
                        certificateDTO.setLink(cell.getStringCellValue());
                        break;
                    case "số điện thoại học sinh":
                        certificateDTO.setPhoneStudent(cell.getStringCellValue());
                        break;
                    // Add additional cases for other headers if needed
                }
            }
        }
        return certificateDTO;
    }
}
