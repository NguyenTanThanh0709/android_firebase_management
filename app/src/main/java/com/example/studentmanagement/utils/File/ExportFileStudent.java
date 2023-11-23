package com.example.studentmanagement.utils.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.studentmanagement.Models.Certificate;
import com.example.studentmanagement.Models.Student;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportFileStudent {

    public static void exportToExcel(List<Student> students, Context context) {

        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();


        Sheet sheet = workbook.createSheet("Students");


        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Tên học sinh");
        headerRow.createCell(1).setCellValue("Giới tính");
        headerRow.createCell(2).setCellValue("Số điện thoại");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Ngày sinh");
        headerRow.createCell(5).setCellValue("Trạng thái");
        headerRow.createCell(6).setCellValue("avatar");
        headerRow.createCell(7).setCellValue("Ngày nhập học");
        headerRow.createCell(8).setCellValue("Ngày tốt nghiệp dự kiến");
        headerRow.createCell(9).setCellValue("Tên Lớp Học");
        headerRow.createCell(10).setCellValue("GPA");
        headerRow.createCell(11).setCellValue("Danh sách chứng chỉ");
        // Add more headers as needed

        // Add data rows
        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getName());

            if(student.getSex()){
                row.createCell(1).setCellValue("Nam");
            }else {
                row.createCell(1).setCellValue("Nữ");
            }

            row.createCell(2).setCellValue(student.getPhoneNumber());
            row.createCell(3).setCellValue(student.getEmail());
            row.createCell(4).setCellValue(student.getName());
            if(student.getStatus()){
                row.createCell(5).setCellValue("Đang học tại trường");
            }else {
                row.createCell(5).setCellValue("Đã nghỉ học");
            }
            row.createCell(6).setCellValue(student.getAvatar());
            row.createCell(7).setCellValue(student.getStartSchool());
            row.createCell(8).setCellValue(student.getEndSchool());
            row.createCell(9).setCellValue(student.getClass_().getName());
            row.createCell(10).setCellValue(student.getGPA().toString());
            String certificate = "";

            if(student.getCertificates() != null ){
                for(Certificate certificate1 : student.getCertificates()){
                    certificate += certificate1.getName() + " -- ";
                }
            }

            row.createCell(11).setCellValue(certificate);
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String fileName = "students_" + timeStamp + ".xlsx";

        // Get the external storage directory
        File externalFilesDir = context.getExternalFilesDir(null);
        File file = new File("/storage/self/primary/Download/", fileName);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            Toast.makeText(context.getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            Log.d("ExportFileStudent", "Exported successfully to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "NO OK", Toast.LENGTH_LONG).show();
            Log.e("ExportFileStudent", "Error exporting file", e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
