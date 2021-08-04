package by.itman.vms.entitis;

import lombok.Data;

/**
 * Virtual Machine (VM) info
 */
@Data
public class VM {
    /**
     * VM id
     */
    private int id;

    /**
     * VM name
     */
    private String name;

    /**
     * number of VM cpu cores
     */
    private int cores;

    /**
     * cpu frequency
     */
    private int frequency;

    /**
     * available ram
     */
    private int ram;
}
