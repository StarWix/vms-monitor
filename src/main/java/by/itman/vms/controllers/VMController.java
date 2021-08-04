package by.itman.vms.controllers;

import by.itman.vms.entitis.Load;
import by.itman.vms.entitis.VM;
import by.itman.vms.services.VMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VMController {
    private final VMService vmService;

    @Autowired
    public VMController(final VMService vmService) {
        this.vmService = vmService;
    }

    /**
     * @return list of available Virtual Machines
     */
    @GetMapping("/vms")
    public List<VM> getAllVms() {
        return vmService.getAllVms();
    }

    /**
     * @param vmIds list of vm ids
     * @return list of loads for passed vm ids
     */
    @GetMapping("/loads")
    public List<Load> getLoadsByIds(@RequestParam final List<Integer> vmIds) {
        return vmIds == null ? Collections.emptyList() : vmService.getLoadByIds(vmIds);
    }
}
