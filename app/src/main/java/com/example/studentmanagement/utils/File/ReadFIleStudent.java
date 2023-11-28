package com.example.studentmanagement.utils.File;

import android.content.Context;
import android.os.Environment;

import com.example.studentmanagement.Models.Student;
import com.example.studentmanagement.dto.StudentDTO;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadFIleStudent {

    public static List<StudentDTO> readExcelFile(Context context, InputStream inputStream) throws IOException {
        List<StudentDTO> studentList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                List<String> headers = getHeaders(headerRow);

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    StudentDTO studentDTO = mapRowToStudentDTO(row, headers);
                    if (studentDTO != null) {
                        studentList.add(studentDTO);
                    }
                }
            }
        }

        return studentList;
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

    private static StudentDTO mapRowToStudentDTO(Row row, List<String> headers) {
        StudentDTO studentDTO = new StudentDTO();

        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                switch (headers.get(i)) {
                    case "Tên học sinh":
                        studentDTO.setName(cell.getStringCellValue());
                        break;
                    case "Số điện thoại":
                        studentDTO.setPhoneNumber(cell.getStringCellValue());
                        break;
                    case "Email":
                        studentDTO.setEmail(cell.getStringCellValue());
                        break;
                    case "Ngày sinh":
                        studentDTO.setBirthDay(cell.getStringCellValue());
                        break;
                    case "Trạng thái":
                        studentDTO.setStatus(Boolean.parseBoolean(cell.getStringCellValue()));
                        break;
                    case "Giới tính":
                        studentDTO.setSex(Boolean.parseBoolean(cell.getStringCellValue()));
                        break;
                    case "avatar":
                        studentDTO.setAvatar("https://firebasestorage.googleapis.com/v0/b/tsimple-384dd.appspot.com/o/image%2Fd7b4d46e-10d1-4cb8-a80d-f199b327253d?alt=media&token=bcfd4618-da43-4100-ba3b-c2a3f8fcd92b");
                        break;
                    case "Ngày nhập học":
                        studentDTO.setStartSchool(cell.getStringCellValue());
                        break;
                    case "Ngày tốt nghiệp dự kiến":
                        studentDTO.setEndSchool(cell.getStringCellValue());
                        break;
                    case "Mã lớp học":
                        studentDTO.setClass_(cell.getStringCellValue());
                        break;
                    // Add additional cases for other headers if needed
                }
            }
        }
        return studentDTO;
    }



}
