package by.itman.vms.entitis;

import lombok.Value;

import java.util.List;

/**
 *
 */
@Value
public class Load {
    /**
     * id of VM
     */
    int vmId;

    /**
     * loading each VM core at the moment. All values are between 0 and 1.
     */
    List<Double> coreLoads;

    /**
     * used VM memory at the moment
     */
    int usedMemory;
}
