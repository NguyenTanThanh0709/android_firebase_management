package com.example.studentmanagement.utils.File;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import java.util.List;

public class ExportFileCertificate {

    public static void exportToExcel(Student student, Context context) {

        Workbook workbook = new XSSFWorkbook();

        if(student == null){
            Toast.makeText(context.getApplicationContext(), "Kiểm Tra Loại Thông tin học sinh", Toast.LENGTH_SHORT).show();
            return ;
        }
        if( student.getCertificates().size() == 0){
            Toast.makeText(context.getApplicationContext(), "Sinh Viên chưa có chứng chỉ nào để xuất!", Toast.LENGTH_SHORT).show();
            return ;
        }

        List<Certificate> certificateList = student.getCertificates();



        Sheet sheet = workbook.createSheet("Certificates");
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Tên học sinh");
        headerRow.createCell(1).setCellValue("Số điện thoại");
        headerRow.createCell(2).setCellValue("Tên chứng chỉ");
        headerRow.createCell(3).setCellValue("Ngày nhận chứng chỉ");
        headerRow.createCell(4).setCellValue("Ngày hết hạn chứng chỉ");
        headerRow.createCell(5).setCellValue("Tổng Điểm của chứng chỉ");
        headerRow.createCell(6).setCellValue("Mô tả chứng chỉ");
        headerRow.createCell(7).setCellValue("Link chứng chỉ");

        int rowNum = 1;
        for (Certificate certificate : certificateList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getName());
            row.createCell(1).setCellValue(student.getPhoneNumber());
            row.createCell(2).setCellValue(certificate.getName());
            row.createCell(3).setCellValue(certificate.getStartCertificate());
            row.createCell(4).setCellValue(certificate.getEndCertificate());
            row.createCell(5).setCellValue(certificate.getOveralScore());
            row.createCell(6).setCellValue(certificate.getDescribe());
            row.createCell(7).setCellValue(certificate.getLink());
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String fileName = "Certificate_" + student.getName() + timeStamp + ".xlsx";

        // Get the external storage directory
        File externalFilesDir = context.getExternalFilesDir(null);
        File file = new File("/storage/self/primary/Download/", fileName);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            Toast.makeText(context.getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            Log.d("ExportCertificate", "Exported successfully to " + file.getAbsolutePath());
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
