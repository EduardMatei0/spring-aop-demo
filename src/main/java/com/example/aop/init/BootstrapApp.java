package com.example.aop.init;

import com.example.aop.model.AzureGroups;
import com.example.aop.model.AzureVirtualMachines;
import com.example.aop.model.Users;
import com.example.aop.repository.AzureGroupsRepository;
import com.example.aop.repository.AzureVirtualMachinesRepository;
import com.example.aop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class BootstrapApp {

    private final AzureGroupsRepository azureGroupsRepository;
    private final AzureVirtualMachinesRepository azureVirtualMachinesRepository;
    private final UsersRepository usersRepository;

//    @PostConstruct
    public void init() {
        List<Users> users = initUsers();
        List<AzureGroups> azureGroups = initGroups();
        initMachines(azureGroups);
        addUsersToGroups(users, azureGroups);

    }

    private List<Users> initUsers() {
        List<Users> usersList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Users user = buildNewUser();
            usersList.add(user);
        }
        usersRepository.saveAll(usersList);
        return usersList;
    }

    private List<AzureGroups> initGroups() {
        List<AzureGroups> azureGroups = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            AzureGroups group = buildGroup();
            azureGroups.add(group);
        }
        azureGroupsRepository.saveAll(azureGroups);
        return azureGroups;
    }

    private void initMachines(List<AzureGroups> azureGroups) {
        List<AzureVirtualMachines> azureVirtualMachinesList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            AzureVirtualMachines azureVirtualMachines = buildNewMachine();
            azureVirtualMachines.setAzureGroupId(azureGroups.get(new Random().nextInt(azureGroups.size())).getId());
            azureVirtualMachinesList.add(azureVirtualMachines);
        }
        azureVirtualMachinesRepository.saveAll(azureVirtualMachinesList);
    }

    private void addUsersToGroups(List<Users> users, List<AzureGroups> azureGroups) {
        int currentElement = 0;
        for (int i = 0; i < users.size(); i++) {
            if (i % 10 == 0) currentElement = i;
            if (currentElement == 200) currentElement = 0;
            users.get(i).setAzureGroups(azureGroups.subList(currentElement, currentElement + 10));
        }
        usersRepository.saveAll(users);
    }

    private Users buildNewUser() {
        return Users
                .builder()
                .email(RandomString.make() + "@gmail.com")
                .name(RandomString.make())
                .phoneNumber(RandomString.make(10))
                .build();
    }

    private AzureVirtualMachines buildNewMachine() {
        return AzureVirtualMachines
                .builder()
                .memory(new Random().nextInt(10000))
                .size(new Random().nextInt(2000))
                .name(RandomString.make())
                .operatingSystem("Windows")
                .uuid(UUID.randomUUID().toString())
                .build();

    }

    private AzureGroups buildGroup() {
        return AzureGroups
                .builder()
                .name(RandomString.make())
                .build();
    }

}
