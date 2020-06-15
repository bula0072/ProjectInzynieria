package com.example.project.controllers;


/*@RestController*/
public class AccountTypeController {
  /*  @Autowired
    AccountTypeRepository accountTypeRepository;
    private final String accountTypesUrl = "account-types";

    @GetMapping(accountTypesUrl)
    List<AccountTypeDto1> getAllAccountTypes() {
        List<AccountTypeDto1> at = new ArrayList<>();
        accountTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).forEach(accountType -> at.add(new AccountTypeDto1(accountType)));
        return at;
    }

    @GetMapping(accountTypesUrl + "/{id}")
    AccountTypeDto2 getAccountTypeById(
            @PathVariable(name = "id") Long id
    ) {
        return new AccountTypeDto2(accountTypeRepository.findById(id).orElseThrow());
    }

    @PostMapping(accountTypesUrl)
    List<AccountTypeDto1> postNewAccountType(
            @RequestParam(name = "type") String type
    ) {
        AccountType accountType = new AccountType(type);
        accountTypeRepository.save(accountType);
        return getAllAccountTypes();
    }

    @DeleteMapping(accountTypesUrl + "/{id}")
    List<AccountTypeDto1> deleteAccount(
            @PathVariable(name = "id") Long id
    ) {
        try {
            AccountType accountType = accountTypeRepository.findById(id).orElseThrow();
            accountTypeRepository.delete(accountType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getAllAccountTypes();
    }

    @PatchMapping(accountTypesUrl + "/{id}")
    List<AccountTypeDto1> patchAccount(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "type") String type
    ) {
        try {
            AccountType accountType = accountTypeRepository.findById(id).orElseThrow();
            accountType.setType(type);
            accountTypeRepository.save(accountType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getAllAccountTypes();
    }*/

}
