package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImp implements JabatanService {
    @Autowired
    private JabatanDB jabatanDB;

    @Override
    public List<JabatanModel> findAllJabatan() {
        return jabatanDB.findAll();
    }

    @Override
    public void addJabatan(JabatanModel jabatan) {
        jabatanDB.save(jabatan);
    }

    @Override
    public JabatanModel getJabatanDetailById(long id) {
        return jabatanDB.findById(id);
    }

    @Override
    public JabatanModel findJabatanDetailById(long id) {
        return jabatanDB.getOne(id);
    }

    @Override
    public void deleteJabatan(long id) {
        jabatanDB.deleteById(id);
    }
}