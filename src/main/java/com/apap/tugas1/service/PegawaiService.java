package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

import java.util.List;

public interface PegawaiService {
    PegawaiModel getPegawaiDetailByNip(String nip);
    //long countEntity();
    //PegawaiModel getPegawaiDetailById(Long id);
    double getGajiLengkapByNip(String nip);
    void addPegawai(PegawaiModel pegawai);
    PegawaiModel cariPegawaiTermuda(long idInstansi);
    PegawaiModel cariPegawaiTertua (long idInstansi);
    List<PegawaiModel> getSemuaPegawai();
}