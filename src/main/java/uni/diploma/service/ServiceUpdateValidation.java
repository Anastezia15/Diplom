package uni.diploma.service;

import uni.diploma.model.dto.UserDto;

interface ServiceUpdateValidation {
    boolean checkEmail(UserDto userDto);
    boolean checkUsername(UserDto userDto);
    boolean checkPassword(UserDto userDto);
}
