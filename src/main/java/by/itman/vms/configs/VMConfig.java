package by.itman.vms.configs;

import by.itman.vms.entitis.VM;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "vm")
public class VMConfig {
    private List<VM> vms;
}
