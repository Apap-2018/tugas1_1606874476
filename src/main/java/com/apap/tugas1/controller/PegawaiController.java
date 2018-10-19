package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private ProvinsiService provinsiService;

    //fitur 1
    @RequestMapping("/")
    private String index(Model model) {
        model.addAttribute("listJabatan", jabatanService.findAllJabatan());
        return "index";
    }

    //fitur 1
    @RequestMapping(value = "/pegawai")
    public String viewPegawai(@RequestParam("nip") String nip, Model model) {
        PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("gajiLengkap", Math.round(pegawaiService.getGajiLengkapByNip(nip)));
        model.addAttribute("jabatanList", pegawai.getJabatanList());

        return "view-pegawai";
    }

    //fitur 2
    @RequestMapping(value = "/pegawai/view", method = RequestMethod.GET)
    private String addPegawai (Model model) {
        PegawaiModel pegawai = new PegawaiModel();
        pegawai.setInstansi(new InstansiModel());

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
        model.addAttribute("listJabatan", jabatanService.findAllJabatan());

        return "tambah-pegawai";
    }

    //fitur 2
/*    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    private String addPegawaiSukses (@ModelAttribute PegawaiModel pegawai, Model model){
        String nip = "";
        nip += pegawai.getInstansi().getId();

        String[] tglLahir = pegawai.getTanggal_lahir().toString().split("-");
        String tglLahirString = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
        nip += tglLahirString;
        nip += pegawai.getTahun_masuk();

        int cnt = 1;
        for (PegawaiModel pegawaiInstansi : pegawai.getInstansi().getPegawaiInstansi()) {
            if (pegawaiInstansi.getTahun_masuk().equals(pegawai.getTahun_masuk()) && pegawaiInstansi.getTanggal_lahir() {
                cnt += 1;
            }
        }
    }*/

    //fitur 5

}
