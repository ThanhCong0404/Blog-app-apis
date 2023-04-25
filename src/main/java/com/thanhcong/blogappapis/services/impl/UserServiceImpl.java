package com.thanhcong.blogappapis.services.impl;

import com.thanhcong.blogappapis.entities.User;
import com.thanhcong.blogappapis.exceptions.ResourceNotFoundException;
import com.thanhcong.blogappapis.payloads.UserDto;
import com.thanhcong.blogappapis.repositories.UserRepository;
import com.thanhcong.blogappapis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User saveUser = userRepository.save(user);

        return userToDTO(saveUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow( ()-> new ResourceNotFoundException("User"," id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User userUpdate = userRepository.save(user);

        return userToDTO(userUpdate);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = userRepository.findById(userId).orElseThrow( ()-> new ResourceNotFoundException("User"," id",userId));

        return userToDTO(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> userDtos = users.stream().map(user -> userToDTO(user)).collect(Collectors.toList());
        return userDtos;

    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow( ()-> new ResourceNotFoundException("User"," id",userId));
        userRepository.delete(user);

    }


    private User dtoToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDTO(User user ){
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAbout(user.getAbout());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }


}
