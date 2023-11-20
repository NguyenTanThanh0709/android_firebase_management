package com.example.studentmanagement.Repository;

import com.example.studentmanagement.Models.Certificate;

public interface CertificateClickListener {
    void onEditCertificateClick(Certificate certificate);
    void onDeleteCertificateClick(int position);
}
