package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JabatanController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private ProvinsiService provinsiService;

    //fitur 5
    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
    private String addJabatan (Model model) {
        JabatanModel jabatan = new JabatanModel();
        model.addAttribute("jabatan", jabatan);

        return "tambah-jabatan";
    }

    //fitur 5
    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
    private String addJabatanSubmit (@ModelAttribute JabatanModel jabatan, Model model) {
        jabatanService.addJabatan(jabatan);
        model.addAttribute("jabatan", jabatan);
        return "sukses-add-jabatan";
    }

    //fitur 6
    @RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
    public String viewJabatan (@RequestParam("idJabatan") long idJabatan, Model model) {
        JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
        model.addAttribute("jabatan", jabatan);
        return "view-jabatan";
    }

    //fitur 7
    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
    public String ubahJabatan (@RequestParam(value = "idJabatan") long idJabatan, Model model) {
        JabatanModel jabatans = jabatanService.findJabatanDetailById(idJabatan);
        model.addAttribute("jabatan", jabatans);
        return "update-jabatan";
    }

    //fitur 7
    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
    public String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {

        jabatanService.addJabatan(jabatan);
        model.addAttribute("jabatan", jabatan);
        return "sukses-ubah-jabatan";
    }

    //fitur 8
    @RequestMapping(value = "/jabatan/hapus", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") long id, Model model) {
        JabatanModel jabatan = jabatanService.findJabatanDetailById(id);

        if (jabatan.getSize() != 0) {
            jabatanService.deleteJabatan(id);
            model.addAttribute("jabatan", jabatan);
            return "delete-failed";
        }
        else {
            model.addAttribute("jabatan", jabatan);
            return "delete";
        }
    }

    //fitur 9
    @RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
    public String viewallJabatan(Model model) {
        model.addAttribute("listJabatan", jabatanService.findAllJabatan());
        return "viewall-jabatan";
    }
}
