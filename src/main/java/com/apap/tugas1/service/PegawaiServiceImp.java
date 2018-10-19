package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImp implements PegawaiService {
    @Autowired
    private PegawaiDB pegawaiDB;

    @Override
    public PegawaiModel getPegawaiDetailByNip(String nip) {
        return pegawaiDB.findByNip(nip);
    }

    @Override
    public double getGajiLengkapByNip(String nip) {
        double gajiLengkap = 0;
        PegawaiModel pegawai = this.getPegawaiDetailByNip(nip);
        double gajiTerbesar = 0;
        for (JabatanModel jabatan:pegawai.getJabatanList()) {
            if (jabatan.getGajiPokok() > gajiTerbesar) {
                gajiTerbesar = jabatan.getGajiPokok();
            }
        }
        gajiLengkap += gajiTerbesar;
        double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
        gajiLengkap += (gajiLengkap * presentaseTunjangan/100);
        return gajiLengkap;
    }

    @Override
    public void addPegawai(PegawaiModel pegawai) {
        pegawaiDB.save(pegawai);
    }

    @Override
    public PegawaiModel cariPegawaiTermuda(long idInstansi) {
        PegawaiModel pegawaiTermuda = new PegawaiModel();
        int cnt = 0;
        for (PegawaiModel pegawaiModel : pegawaiDB.findAll()) {
            if (pegawaiModel.getInstansi().getId() == idInstansi) {
                if (cnt == 0) {
                    pegawaiTermuda = pegawaiModel;
                    cnt += 1;
                }
                else {
                    if (pegawaiModel.getTanggalLahir().compareTo(pegawaiTermuda.getTanggalLahir()) > 0) {
                        pegawaiTermuda = pegawaiModel;
                    }
                }
            }
        }
        return pegawaiTermuda;
    }

    @Override
    public PegawaiModel cariPegawaiTertua(long idInstansi) {
        PegawaiModel pegawaiTertua = new PegawaiModel();
        int cnt = 0;
        for (PegawaiModel pegawaiModel : pegawaiDB.findAll()) {
            if (pegawaiModel.getInstansi().getId() == idInstansi) {
                if (cnt == 0) {
                    pegawaiTertua = pegawaiModel;
                    cnt += 1;
                }
                else {
                    if (pegawaiModel.getTanggalLahir().compareTo(pegawaiTertua.getTanggalLahir()) < 0) {
                        pegawaiTertua = pegawaiModel;
                    }
                }
            }
        }
        return pegawaiTertua;
    }

    //@Override
    //public long countEntity() {
    //return pegawaiDB.count();
    //}

    //@Override
    //public PegawaiModel getPegawaiDetailById(Long id) {
    //return pegawaiDB.getOne(id);
    //}

}
