package by.itman.vms.services;

import by.itman.vms.configs.VMConfig;
import by.itman.vms.entitis.Load;
import by.itman.vms.entitis.VM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VMService {
    private final List<VM> vms;
    private volatile Map<Integer, Load> loads;

    @Autowired
    public VMService(final VMConfig vmConfig) {
        vms = vmConfig.getVms();

        loads = vms.stream()
                .map(this::generateNew)
                .collect(Collectors.toMap(Load::getVmId, Function.identity()));
    }

    private Load generateNew(final VM vm) {
        final var random = ThreadLocalRandom.current();
        final var coreLoads = random.doubles(vm.getCores(), 0, 0.1)
                .boxed()
                .collect(Collectors.toList());
        final int maxRam = vm.getRam() / 2;
        final int usedMemory = random.nextInt(Math.min(200, maxRam), maxRam + 1);

        return new Load(vm.getId(), coreLoads, usedMemory);
    }

    private Load update(final VM vm) {
        final var random = ThreadLocalRandom.current();
        final var load = loads.get(vm.getId());

        final var coreLoads = load.getCoreLoads().stream()
                .map(x -> Math.max(0, Math.min(1, x + random.nextDouble(-0.2, 0.2))))
                .collect(Collectors.toList());
        final int newRam = (int) (load.getUsedMemory() + vm.getRam() * random.nextDouble(-0.024, 0.025));
        final int usedMemory = Math.max(200, Math.min(vm.getRam(), newRam));

        return new Load(load.getVmId(), coreLoads, usedMemory);
    }

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void updateLoads() {
        loads = vms.stream()
                .map(this::update)
                .collect(Collectors.toMap(Load::getVmId, Function.identity()));

        log.info("Current loads: {}", loads.values());
    }

    public List<VM> getAllVms() {
        return vms;
    }

    public List<Load> getLoadByIds(final List<Integer> vmIds) {
        final Map<Integer, Load> loads = this.loads;
        return vmIds.stream()
                .distinct()
                .map(loads::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
