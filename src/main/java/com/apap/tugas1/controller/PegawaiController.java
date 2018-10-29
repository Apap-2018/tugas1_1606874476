package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
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

import java.util.List;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private ProvinsiService provinsiService;

    @Autowired
    private InstansiService instansiService;

    //fitur 1
    @RequestMapping("/")
    private String index(Model model) {
        model.addAttribute("listJabatan", jabatanService.findAllJabatan());
        model.addAttribute("listInstansi",instansiService.findAllInstansi());
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
    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
    private String addPegawai(Model model) {
        PegawaiModel pegawai = new PegawaiModel();
        pegawai.setInstansi(new InstansiModel());

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
        model.addAttribute("listJabatan", jabatanService.findAllJabatan());

        return "tambah-pegawai";
    }
    //fitur 2
    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    private String addPegwawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
        String nip = "";

        nip += pegawai.getInstansi().getId();

        String[] tglLahir = pegawai.getTanggalLahir().toString().split("-");
        String tglLahirString = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
        nip += tglLahirString;

        nip += pegawai.getTahunMasuk();

        int counterSama = 1;
        for (PegawaiModel pegawaiInstansi:pegawai.getInstansi().getPegawaiInstansi()) {
            if (pegawaiInstansi.getTahunMasuk().equals(pegawai.getTahunMasuk()) && pegawaiInstansi.getTanggalLahir().equals(pegawai.getTanggalLahir())) {
                counterSama += 1;
            }
        }
        nip += "0" + counterSama;

        pegawai.setNip(nip);
        pegawaiService.addPegawai(pegawai);
        model.addAttribute("pegawai", pegawai);
        return "sukses-add-pegawai";
    }

    //fitur 10
    @RequestMapping(value = "pegawai/termuda-tertua", method = RequestMethod.GET)
    public String mudaTua(@RequestParam(value = "idInstansi") long idInstansi, Model model) {
        PegawaiModel pegawaiTermuda = pegawaiService.cariPegawaiTermuda(idInstansi);
        PegawaiModel pegawaiTertua = pegawaiService.cariPegawaiTertua(idInstansi);
        System.out.println(pegawaiTermuda.getId());
        System.out.println(pegawaiTermuda.getInstansi().getNama());

        model.addAttribute("pegawaiTermuda", pegawaiTermuda);
        model.addAttribute("gajiLengkapTermuda", Math.round(pegawaiService.getGajiLengkapByNip(pegawaiTermuda.getNip())));
        model.addAttribute("jabatanListTermuda", pegawaiTermuda.getJabatanList());

        model.addAttribute("pegawaiTertua", pegawaiTertua);
        model.addAttribute("gajiLengkapTertua", Math.round(pegawaiService.getGajiLengkapByNip(pegawaiTertua.getNip())));
        model.addAttribute("jabatanListTertua", pegawaiTertua.getJabatanList());

        return "view-instansi";
    }

    //fitur 3
    @RequestMapping(value = "/pegawai/ubah")
    public String ubahPegawai (@RequestParam(value = "nip") String nip, Model model) {
        PegawaiModel pegawaiModel = pegawaiService.getPegawaiDetailByNip(nip);

        model.addAttribute("listporvinsi", provinsiService.getProvinsiList());
        model.addAttribute("listJabatan", jabatanService.findAllJabatan());
        model.addAttribute("pegawai", pegawaiModel);
        return "ubah-pegawai";
    }

    //fitur 3
    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
    private String ubahPegawaiSubmit (@ModelAttribute PegawaiModel pegawai, Model model) {
        String nip = "";
        nip += pegawai.getInstansi().getId();

        String[] tglLahir = pegawai.getTanggalLahir().toString().split("-");
        String tglLahirString = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
        nip += tglLahirString;

        nip += pegawai.getTahunMasuk();
        int cnt = 1;
        for (PegawaiModel pegawaiInstansi : pegawai.getInstansi().getPegawaiInstansi()) {
            if (pegawaiInstansi.getTahunMasuk().equals(pegawai.getTahunMasuk()) && pegawaiInstansi.getTanggalLahir().equals(pegawai.getTanggalLahir()) && pegawaiInstansi.getId() != pegawai.getId()) {
                cnt += 1;
            }
        }
        nip += "0" + cnt;
        pegawai.setNip(nip);
        pegawaiService.addPegawai(pegawai);
        model.addAttribute("pegawai", pegawai);
        return "ubah-pegawai-sukses";

    }

    //fitru 4
    @RequestMapping(value = "/pegawai/cari")
    public String viewPegawaiFilter(Model model) {
        List<PegawaiModel> listPegawai = pegawaiService.getSemuaPegawai();
        model.addAttribute("listPegawai", listPegawai);
        return "view-pegawai-filter";
    }
}