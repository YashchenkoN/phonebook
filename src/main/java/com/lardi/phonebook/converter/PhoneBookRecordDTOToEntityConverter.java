package com.lardi.phonebook.converter;

import com.lardi.phonebook.dto.PhoneBookRecordDTO;
import com.lardi.phonebook.entity.PhoneBookRecord;
import com.lardi.phonebook.service.PhoneBookRecordService;
import com.lardi.phonebook.service.UserService;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Nikolay Yashchenko
 */
public class PhoneBookRecordDTOToEntityConverter implements Converter<PhoneBookRecordDTO, PhoneBookRecord> {

    private UserService userService;
    private PhoneBookRecordService phoneBookRecordService;

    public PhoneBookRecordDTOToEntityConverter(UserService userService, PhoneBookRecordService phoneBookRecordService) {
        this.userService = userService;
        this.phoneBookRecordService = phoneBookRecordService;
    }

    @Override
    public PhoneBookRecord convert(PhoneBookRecordDTO phoneBookRecordDTO) {
        if (phoneBookRecordDTO == null) return null;

        PhoneBookRecord phoneBookRecord = null;

        if (phoneBookRecordDTO.getId() != null) {
            phoneBookRecord = phoneBookRecordService.read(phoneBookRecordDTO.getId());
        }

        if (phoneBookRecord == null) {
            phoneBookRecord = new PhoneBookRecord();
            phoneBookRecord.setUser(userService.read(phoneBookRecordDTO.getUserId()));
        }

        phoneBookRecord.setFirstName(phoneBookRecordDTO.getFirstName());
        phoneBookRecord.setLastName(phoneBookRecordDTO.getLastName());
        phoneBookRecord.setPatronymic(phoneBookRecordDTO.getPatronymic());
        phoneBookRecord.setEmail(phoneBookRecordDTO.getEmail());
        phoneBookRecord.setMobilePhone(phoneBookRecordDTO.getMobilePhone());
        phoneBookRecord.setHomePhone(phoneBookRecordDTO.getHomePhone());
        phoneBookRecord.setAddress(phoneBookRecordDTO.getAddress());

        return phoneBookRecord;
    }
}
