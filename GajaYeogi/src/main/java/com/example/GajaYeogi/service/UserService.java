package com.example.GajaYeogi.service;

import com.example.GajaYeogi.dto.UserDto;
import com.example.GajaYeogi.entity.*;
import com.example.GajaYeogi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String saveuser(UserDto userDto){
        try {
            String user = userDto.getUser();
            String username = userDto.getUsername();

            Optional<UserEntity> userOptional = userRepository.findByUser(user);

            if (userOptional.isPresent()) {
                return "이미 존재하는 사용자입니다.";
            }

            UserEntity userEntity = new UserEntity();
            userEntity.setUser(user);
            userEntity.setUsername(username);

            userRepository.save(userEntity);

            return "사용자 추가 완료";
        } catch (Exception e) {
            e.printStackTrace();
            return "사용자 추가 실패";
        }
    }

    public UserDto readuser(UserDto userDto){
        UserDto userdtos = new UserDto();
        try{
            String user = userDto.getUser();

            Optional<UserEntity> userOptional = userRepository.findByUser(user);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();

                userdtos.setUserid(userEntity.getUserid());
                userdtos.setUser(userEntity.getUser());
                userdtos.setUsername(userEntity.getUsername());

                List<String> scrapIds = userEntity.getScraps().stream()
                        .map(ScrapEntity::getScrapid)
                        .collect(Collectors.toList());
                userdtos.setScrapids(scrapIds);

                List<String> postwriteids = userEntity.getPostwriteid().stream()
                        .map(PostWriteidEntity::getPostwriteid)
                        .collect(Collectors.toList());
                userdtos.setPostwriteids(postwriteids);

                List<String> reviewwriteids = userEntity.getReviewwriteid().stream()
                        .map(ReviewWriteidEntity::getReviewwriteid)
                        .collect(Collectors.toList());
                userdtos.setReviewwriteids(reviewwriteids);

                List<String> visitids = userEntity.getVisitid().stream()
                        .map(VisitidEntity::getVisitid)
                        .collect(Collectors.toList());
                userdtos.setVisitids(visitids);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return userdtos;
    }
}
