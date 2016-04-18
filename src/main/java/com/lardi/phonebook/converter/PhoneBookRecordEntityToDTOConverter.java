package com.lardi.phonebook.converter;

import com.lardi.phonebook.dto.PhoneBookRecordDTO;
import com.lardi.phonebook.entity.PhoneBookRecord;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Nikolay Yashchenko
 */
public class PhoneBookRecordEntityToDTOConverter implements Converter<PhoneBookRecord, PhoneBookRecordDTO> {

    @Override
    public PhoneBookRecordDTO convert(PhoneBookRecord phoneBookRecord) {
        if (phoneBookRecord == null) return null;

        PhoneBookRecordDTO phoneBookRecordDTO = new PhoneBookRecordDTO();
        phoneBookRecordDTO.setId(phoneBookRecord.getId());
        phoneBookRecordDTO.setUserId(phoneBookRecord.getUser().getId());
        phoneBookRecordDTO.setFirstName(phoneBookRecord.getFirstName());
        phoneBookRecordDTO.setLastName(phoneBookRecord.getLastName());
        phoneBookRecordDTO.setPatronymic(phoneBookRecord.getPatronymic());
        phoneBookRecordDTO.setEmail(phoneBookRecord.getEmail());
        phoneBookRecordDTO.setMobilePhone(phoneBookRecord.getMobilePhone());
        phoneBookRecordDTO.setHomePhone(phoneBookRecord.getHomePhone());
        phoneBookRecordDTO.setAddress(phoneBookRecord.getAddress());

        return phoneBookRecordDTO;
    }
}
