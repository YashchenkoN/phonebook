package com.lardi.phonebook.web.rest;

import com.lardi.phonebook.dto.BaseResponse;
import com.lardi.phonebook.dto.PhoneBookRecordDTO;
import com.lardi.phonebook.entity.PhoneBookRecord;
import com.lardi.phonebook.service.AuthenticationHelperService;
import com.lardi.phonebook.service.PhoneBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Yashchenko
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/api/record")
public class PhoneBookRecordEndpoint {

    @Autowired
    @Qualifier("phoneBookRecordDTOValidator")
    private Validator phoneBookRecordValidator;

    @Autowired
    @Qualifier("idValidator")
    private Validator idValidator;

    @Autowired
    @Qualifier("phoneBookMobilePhoneInDBValidator")
    private Validator phoneBookIdValidator;

    @Autowired
    private PhoneBookRecordService phoneBookRecordService;

    @Autowired
    @Qualifier("myConversionService")
    private ConversionService conversionService;

    @Autowired
    private AuthenticationHelperService authenticationHelperService;

    @InitBinder("phoneBookRecordDTO")
    public void initDTOValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(phoneBookRecordValidator);
    }

    @InitBinder("id")
    public void initIdValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(idValidator);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll(@RequestParam(value = "firstname", required = false) String firstName,
                                 @RequestParam(value = "lastname", required = false) String lastName,
                                 @RequestParam(value = "phone", required = false) String phone) {

        Long userId = authenticationHelperService.getLoggedInUserId();

        List<PhoneBookRecord> phoneBookRecordList = firstName != null && !firstName.isEmpty() ? phoneBookRecordService.getByFirstName(firstName, userId) :
                lastName != null && !lastName.isEmpty() ? phoneBookRecordService.getByLastName(lastName, userId) :
                        phone != null && !phone.isEmpty() ? phoneBookRecordService.getByPhone(phone, userId) : phoneBookRecordService.getByUserId(userId);

        List<PhoneBookRecordDTO> result = phoneBookRecordList.stream()
                .map(p -> conversionService.convert(p, PhoneBookRecordDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result.toArray());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Validated @RequestBody PhoneBookRecordDTO phoneBookRecordDTO,
                                 BindingResult bindingResult) {

        phoneBookIdValidator.validate(phoneBookRecordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new BaseResponse(bindingResult));
        }

        PhoneBookRecord phoneBookRecord = conversionService.convert(phoneBookRecordDTO, PhoneBookRecord.class);
        phoneBookRecord = phoneBookRecordService.create(phoneBookRecord);

        phoneBookRecordDTO = conversionService.convert(phoneBookRecord, PhoneBookRecordDTO.class);

        return ResponseEntity.ok(phoneBookRecordDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@Validated @PathVariable("id") Long id,
                                 @Validated @RequestBody PhoneBookRecordDTO phoneBookRecordDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new BaseResponse(bindingResult));
        }

        phoneBookRecordDTO.setId(id);

        PhoneBookRecord phoneBookRecord = conversionService.convert(phoneBookRecordDTO, PhoneBookRecord.class);
        phoneBookRecord = phoneBookRecordService.create(phoneBookRecord);

        return ResponseEntity.ok(conversionService.convert(phoneBookRecord, PhoneBookRecordDTO.class));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "id");
        idValidator.validate(id, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new BaseResponse(bindingResult));
        }
        phoneBookRecordService.delete(id);
        return ResponseEntity.ok().build();
    }
}
